package com.cn.rmq.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IRmqService;
import com.cn.rmq.dal.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 消息服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class RmqServiceImpl extends BaseServiceImpl<MessageMapper, Message, String>
        implements IRmqService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    public String createPreMessage(String consumerQueue, String messageBody) {
        if (StringUtils.isBlank(consumerQueue)) {
            throw new CheckException("consumerQueue is empty");
        }
        if (StringUtils.isBlank(messageBody)) {
            throw new CheckException("messageBody is empty");
        }

        String id = IdUtil.simpleUUID();

        // 插入预发送消息记录
        Message message = new Message();
        message.setId(id);
        message.setConsumerQueue(consumerQueue);
        message.setMessageBody(messageBody);
        message.setCreateTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        mapper.insertSelective(message);

        return id;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void confirmAndSendMessage(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            throw new CheckException("messageId is empty");
        }

        // 获取消息
        Message message = mapper.selectByPrimaryKey(messageId);
        if (message == null) {
            throw new CheckException("message not exist");
        }

        // 更新消息状态为发送中
        message = new Message();
        message.setId(messageId);
        message.setStatus(MessageStatusEnum.SENDING.getValue());
        message.setUpdateTime(LocalDateTime.now());
        mapper.updateByPrimaryKeySelective(message);

        // 发送MQ消息
        jmsMessagingTemplate.convertAndSend(message.getConsumerQueue(), message.getMessageBody());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveAndSendMessage(String consumerQueue, String messageBody) {
        if (StringUtils.isBlank(consumerQueue)) {
            throw new CheckException("consumerQueue is empty");
        }
        if (StringUtils.isBlank(messageBody)) {
            throw new CheckException("messageBody is empty");
        }

        String id = IdUtil.simpleUUID();

        // 插入发送消息记录
        Message message = new Message();
        message.setId(id);
        message.setConsumerQueue(consumerQueue);
        message.setMessageBody(messageBody);
        message.setStatus(MessageStatusEnum.SENDING.getValue());
        message.setCreateTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        mapper.insertSelective(message);

        // 发送MQ消息
        jmsMessagingTemplate.convertAndSend(consumerQueue, messageBody);
    }

    @Override
    public void directSendMessage(String consumerQueue, String messageBody) {
        if (StringUtils.isBlank(consumerQueue)) {
            throw new CheckException("consumerQueue is empty");
        }
        if (StringUtils.isBlank(messageBody)) {
            throw new CheckException("messageBody is empty");
        }

        // 发送MQ消息
        jmsMessagingTemplate.convertAndSend(consumerQueue, messageBody);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resendMessageById(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            throw new CheckException("messageId is empty");
        }

        // 校验消息是否存在
        Message message = mapper.selectByPrimaryKey(messageId);
        if (message == null) {
            throw new CheckException("message not exist");
        }

        // 增加重发次数
        mapper.addResendTimes(messageId);

        // 发送MQ消息
        jmsMessagingTemplate.convertAndSend(message.getConsumerQueue(), message.getMessageBody());
    }
}
