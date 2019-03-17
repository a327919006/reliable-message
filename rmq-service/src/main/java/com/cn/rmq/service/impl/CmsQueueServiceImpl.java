package com.cn.rmq.service.impl;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.cms.model.vo.queue.CmsQueueVo;
import com.cn.rmq.api.cms.service.ICmsQueueService;
import com.cn.rmq.api.model.po.Queue;
import com.cn.rmq.dal.mapper.QueueMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

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

    @Override
    public DataGrid listPage(CmsQueueListDto req) {
        Page<Object> pageInfo = PageHelper.startPage(req.getPage(), req.getRows());
        List<CmsQueueVo> list = mapper.cmsListPage(req);

        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(list);
        dataGrid.setTotal(pageInfo.getTotal());
        return dataGrid;
    }
}
