package com.cn.rmq.schedule.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.schedule.service.ICheckMessageService;
import com.cn.rmq.api.schedule.service.ScheduleMessageDto;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IQueueService;
import com.cn.rmq.schedule.config.CheckTaskConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
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
    public void checkMessage() {
        ScheduleMessageDto condition = new ScheduleMessageDto();

        // 获取消费队列列表
        List<Queue> queueList = queueService.list(new Queue());
        for (Queue queue : queueList) {

            // 设置消息查询条件
            setCondition(condition, queue);
            log.info("condition=" + condition);

            // 获取预发送消息列表

//            List<Message> messageList = messageService.list(condition);
//            for (Message message : messageList) {
//                log.info("msaage=" + JSONUtil.toJsonStr(message));
//            }
        }
    }

    /**
     * 设置查询条件
     *
     * @param condition 条件对象
     * @param queue     队列信息
     */
    private void setCondition(ScheduleMessageDto condition, Queue queue) {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MILLISECOND, -queue.getCheckDuration());
        condition.setCreateEndTime(DateUtil.formatDateTime(calendar.getTime()));

        calendar.add(Calendar.MILLISECOND, -queue.getCheckDuration() * checkTaskConfig.getMaxDurationTimes());
        condition.setCreateStartTime(DateUtil.formatDateTime(calendar.getTime()));

        condition.setStatus(MessageStatusEnum.WAIT.getValue());
        condition.setConsumerQueue(queue.getConsumerQueue());
    }
}
