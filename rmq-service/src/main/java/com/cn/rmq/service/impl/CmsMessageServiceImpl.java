package com.cn.rmq.service.impl;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.message.CmsMessageListDto;
import com.cn.rmq.api.cms.model.vo.message.CmsMessageVo;
import com.cn.rmq.api.cms.service.ICmsMessageService;
import com.cn.rmq.api.enums.AlreadyDeadEnum;
import com.cn.rmq.api.exceptions.CheckException;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.dal.mapper.MessageMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference
    private IMessageService messageService;

    @Override
    public DataGrid listPage(CmsMessageListDto req) {
        Page<Object> pageInfo = PageHelper.startPage(req.getPage(), req.getRows());
        List<CmsMessageVo> list = mapper.cmsListPage(req);

        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(list);
        dataGrid.setTotal(pageInfo.getTotal());
        return dataGrid;
    }

    @Override
    public int resendAllDeadMessageByQueueName(String consumerQueue) {
        if (StringUtils.isBlank(consumerQueue)) {
            throw new CheckException("consumerQueue is empty");
        }

        // 构造查询条件
        Message condition = new Message();
        condition.setConsumerQueue(consumerQueue);
        condition.setAlreadyDead(AlreadyDeadEnum.NO.getValue());

        int pageSize = 100;
        // 计数标识，首页需要查询死亡消息总数
        boolean countFlag = true;
        int pages;
        int totalCount = 0;

        for (int pageNum = 1; ; pageNum++) {
            // 分页查询死亡消息
            Page<Message> pageInfo = PageHelper.startPage(pageNum, pageSize, countFlag);
            List<Message> list = mapper.list(condition);

            // 遍历消息列表，重发消息
            list.forEach((message) -> messageService.resendMessage(message));

            // 计数
            totalCount += list.size();

            pages = pageInfo.getPages();
            if (pageNum >= pages) {
                break;
            }
            countFlag = false;
        }
        return totalCount;
    }
}
