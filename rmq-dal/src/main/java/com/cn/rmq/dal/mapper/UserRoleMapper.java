package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.model.po.UserRole;

import java.util.List;

/**
 * <p>用户角色关联表操作接口</p>
 *
 */
public interface UserRoleMapper extends BaseMapper<UserRole, String> {

    /**
     * <p>根据用户Id获取用户角色记录列表</p>
     *
     * @param sysUserId 系统用户唯一标识
     */
    List<UserRole> selectByUserId(String sysUserId);

    /**
     * 删除多个用户主键对应的关联记录
     *
     * @param sysUserIds 系统用户唯一标识列表
     * @return
     */
    int deleteByUserIds(List<String> sysUserIds);

    /**
     * 删除多个角色主键对应的关联记录
     *
     * @param sysRoleIds 角色唯一标识列表
     * @return
     */
    int deleteByRoleIds(List<String> sysRoleIds);
}