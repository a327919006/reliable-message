package com.cn.rmq.api.schedule.service;

import com.cn.rmq.api.model.po.Queue;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息确认子系统服务接口
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
public interface ICheckMessageService {
    /**
     * 处理所有长时间未确认的消息，和上游业务系统确认是否发送该消息
     */
    void checkWaitingMessage();

    /**
     * 处理某个队列长时间未确认的消息
     * @param queue 队列信息
     */
    void checkQueueWaitingMessage(Queue queue);
}
