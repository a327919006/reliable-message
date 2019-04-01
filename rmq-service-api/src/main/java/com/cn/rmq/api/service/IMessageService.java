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
     * 标记所有重发次数超过限制的消息为已死亡
     *
     * @param resendTimes 最大重发次数限制
     * @return 处理记录数量
     */
    int updateMessageDead(Short resendTimes);
}
