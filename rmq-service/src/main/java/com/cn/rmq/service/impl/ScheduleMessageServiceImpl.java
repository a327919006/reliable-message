package com.cn.rmq.service.impl;

import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.schedule.model.dto.ScheduleMessageDto;
import com.cn.rmq.api.schedule.service.IScheduleMessageService;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.dal.mapper.MessageMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

/**
 * 消息服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class ScheduleMessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, String>
        implements IScheduleMessageService {

    @Reference
    private IMessageService messageService;

    @Override
    public Page<Message> listPage(ScheduleMessageDto req) {
        Page<Message> page = PageHelper.startPage(req.getPageNum(), req.getPageSize(), req.getNeedCount());
        mapper.listByCondition(req);
        return page;
    }
}
