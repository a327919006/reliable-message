package com.cn.rmq.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.cms.service.ICmsMessageService;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.dto.queue.QueueAddDto;
import com.cn.rmq.api.model.dto.queue.QueueUpdateDto;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.service.IQueueService;
import com.cn.rmq.dal.mapper.QueueMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 队列服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class QueueServiceImpl extends BaseServiceImpl<QueueMapper, Queue, String>
        implements IQueueService {

    @Reference
    private ICmsMessageService cmsMessageService;

    @Override
    public void add(QueueAddDto req) {
        Queue queue = new Queue();
        queue.setConsumerQueue(req.getConsumerQueue());
        int count = mapper.count(queue);
        if (count > 0) {
            throw new CheckException("consumerQueue:" + req.getConsumerQueue() + " is exist");
        }

        BeanUtils.copyProperties(req, queue);
        queue.setId(IdUtil.simpleUUID());
        queue.setCreateTime(new Date());
        queue.setUpdateTime(new Date());
        mapper.insertSelective(queue);
    }

    @Override
    public void update(QueueUpdateDto req) {
        Queue queue = mapper.selectByPrimaryKey(req.getId());
        if (queue == null) {
            throw new CheckException("queue not exist");
        }

        // 校验消费队列是否重复
        Queue checkCondition = new Queue();
        checkCondition.setConsumerQueue(req.getConsumerQueue());
        Queue check = mapper.get(checkCondition);
        if (check != null && !check.getId().equals(queue.getId())) {
            throw new CheckException("consumerQueue:" + req.getConsumerQueue() + " is exist");
        }

        BeanUtils.copyProperties(req, queue);
        queue.setUpdateTime(new Date());
        mapper.updateByPrimaryKeySelective(queue);
    }

    @Override
    public int resendDead(String id) {
        Queue queue = mapper.selectByPrimaryKey(id);
        if (queue == null) {
            throw new CheckException("queue not exist");
        }

        return cmsMessageService.resendAllDeadMessageByQueueName(queue.getConsumerQueue());
    }
}
