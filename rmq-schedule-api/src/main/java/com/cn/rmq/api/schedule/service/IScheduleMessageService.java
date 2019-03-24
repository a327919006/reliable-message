package com.cn.rmq.api.schedule.service;

import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.schedule.model.dto.ScheduleMessageDto;
import com.cn.rmq.api.service.IBaseService;
import com.github.pagehelper.Page;

/**
 * <p>Title:</p>
 * <p>Description:
 * 消息确认子系统服务接口
 * </p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
public interface IScheduleMessageService extends IBaseService<Message, String> {

    /**
     * 分页查询
     *
     * @param req 查询条件
     * @return 数据列表
     */
    Page<Message> listPage(ScheduleMessageDto req);
}
