package com.cn.rmq.cms.web;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.message.CmsMessageListDto;
import com.cn.rmq.api.cms.model.vo.message.CmsMessageVo;
import com.cn.rmq.api.cms.service.ICmsMessageService;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.dto.RspBase;
import com.cn.rmq.api.model.po.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>消息管理制器</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@RestController
@RequestMapping(value = "/message")
@Slf4j
public class MessageController {

    @Reference
    private ICmsMessageService cmsMessageService;

    @GetMapping("/page")
    public Object page(@ModelAttribute CmsMessageListDto req) {
        log.info("【message-page】start：" + req);
        DataGrid rsp = cmsMessageService.listPage(req);
        log.info("【message-page】success");
        return rsp;
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable("id") String id) {
        log.info("【message-get】start：" + id);
        Message message = cmsMessageService.selectByPrimaryKey(id);
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
