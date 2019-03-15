package com.cn.rmq.api.service;

import com.cn.rmq.api.model.dto.cms.DataGrid;
import com.cn.rmq.api.model.dto.cms.message.CmsMessageListDto;
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
     */
    void resendAllDeadMessageByQueueName(String consumerQueue);

    /**
     * 分页查询
     *
     * @param req 查询条件
     * @return 数据列表
     */
    DataGrid listPage(CmsMessageListDto req);
}
