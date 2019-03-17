package com.cn.rmq.api.cms.service;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.api.service.IBaseService;

/**
 * 消费队列服务接口
 *
 * @author Chen Nan
 */
public interface ICmsQueueService extends IBaseService<Queue, String> {

    /**
     * 分页查询
     *
     * @param req 查询条件
     * @return 数据列表
     */
    DataGrid listPage(CmsQueueListDto req);
}
