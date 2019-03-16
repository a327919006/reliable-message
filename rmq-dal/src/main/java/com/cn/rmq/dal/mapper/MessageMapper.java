package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.cms.model.dto.message.CmsMessageListDto;
import com.cn.rmq.api.cms.model.vo.message.CmsMessageVo;
import com.cn.rmq.api.model.po.Message;

import java.util.List;

/**
 * @author Chen Nan
 */
public interface MessageMapper extends BaseMapper<Message, String> {

    /**
     * 增加重发次数
     *
     * @param id 消息ID
     * @return 处理结果
     */
    int addResendTimes(String id);

    /**
     * CMS获取消息列表
     * @param req 请求参数
     * @return 消息列表
     */
    List<CmsMessageVo> cmsListPage(CmsMessageListDto req);
}