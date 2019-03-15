package com.cn.rmq.cms.web;

import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.dto.RspBase;
import com.cn.rmq.api.model.dto.cms.DataGrid;
import com.cn.rmq.api.model.dto.cms.message.CmsMessageListDto;
import com.cn.rmq.api.model.po.Message;
import com.cn.rmq.api.model.vo.cms.message.CmsMessageVo;
import com.cn.rmq.api.service.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chen Nan
 */
@RestController
@RequestMapping(value = "/message")
@Slf4j
public class MessageController {

    @Reference
    private IMessageService messageService;

    @GetMapping("/page")
    public Object page(@ModelAttribute CmsMessageListDto req) {
        log.info("【message-page】start：" + req);
        DataGrid rsp = messageService.listPage(req);
        log.info("【message-page】success");
        return rsp;
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable("id") String id) {
        log.info("【message-get】start：" + id);
        Message message = messageService.selectByPrimaryKey(id);
        RspBase rspBase = new RspBase();
        if (message != null) {
            CmsMessageVo messageVo = new CmsMessageVo();
            BeanUtils.copyProperties(message, messageVo);
            rspBase.setData(messageVo);
            log.info("【message-get】success");
        } else {
            rspBase.code(Constants.CODE_FAILURE).msg("message not exist");
        }
        return rspBase;
    }

}
