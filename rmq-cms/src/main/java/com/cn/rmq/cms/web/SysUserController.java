package com.cn.rmq.cms.web;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.system.SysResourceDTO;
import com.cn.rmq.api.cms.model.dto.system.SysUserDTO;
import com.cn.rmq.api.cms.model.po.SysUser;
import com.cn.rmq.api.cms.model.po.UserRole;
import com.cn.rmq.api.cms.service.ISysUserService;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.api.model.dto.RspBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>系统用户控制器</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Controller
@RequestMapping(value = "/sys_user", method = RequestMethod.POST)
@Slf4j
public class SysUserController {
    private static final String[] IGNORES = {"sysUserId", "createTime"};

    @Reference
    private ISysUserService sysUserService;

    /**
     * <p>获取管理页面</p>
     *
     * @return 管理页面路径
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String page(String name) {
        return "/sys-user/" + name;
    }

    /**
     * <p>新增用户</p>
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    public Object create(@ModelAttribute SysUser model, HttpSession session) {
        log.info("请求参数：" + model);
        // 用户名验证
        List<SysUser> users = sysUserService.selectByUserName(model.getUserName());
        RspBase rspBase = new RspBase();
        if (null != users && users.size() > 0) {
            rspBase.code(Constants.CODE_FAILURE).msg("该用户已存在");
            log.warn("应答内容：" + rspBase);
        } else {
            SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
            model.setCreateUser(sysUser.getUserName());

            // 新增用户
            SysUser newUser = new SysUser();
            BeanUtils.copyProperties(model, newUser);
            newUser.setSysUserId(IdUtil.simpleUUID());
            newUser.setUserPwd(SecureUtil.md5(newUser.getUserPwd()));
            newUser.setCreateTime(new Date());
            newUser.setUpdateTime(new Date());
            sysUserService.insertSelective(newUser);
            BeanUtils.copyProperties(newUser, model);
            rspBase.code(Constants.CODE_SUCCESS).msg("新增成功").data(model);
            log.info("应答内容：" + rspBase);
        }
        return rspBase;
    }

    /**
     * <p>删除用户</p>
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam("userIds") String userIds) {
        log.info("请求内容：" + userIds);
        List<String> list = Arrays.asList(userIds.split(","));
        int ret = sysUserService.deleteByPrimaryKeys(list);
        RspBase rspBase = new RspBase();
        if (ret <= 0) {
            rspBase.code(Constants.CODE_FAILURE).msg("删除失败");
            log.warn("应答内容：" + rspBase);
        } else {
            rspBase.code(Constants.CODE_SUCCESS).msg("删除成功");
            log.info("应答内容：" + rspBase);
        }
        return rspBase;
    }

    /**
     * <p>用户更新</p>
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@ModelAttribute SysUser model, HttpSession session) {
        log.info("请求参数：" + model);
        // 用户验证
        SysUser user = sysUserService.selectByPrimaryKey(model.getSysUserId());
        RspBase rspBase = new RspBase();
        if (null == user) {
            rspBase.code(Constants.CODE_FAILURE).msg("系统用户不存在");
            log.warn("应答内容：" + rspBase);
            return rspBase;
        }

        // 更新用户
        if (!user.getUserName().equals(model.getUserName())) {
            // 用户名验证
            List<SysUser> users = sysUserService.selectByUserName(model.getUserName());
            if (null != users && users.size() > 0) {
                rspBase.code(Constants.CODE_FAILURE).msg("用户：" + model.getUserName() + " 已存在");
                log.warn("应答内容：" + rspBase);
                return rspBase;
            }
        }

        SysUser sysUser = (SysUser) session.getAttribute(Constants.SESSION_USER);
        model.setUpdateUser(sysUser.getUserName());

        BeanUtils.copyProperties(model, user, IGNORES);
        user.setUpdateTime(new Date());
        sysUserService.updateByPrimaryKeySelective(user);
        BeanUtils.copyProperties(user, model);
        rspBase.code(Constants.CODE_SUCCESS).msg("修改成功").data(model);
        log.info("应答内容：" + rspBase);
        return rspBase;
    }

    /**
     * <p>用户查询</p>
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    public Object search(@ModelAttribute SysUserDTO model) {
        log.info("请求参数：" + model);
        // 过滤用户状态为全部的搜索条件
        if (Byte.valueOf((byte) -1).equals(model.getUserStatus())) {
            model.setUserStatus(null);
        }
        DataGrid dataGrid = sysUserService.selectByConditionPage(model);
        return dataGrid;
    }

    /**
     * <p>用户密码修改</p>
     */
    @RequestMapping(value = "/password/change")
    @ResponseBody
    public Object changePassword(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, HttpServletRequest request) {
        Object sessionObj = request.getSession().getAttribute(Constants.SESSION_USER);
        if (null == sessionObj) {
            return new ModelAndView("redirect:/login");
        }

        SysUser sysUser = sysUserService.selectByPrimaryKey(((SysUser) sessionObj).getSysUserId());
        RspBase rspBase = new RspBase();
        if (!sysUser.getUserPwd().equals(SecureUtil.md5(oldPwd))) {
            rspBase.code(Constants.CODE_FAILURE).msg("旧密码错误");
            log.info("应答内容：" + rspBase);
        } else {
            sysUser.setUserPwd(SecureUtil.md5(newPwd));
            sysUser.setUpdateTime(new Date());
            sysUserService.updateByPrimaryKeySelective(sysUser);
            rspBase.code(Constants.CODE_SUCCESS).msg("修改密码成功，请重新登录");
            request.getSession().removeAttribute(Constants.SESSION_USER);
            log.info("应答内容：" + rspBase);
        }
        return rspBase;
    }

    /**
     * <p>获取用户角色</p>
     *
     * @param userId 用户唯一标识
     */
    @RequestMapping(value = "/{userId}/roles")
    @ResponseBody
    public Object getUserRoles(@PathVariable("userId") String userId) {
        List<UserRole> userRoles = sysUserService.selectUserRoleByUserId(userId);
        return userRoles;
    }

    /**
     * <p>为用户重新分配角色</p>
     */
    @RequestMapping(value = "/{userId}/role/allot")
    @ResponseBody
    public Object allotUserRoles(@RequestParam(value = "roleIds") String roleIds, @PathVariable("userId") String userId) {
        log.info("请求参数：userId=" + userId + ", roleIds=" + roleIds);
        RspBase rspBase = new RspBase();
        if (userId.equals("512c06d31bff11e6948f6c0b84680048")) {
            return rspBase.code(Constants.CODE_FAILURE).msg("禁止修改系统账号角色");
        }
        sysUserService.allotUserRole(userId, Arrays.asList(roleIds.split(",")));
        rspBase.code(Constants.CODE_SUCCESS).msg("分配角色成功");
        log.info("应答内容：" + rspBase);
        return rspBase;
    }

    @RequestMapping(value = "/{userId}/menu")
    @ResponseBody
    public Object getUserMenu(@PathVariable("userId") String userId) {
        List<SysResourceDTO> userMenus = sysUserService.selectMenuByUserId(userId);
        return userMenus;
    }
}
