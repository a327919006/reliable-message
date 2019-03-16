package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.cms.model.po.Queue;
import com.cn.rmq.api.cms.model.vo.queue.CmsQueueVo;

import java.util.List;

/**
 * @author Chen Nan
 */
public interface QueueMapper extends BaseMapper<Queue, String> {

    /**
     * CMS获取消息列表
     *
     * @param req 请求参数
     * @return 消息列表
     */
    List<CmsQueueVo> cmsListPage(CmsQueueListDto req);
}