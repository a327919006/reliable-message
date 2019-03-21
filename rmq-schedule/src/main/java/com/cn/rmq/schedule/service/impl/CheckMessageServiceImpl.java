package com.cn.rmq.schedule.service.impl;

import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.schedule.service.ICheckMessageService;
import com.cn.rmq.api.schedule.service.ScheduleMessageDto;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IQueueService;
import com.cn.rmq.api.utils.DateFormatUtils;
import com.cn.rmq.schedule.config.CheckTaskConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    private IMessageService messageService;
    @Autowired
    private CheckTaskConfig checkTaskConfig;

    @Override
    public void checkWaitingMessage() {
        // 获取消费队列列表
        List<Queue> queueList = queueService.list(new Queue());
        for (Queue queue : queueList) {
            checkQueueWaitingMessage(queue);
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
        log.info("condition=" + condition);

        // 获取预发送消息列表

        List<Message> messageList = messageService.listByCondition(condition);
        for (Message message : messageList) {
            log.info("message=" + JSONUtil.toJsonStr(message));
            checkMessage(queue, message);
        }
    }

    /**
     * 处理某个未确认的消息
     *
     * @param message 消息信息
     */
    private void checkMessage(Queue queue, Message message) {

    }

    /**
     * 创建消息查询条件
     *
     * @param queue 队列信息
     */
    private ScheduleMessageDto createCondition(Queue queue) {
        ScheduleMessageDto condition = new ScheduleMessageDto();

        LocalDateTime dateTime = LocalDateTime.now();

        dateTime = dateTime.minus(queue.getCheckDuration().longValue(), ChronoUnit.MILLIS);
        condition.setCreateEndTime(DateFormatUtils.formatDateTime(dateTime));

        dateTime = dateTime.minus(queue.getCheckDuration() * checkTaskConfig.getMaxDurationTimes(), ChronoUnit.MILLIS);
        condition.setCreateStartTime(DateFormatUtils.formatDateTime(dateTime));

        condition.setStatus(MessageStatusEnum.WAIT.getValue());
        condition.setConsumerQueue(queue.getConsumerQueue());

        return condition;
    }
}
