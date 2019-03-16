package com.cn.rmq.api.service;

import com.cn.rmq.api.model.po.Message;

/**
 * 消息服务接口
 *
 * @author Chen Nan
 */
public interface IMessageService extends IBaseService<Message, String> {
    /**
     * 创建预发送消息
     *
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     * @return 消息ID
     */
    String createPreMessage(String consumerQueue, String messageBody);

    /**
     * 确认发送消息
     *
     * @param messageId 消息 ID
     */
    void confirmAndSendMessage(String messageId);

    /**
     * 存储并发送消息
     *
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     */
    void saveAndSendMessage(String consumerQueue, String messageBody);

    /**
     * 直接发送消息
     *
     * @param consumerQueue 消费队列
     * @param messageBody   消息内容
     */
    void directSendMessage(String consumerQueue, String messageBody);

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

    /**
     * 重发某个消息队列中的全部已死亡的消息
     *
     * @param consumerQueue 消费队列
     * @return 重发的消息数量
     */
    int resendAllDeadMessageByQueueName(String consumerQueue);
}
