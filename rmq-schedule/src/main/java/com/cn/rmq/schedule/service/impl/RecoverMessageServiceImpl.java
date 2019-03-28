package com.cn.rmq.schedule.service.impl;

import com.cn.rmq.api.enums.AlreadyDeadEnum;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.schedule.model.dto.ScheduleMessageDto;
import com.cn.rmq.api.schedule.service.IRecoverMessageService;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.api.utils.DateFormatUtils;
import com.cn.rmq.schedule.config.RecoverTaskConfig;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
public class RecoverMessageServiceImpl implements IRecoverMessageService {

    @Reference
    private IMessageService messageService;
    @Reference
    private IRmqService rmqService;

    @Autowired
    private ThreadPoolExecutor recoverExecutor;
    @Autowired
    private RecoverTaskConfig config;

    @Override
    public void recoverSendingMessage() {
        int maxResendTimes = config.getInterval().size() - 1;
        for (int resendTimes = maxResendTimes; resendTimes >= 0; --resendTimes) {
            recoverSendingMessage(resendTimes);
        }
        log.info("【RecoverTask】start wait all thread complete");
        try {
            recoverExecutor.shutdown();
            boolean complete = recoverExecutor.awaitTermination(config.getWaitCompleteTimeout(), TimeUnit.MILLISECONDS);
            if (complete) {
                log.info("【RecoverTask】all thread completed");
            } else {
                log.info("【RecoverTask】wait all thread complete timeout");
            }
        } catch (InterruptedException e) {
            log.error("【RecoverTask】InterruptedException", e);
        }
    }

    private void recoverSendingMessage(int resendTimes) {
        // 设置消息查询条件
        ScheduleMessageDto condition = createCondition(resendTimes);
        log.info("【RecoverTask】message list condition=" + condition);

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
                    recoverExecutor.execute(() -> recoverMessage(message));
                } catch (RejectedExecutionException e) {
                    log.error("【RecoverTask】Thread pool exhaustion:" + e.getMessage());
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
     * 重发消息
     *
     * @param message 消息信息
     */
    private void recoverMessage(Message message) {
        messageService.resendMessage(message);
    }

    /**
     * 创建消息查询条件
     *
     * @param resendTimes 重发次数
     */
    private ScheduleMessageDto createCondition(int resendTimes) {
        ScheduleMessageDto condition = new ScheduleMessageDto();

        // 计算时间
        LocalDateTime endTime = LocalDateTime.now().minusMinutes(getRecoverInterval(resendTimes));

        // 多长时间未确认
        condition.setCreateEndTime(DateFormatUtils.formatDateTime(endTime));
        // 消息状态为待确认
        condition.setStatus(MessageStatusEnum.SENDING.getValue());
        // 消息未死亡
        condition.setAlreadyDead(AlreadyDeadEnum.NO.getValue());
        // 重发次数
        condition.setResendTimes((short) resendTimes);
        // 排序字段
        condition.setOrderBy(Constants.ORDER_BY_CONFIRM_TIME);

        return condition;
    }

    /**
     * 获取消息重发时间间隔（分钟）
     *
     * @param resendTimes 当前重发次数
     * @return 重发时间间隔（分钟）
     */
    private long getRecoverInterval(int resendTimes) {
        long result = 0L;
        List<Long> interval = config.getInterval();
        for (int i = 0; i <= resendTimes; i++) {
            result += interval.get(i);
        }
        return result;
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
