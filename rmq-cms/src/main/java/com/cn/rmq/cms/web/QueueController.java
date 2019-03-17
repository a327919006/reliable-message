package com.cn.rmq.cms.web;

import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.queue.CmsQueueListDto;
import com.cn.rmq.api.cms.model.po.SysUser;
import com.cn.rmq.api.cms.service.ICmsQueueService;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.dto.RspBase;
import com.cn.rmq.api.model.dto.queue.QueueAddDto;
import com.cn.rmq.api.model.dto.queue.QueueUpdateDto;
import com.cn.rmq.api.service.IMessageService;
import com.cn.rmq.api.service.IQueueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * <p>消息管理制器</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@RestController
@RequestMapping(value = "/queue")
@Slf4j
public class QueueController {

    @Reference
    private ICmsQueueService cmsQueueService;
    @Reference
    private IQueueService queueService;
    @Reference
    private IMessageService messageService;

    @GetMapping("/page")
    public Object page(@ModelAttribute CmsQueueListDto req) {
        log.info("【queue-page】start：" + req);
        DataGrid rsp = cmsQueueService.listPage(req);
        log.info("【queue-page】success");
        return rsp;
    }

    @PostMapping
    public Object add(@ModelAttribute @Valid QueueAddDto req, HttpSession session) {
        log.info("【queue-create】add：" + req);
        SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
        req.setCreateUser(sysUser.getUserName());
        req.setUpdateUser(sysUser.getUserName());
        queueService.add(req);
        log.info("【queue-create】add");
        return new RspBase();
    }

    @PutMapping
    public Object update(@ModelAttribute @Valid QueueUpdateDto req, HttpSession session) {
        log.info("【queue-update】create：" + req);
        SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
        req.setUpdateUser(sysUser.getUserName());
        queueService.update(req);
        log.info("【queue-update】create");
        return new RspBase();
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") String id) {
        log.info("【queue-delete】start：" + id);
        cmsQueueService.deleteByPrimaryKey(id);
        log.info("【queue-delete】success：" + id);
        return new RspBase();
    }

    @PostMapping("/{id}/resend")
    public Object resend(@PathVariable("id") String id) {
        log.info("【queue-resend】start：" + id);
        int count = queueService.resendDead(id);
        RspBase rspBase = new RspBase();
        rspBase.data(count);
        log.info("【queue-resend】success:id={},count={}", id, count);
        return rspBase;
    }
}
