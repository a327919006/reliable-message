package com.cn.rmq.schedule.service.impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.schedule.service.ICheckMessageService;
import com.cn.rmq.api.schedule.service.ScheduleMessageDto;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IQueueService;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.api.utils.DateFormatUtils;
import com.cn.rmq.schedule.config.CheckTaskConfig;
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
    private ThreadPoolExecutor executor;
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
            // 因为确认超时时间最长为5秒，因此此处超时时间建议设置大于5秒，则足够所有线程完成。
            boolean complete = executor.awaitTermination(config.getWaitCompleteTimeout(), TimeUnit.MILLISECONDS);
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

        // 获取预发送消息列表
        List<Message> messageList = messageService.listByCondition(condition);
        for (Message message : messageList) {
            try {
                executor.execute(() -> checkMessage(queue, message));
            } catch (RejectedExecutionException e) {
                log.error("【CheckTask】Thread pool exhaustion:" + e.getMessage());
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
            String checkRsp = HttpUtil.post(queue.getCheckUrl(), message.getMessageBody(), queue.getCheckTimeout());
            log.info("【CheckTask】check success, messageId={}, checkRsp={}", message.getId(), checkRsp);

            JSONObject jsonObject = JSONUtil.parseObj(checkRsp);
            Integer code = jsonObject.getInt(Constants.KEY_CODE);
            if (code.equals(Constants.CODE_SUCCESS)) {
                Integer data = jsonObject.getInt(Constants.KEY_DATA);
                if (data == 1) {
                    log.info("【CheckTask】message confirm, messageId={}", message.getId());
                    rmqService.confirmAndSendMessage(message.getId());
                } else {
                    log.info("【CheckTask】message delete, messageId={}, data={}", message.getId(), data);
                    messageService.deleteByPrimaryKey(message.getId());
                }
            } else {
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

        LocalDateTime endTime = LocalDateTime.now().minus(queue.getCheckDuration().longValue(), ChronoUnit.MILLIS);

        // 多久未确认
        condition.setCreateEndTime(DateFormatUtils.formatDateTime(endTime));
        condition.setStatus(MessageStatusEnum.WAIT.getValue());
        condition.setConsumerQueue(queue.getConsumerQueue());

        return condition;
    }
}
