package com.cn.rmq.api.service;

import com.cn.rmq.api.model.po.Message;

/**
 * 消息服务接口
 *
 * @author Chen Nan
 */
public interface IMessageService extends IBaseService<Message, String> {

    /**
     * 重发消息
     *
     * @param message 消息
     */
    void resendMessage(Message message);

    /**
     * 重发消息
     *
     * @param messageId 消息ID
     */
    void resendMessageById(String messageId);

    /**
     * 将消息标记为死亡消息
     *
     * @param messageId 消息ID
     */
    void setMessageToAlreadyDead(String messageId);
}
