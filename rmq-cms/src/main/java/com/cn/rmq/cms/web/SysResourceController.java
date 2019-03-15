package com.cn.rmq.cms.web;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.dto.RspBase;
import com.cn.rmq.api.model.dto.cms.DataGrid;
import com.cn.rmq.api.model.dto.cms.system.SysResourceDTO;
import com.cn.rmq.api.model.enums.SysResourceTypeEnum;
import com.cn.rmq.api.model.po.SysResource;
import com.cn.rmq.api.model.po.SysUser;
import com.cn.rmq.api.service.ISysResourceService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>资源控制器</p>
 */
@Controller
@RequestMapping(value = "/sys_resource", method = RequestMethod.POST)
public class SysResourceController {

    private static final Logger LOG = Logger.getLogger(SysResourceController.class);
    private static final String[] IGNORES = {"resourceId", "createTime"};

    @Reference
    private ISysResourceService sysResourceService;

    /**
     * <p>获取管理页面</p>
     *
     * @return 管理页面路径
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(String name) {
        return "/sys-resource/" + name;
    }

    /**
     * <p>新增资源</p>
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    @RequiresPermissions("resource:create")
    public Object create(@ModelAttribute SysResourceDTO model, HttpSession session) {
        LOG.info("请求参数：" + model);
        // 资源名称验证
        List<SysResourceDTO> resources = sysResourceService.selectByName(model.getName());
        RspBase rspBase = new RspBase();
        if (null != resources && resources.size() > 0) {
            rspBase.code(Constants.CODE_FAILURE).msg("该资源已存在");
            LOG.warn("应答内容：" + rspBase);
        } else {
            SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
            model.setCreateUser(sysUser.getUserName());

            // 新增资源
            SysResource resource = new SysResource();
            BeanUtils.copyProperties(model, resource);
            resource.setResourceId(IdUtil.simpleUUID());
            resource.setCreateTime(new Date());
            sysResourceService.insertSelective(resource);
            BeanUtils.copyProperties(resource, model);
            rspBase.code(Constants.CODE_SUCCESS).msg("新增成功").data(model);
            LOG.info("应答内容：" + rspBase);
        }
        return rspBase;
    }

    /**
     * <p>删除资源</p>
     *
     * @param resourceIds
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("resourceIds") String resourceIds) {
        LOG.info("请求参数：resourceIds=" + resourceIds);
        List<String> list = Arrays.asList(resourceIds.split(","));
        int ret = sysResourceService.deleteByPrimaryKeys(list);
        RspBase rspBase = new RspBase();
        if (ret <= 0) {
            rspBase.code(Constants.CODE_FAILURE).msg("删除失败");
            LOG.warn("应答内容：" + rspBase);
        } else {
            rspBase.code(Constants.CODE_SUCCESS).msg("删除成功");
            LOG.info("应答内容：" + rspBase);
        }
        return rspBase;
    }

    /**
     * <p>更新资源</p>
     *
     * @param model {@link SysResourceDTO} 更新内容
     * @return 更新操作应答
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@ModelAttribute SysResourceDTO model, HttpSession session) {
        LOG.info("请求参数：" + model);
        // 资源验证
        SysResource resource = sysResourceService.selectByPrimaryKey(model.getResourceId());
        RspBase rspBase = new RspBase();
        if (null == resource) {
            rspBase.code(Constants.CODE_FAILURE).msg("资源不存在");
            LOG.warn("应答内容：" + rspBase);
            return rspBase;
        }

        // 更新用户
        if (!resource.getName().equals(model.getName())) {
            // 用户名验证
            List<SysResourceDTO> resources = sysResourceService.selectByName(model.getName());
            if (null != resources && resources.size() > 0) {
                rspBase.code(Constants.CODE_FAILURE).msg("资源：" + model.getName() + "已存在");
                LOG.warn("应答内容：" + rspBase);
                return rspBase;
            }
        }

        SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
        model.setUpdateUser(sysUser.getUserName());

        BeanUtils.copyProperties(model, resource, IGNORES);
        resource.setUpdateTime(new Date());
        sysResourceService.updateByPrimaryKey(resource);
        BeanUtils.copyProperties(resource, model);
        rspBase.code(Constants.CODE_SUCCESS).msg("修改成功").data(model);
        LOG.info("应答内容：" + rspBase);
        return rspBase;
    }

    /**
     * <p>查询资源</p>
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public Object searchResources(@ModelAttribute SysResourceDTO model) {
        LOG.info("请求参数：" + model);
        DataGrid datagrid = sysResourceService.selectByConditionPage(model);
        return datagrid;
    }

    @RequestMapping(value = "/menu")
    @ResponseBody
    public Object getMenu() {
        List<SysResourceDTO> resources = sysResourceService.selectByType(SysResourceTypeEnum.MENU.getValue());

//        LOG.info("应答内容：" + resources);
        return resources;
    }

    @RequestMapping(value = "/ztree")
    @ResponseBody
    public Object getZTree(@ModelAttribute SysResourceDTO model, HttpServletResponse response) {
        LOG.info("请求参数：" + model);
        model.setStatus((byte) 1);
        List<SysResourceDTO> resources = sysResourceService.selectByConditionAll(model);
//        LOG.info("应答内容：status=" + response.getStatus() + ", entity=" + resources);
        return resources;
    }
}
