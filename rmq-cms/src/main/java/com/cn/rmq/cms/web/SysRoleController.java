package com.cn.rmq.cms.web;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.dto.RspBase;
import com.cn.rmq.api.model.dto.cms.DataGrid;
import com.cn.rmq.api.model.dto.cms.system.SysRoleDTO;
import com.cn.rmq.api.model.po.RoleResource;
import com.cn.rmq.api.model.po.SysRole;
import com.cn.rmq.api.model.po.SysUser;
import com.cn.rmq.api.service.ISysRoleService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>角色控制器</p>
 *
 */
@Controller
@RequestMapping(value = "/sys_role", method = RequestMethod.POST)
public class SysRoleController {

    private static final Logger LOG = Logger.getLogger(SysRoleController.class);
    private static final String[] IGNORES = {"roleId", "createTime"};

    @Reference
    private ISysRoleService sysRoleService;

    /**
     * <p>获取管理页面</p>
     *
     * @return 管理页面路径
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(String name) {
        return "/sys-role/" + name;
    }

    /**
     * <p>新增</p>
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    public Object create(@ModelAttribute SysRole model, HttpSession session) {
        LOG.info("请求参数：" + model);

        RspBase rspBase = new RspBase();
        // 验证角色名称
        List<SysRole> roles = sysRoleService.selectByRoleName(model.getRoleName());
        if (null != roles && roles.size() > 0) {
            rspBase.code(Constants.CODE_FAILURE).msg("该角色已存在");
            LOG.info("应答内容：" + rspBase);
            return rspBase;
        }

        SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
        model.setCreateUser(sysUser.getUserName());

        // 新增角色
        SysRole newRole = new SysRole();
        BeanUtils.copyProperties(model, newRole);
        newRole.setRoleId(IdUtil.simpleUUID());
        newRole.setCreateTime(new Date());
        sysRoleService.insert(newRole);
        rspBase.code(Constants.CODE_SUCCESS).msg("新增成功").data(newRole);
        LOG.info("应答内容：" + rspBase);
        return rspBase;
    }

    /**
     * <p>删除</p>
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("roleIds") String roleIds) {
        LOG.debug("请求参数：" + roleIds);
        List<String> list = Arrays.asList(roleIds.split(","));
        sysRoleService.deleteByPrimaryKeys(list);
        RspBase rspBase = new RspBase();
        rspBase.code(Constants.CODE_SUCCESS).msg("删除成功");
        LOG.debug("应答内容：" + rspBase);
        return rspBase;
    }

    /**
     * <p>修改</p>
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@ModelAttribute SysRole model, HttpSession session) {
        LOG.info("请求参数：" + model);

        RspBase rspBase = new RspBase();
        // 验证角色
        SysRole role = sysRoleService.selectByPrimaryKey(model.getRoleId());
        if (null == role) {
            rspBase.code(Constants.CODE_FAILURE).msg("修改失败：无对应角色信息");
            LOG.info("应答内容：" + rspBase);
            return rspBase;
        }

        // 更新角色信息
        if (!role.getRoleName().equals(model.getRoleName())) {
            // 用户名验证
            List<SysRole> roles = sysRoleService.selectByRoleName(model.getRoleName());
            if (null != roles && roles.size() > 0) {
                rspBase.code(Constants.CODE_FAILURE).msg("角色：" + model.getRoleName()  + " 已存在");
                LOG.warn("应答内容：" + rspBase);
                return rspBase;
            }
        }

        SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
        model.setUpdateUser(sysUser.getUserName());

        BeanUtils.copyProperties(model, role, IGNORES);
        role.setUpdateTime(new Date());
        sysRoleService.updateByPrimaryKeySelective(role);
        BeanUtils.copyProperties(role, model);
        rspBase.code(Constants.CODE_SUCCESS).msg("修改成功").data(model);
        LOG.info("应答内容：" + rspBase);
        return rspBase;
    }

    /**
     * <p>获取角色-资源列表</p>
     */
    @RequestMapping(value = "/{roleId}/resources")
    @ResponseBody
    public Object getRoleResources(@PathVariable("roleId") String roleId) {
        LOG.info("请求参数：roleId=" + roleId);
        List<RoleResource> roleResources = sysRoleService.getRoleResources(roleId);
        LOG.info("应答内容：" + roleResources);
        return roleResources;
    }

    /**
     * <p>获取列表</p>
     */
    @RequiresPermissions("role:search")
    @RequestMapping(value = "/search")
    @ResponseBody
    public Object search(@ModelAttribute SysRoleDTO model) {
        LOG.info("请求参数：" + model);
        DataGrid datagrid = sysRoleService.selectByConditionPage(model);
        return datagrid;
    }

    @RequestMapping(value = "/ztree")
    @ResponseBody
    public Object getZTree(@ModelAttribute SysRoleDTO model) {
        LOG.info("请求参数：" + model);
        List<SysRole> roles = sysRoleService.selectByConditionAll(model);
        LOG.info("应答内容：" + roles);
        return roles;
    }

    @RequestMapping(value = "/{roleId}/resources/allot")
    @ResponseBody
    public Object allotRoleResources(@RequestParam(value = "resourceIds") String resourceIds, @PathVariable("roleId") String roleId) {
        LOG.info("请求参数：roleId=" + roleId + ", resourceIds=" + resourceIds);
        RspBase rspBase = new RspBase();
        int ret = sysRoleService.allotRoleResources(roleId, Arrays.asList(resourceIds.split(",")));
        if (ret <= 0) {
            rspBase.code(Constants.CODE_FAILURE).msg("分配角色资源失败");
            LOG.warn("应答内容：" + rspBase);
            return rspBase;
        }
        rspBase.code(Constants.CODE_SUCCESS).msg("分配角色资源成功");
        LOG.info("应答内容：" + rspBase);
        return rspBase;
    }
}
