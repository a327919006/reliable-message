package com.cn.rmq.service.impl;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.message.CmsMessageListDto;
import com.cn.rmq.api.cms.model.vo.message.CmsMessageVo;
import com.cn.rmq.api.cms.service.ICmsMessageService;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.dal.mapper.MessageMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

/**
 * 消息服务实现
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service
@Slf4j
public class CmsMessageServiceImpl extends BaseServiceImpl<MessageMapper, Message, String>
        implements ICmsMessageService {

    @Override
    public DataGrid listPage(CmsMessageListDto req) {
        Page<Object> pageInfo = PageHelper.startPage(req.getPage(), req.getRows());
        List<CmsMessageVo> list = mapper.cmsListPage(req);

        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(list);
        dataGrid.setTotal(pageInfo.getTotal());
        return dataGrid;
    }
}
