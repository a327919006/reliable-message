package com.cn.rmq.api.cms.service;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueAddDto;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueUpdateDto;
import com.cn.rmq.api.cms.model.po.Queue;
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

    /**
     * 添加
     * @param req 添加对象参数
     */
    void add(CmsQueueAddDto req);

    /**
     * 更新
     * @param req 更新对象参数
     */
    void update(CmsQueueUpdateDto req);

    /**
     * 重发队列死亡消息
     * @param id 队列ID
     * @return 重发消息数量
     */
    int resendDead(String id);
}
