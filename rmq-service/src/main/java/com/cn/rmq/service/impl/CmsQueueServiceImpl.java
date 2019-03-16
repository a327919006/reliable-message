package com.cn.rmq.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueAddDto;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueUpdateDto;
import com.cn.rmq.api.cms.model.po.Queue;
import com.cn.rmq.api.cms.model.vo.queue.CmsQueueVo;
import com.cn.rmq.api.cms.service.ICmsQueueService;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.dal.mapper.QueueMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * 队列服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class CmsQueueServiceImpl extends BaseServiceImpl<QueueMapper, Queue, String>
        implements ICmsQueueService {

    @Reference
    private IMessageService messageService;

    @Override
    public DataGrid listPage(CmsQueueListDto req) {
        Page<Object> pageInfo = PageHelper.startPage(req.getPage(), req.getRows());
        List<CmsQueueVo> list = mapper.cmsListPage(req);

        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(list);
        dataGrid.setTotal(pageInfo.getTotal());
        return dataGrid;
    }

    @Override
    public void add(CmsQueueAddDto req) {
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
    public void update(CmsQueueUpdateDto req) {
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

        return messageService.resendAllDeadMessageByQueueName(queue.getConsumerQueue());
    }
}
