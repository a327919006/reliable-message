package com.cn.rmq.service.impl;

import com.cn.rmq.api.enums.AlreadyDeadEnum;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.dal.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 消息服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class MessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, String>
        implements IMessageService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resendMessage(Message message) {
        // 增加重发次数
        mapper.addResendTimes(message.getId());

        // 发送MQ消息
        jmsMessagingTemplate.convertAndSend(message.getConsumerQueue(), message.getMessageBody());
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

    @Override
    public void setMessageToAlreadyDead(String messageId) {
        if (StringUtils.isBlank(messageId)) {
            throw new CheckException("messageId is empty");
        }

        Message message = new Message();
        message.setId(messageId);
        message.setAlreadyDead(AlreadyDeadEnum.YES.getValue());
        message.setUpdateTime(LocalDateTime.now());
        mapper.updateByPrimaryKeySelective(message);
    }
}
