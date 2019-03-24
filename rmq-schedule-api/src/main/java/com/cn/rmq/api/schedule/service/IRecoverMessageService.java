package com.cn.rmq.api.schedule.service;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息确认子系统服务接口
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
public interface IRecoverMessageService {
    /**
     * 处理状态为“发送中”但长时间没有被成功确认消费的消息
     */
    void recoverSendingMessage();
}
