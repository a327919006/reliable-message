package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.model.dto.cms.message.CmsMessageListDto;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.vo.cms.message.CmsMessageVo;

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

    List<CmsMessageVo> cmsListPage(CmsMessageListDto req);
}