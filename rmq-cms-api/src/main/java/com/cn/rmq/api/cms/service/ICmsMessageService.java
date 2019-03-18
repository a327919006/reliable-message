package com.cn.rmq.api.cms.service;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.message.CmsMessageListDto;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.service.IBaseService;

/**
 * 消息服务接口
 *
 * @author Chen Nan
 */
public interface ICmsMessageService extends IBaseService<Message, String> {

    /**
     * 分页查询
     *
     * @param req 查询条件
     * @return 数据列表
     */
    DataGrid listPage(CmsMessageListDto req);

    /**
     * 重发某个消息队列中的全部已死亡的消息
     *
     * @param consumerQueue 消费队列
     * @return 重发的消息数量
     */
    int resendAllDeadMessageByQueueName(String consumerQueue);
}
