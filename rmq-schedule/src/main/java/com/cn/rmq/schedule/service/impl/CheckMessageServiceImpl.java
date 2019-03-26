package com.cn.rmq.schedule.service.impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.schedule.model.dto.ScheduleMessageDto;
import com.cn.rmq.api.schedule.service.ICheckMessageService;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IQueueService;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.api.utils.DateFormatUtils;
import com.cn.rmq.schedule.config.CheckTaskConfig;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Slf4j
@Service
public class CheckMessageServiceImpl implements ICheckMessageService {

    @Reference
    private IQueueService queueService;
    @Reference
    private IRmqService rmqService;
    @Reference
    private IMessageService messageService;

    @Autowired
    private ThreadPoolExecutor checkExecutor;
    @Autowired
    private CheckTaskConfig config;

    @Override
    public void checkWaitingMessage() {
        // 获取消费队列列表
        List<Queue> queueList = queueService.list(new Queue());
        for (Queue queue : queueList) {
            checkQueueWaitingMessage(queue);
        }
        log.info("【CheckTask】start wait all thread complete");
        try {
            checkExecutor.shutdown();
            // 因为确认超时时间最长为5秒，因此此处超时时间建议设置大于5秒，则足够所有线程完成。
            boolean complete = checkExecutor.awaitTermination(config.getWaitCompleteTimeout(), TimeUnit.MILLISECONDS);
            if (complete) {
                log.info("【CheckTask】all thread completed");
            } else {
                log.info("【CheckTask】wait all thread complete timeout");
            }
        } catch (InterruptedException e) {
            log.error("【CheckTask】InterruptedException", e);
        }
    }

    /**
     * 处理某个队列长时间未确认的消息
     *
     * @param queue 队列信息
     */
    private void checkQueueWaitingMessage(Queue queue) {
        // 设置消息查询条件
        ScheduleMessageDto condition = createCondition(queue);
        log.info("【CheckTask】message list condition=" + condition);

        int pageSize = config.getCorePoolSize();
        // 计数标识，首页需要获取消息总数
        boolean countFlag = true;
        int totalPage = 0;

        for (int pageNum = 1; ; pageNum++) {
            // 分页查询消息
            Page<Message> page = getPage(condition, pageNum, pageSize, countFlag);
            List<Message> messageList = page.getResult();

            // 多线程处理消息
            for (Message message : messageList) {
                try {
                    checkExecutor.execute(() -> checkMessage(queue, message));
                } catch (RejectedExecutionException e) {
                    log.error("【CheckTask】Thread pool exhaustion:" + e.getMessage());
                }
            }

            if (countFlag) {
                countFlag = false;
                totalPage = page.getPages();
            }
            if (pageNum >= totalPage) {
                break;
            }
        }
    }

    /**
     * 处理某个未确认的消息
     *
     * @param message 消息信息
     */
    private void checkMessage(Queue queue, Message message) {
        try {
            log.info("【CheckTask】check message={}", JSONUtil.toJsonStr(message));
            // 调用业务方http接口确认消息是否需要发送
            String checkRsp = HttpUtil.post(queue.getCheckUrl(), message.getMessageBody(), queue.getCheckTimeout());
            log.info("【CheckTask】check success, messageId={}, checkRsp={}", message.getId(), checkRsp);

            // 解析业务方返回结果
            JSONObject jsonObject = JSONUtil.parseObj(checkRsp);
            Integer code = jsonObject.getInt(Constants.KEY_CODE);
            if (code.equals(Constants.CODE_SUCCESS)) {
                // code=CODE_SUCCESS，业务方处理正常
                Integer data = jsonObject.getInt(Constants.KEY_DATA);
                if (data == 1) {
                    // data=1，该消息需要发送
                    log.info("【CheckTask】message confirm, messageId={}", message.getId());
                    rmqService.confirmAndSendMessage(message.getId());
                } else {
                    // data!=1，该消息不需要发送，直接删除
                    log.info("【CheckTask】message delete, messageId={}, data={}", message.getId(), data);
                    messageService.deleteByPrimaryKey(message.getId());
                }
            } else {
                // 业务方处理异常，记录日志
                String msg = jsonObject.getStr(Constants.KEY_MSG);
                log.error("【CheckTask】check fail, messageId={}, code={}, msg={}", message.getId(), code, msg);
            }
        } catch (HttpException e) {
            log.error("【CheckTask】check HttpException, messageId={}, error:{}", message.getId(), e.getMessage());
        } catch (Exception e) {
            log.error("【CheckTask】check Exception, messageId={}, error:{}", message.getId(), e.getMessage());
        }
    }

    /**
     * 创建消息查询条件
     *
     * @param queue 队列信息
     */
    private ScheduleMessageDto createCondition(Queue queue) {
        ScheduleMessageDto condition = new ScheduleMessageDto();

        // 计算时间
        LocalDateTime endTime = LocalDateTime.now().minus(queue.getCheckDuration().longValue(), ChronoUnit.MILLIS);

        // 多长时间未确认
        condition.setCreateEndTime(DateFormatUtils.formatDateTime(endTime));
        // 消息状态为待确认
        condition.setStatus(MessageStatusEnum.WAIT.getValue());
        // 指定队列
        condition.setConsumerQueue(queue.getConsumerQueue());
        // 排序字段
        condition.setOrderBy(Constants.ORDER_BY_CREATE_TIME);

        return condition;
    }

    /**
     * 获取分页消息
     *
     * @param condition 筛选条件
     * @param pageNum   页码
     * @param pageSize  数量
     * @param countFlag 是否获取总数
     * @return 本页消息
     */
    private Page<Message> getPage(ScheduleMessageDto condition, int pageNum, int pageSize, boolean countFlag) {
        condition.setPageNum(pageNum);
        condition.setPageSize(pageSize);
        condition.setCount(countFlag);
        return messageService.listPage(condition);
    }
}
