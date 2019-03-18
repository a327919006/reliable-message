package com.cn.rmq.check.schedule;

import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IQueueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@Component
@Slf4j
public class CheckTask {

    @Reference
    private IQueueService queueService;
    @Reference
    private IMessageService messageService;

    @Scheduled(cron = "0/5 * * * * ? ")
    public void check() {
        log.info("【CheckTask】start");

        Message messageCondition = new Message();

        // 获取消费队列列表
        List<Queue> queueList = queueService.list(new Queue());
        for (Queue queue : queueList) {

            // 获取预发送消息列表
            messageCondition.setConsumerQueue(queue.getConsumerQueue());
            messageCondition.setStatus(MessageStatusEnum.WAIT.getValue());

            List<Message> messageList = messageService.list(messageCondition);

            for (Message message : messageList) {

            }
        }

        log.info("【CheckTask】end");
    }
}
