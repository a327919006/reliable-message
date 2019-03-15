package com.cn.rmq.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.enums.AlreadyDeadEnum;
import com.cn.rmq.api.enums.MessageStatusEnum;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.dal.mapper.MessageMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
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
        message.setUpdateTime(new Date());
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
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
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
        Message message = new Message();
        message.setId(messageId);
        int count = mapper.count(message);
        if (0 == count) {
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

        // 校验消息是否存在
        Message message = new Message();
        message.setId(messageId);
        int count = mapper.count(message);
        if (0 == count) {
            throw new CheckException("message not exist");
        }

        // 更新消息死亡
        message.setAlreadyDead(AlreadyDeadEnum.YES.getValue());
        message.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(message);
    }

    @Override
    public void resendAllDeadMessageByQueueName(String consumerQueue) {
        // 构造查询条件
        Message condition = new Message();
        condition.setConsumerQueue(consumerQueue);
        condition.setAlreadyDead(AlreadyDeadEnum.NO.getValue());

        int pageSize = 100;
        // 计数标识，首页需要查询死亡消息总数
        boolean countFlag = true;
        int pages;

        for (int pageNum = 1; ; pageNum++) {
            // 分页查询死亡消息
            Page<Message> pageInfo = PageHelper.startPage(pageNum, pageSize, countFlag);
            List<Message> list = mapper.list(condition);

            // 遍历消息列表，重发消息
            list.forEach(this::resendMessage);

            pages = pageInfo.getPages();
            if (pageNum >= pages) {
                break;
            }
            countFlag = false;
        }
    }
}
