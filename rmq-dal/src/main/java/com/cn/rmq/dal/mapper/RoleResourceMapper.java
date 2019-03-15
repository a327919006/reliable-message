package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.cms.model.po.RoleResource;

import java.util.List;

/**
 * <p>角色资源数据库操作接口</p>
 *
 */
public interface RoleResourceMapper extends BaseMapper<RoleResource, String> {

    /**
     * <p>根据角色Id获取角色资源记录列表</p>
     *
     * @param sysRoleId 角色唯一标识
     * @return
     */
    List<RoleResource> selectByRoleId(String sysRoleId);

    /**
     * 删除多个主键对应的关联记录
     *
     * @param sysRoleIds 角色唯一标识列表
     * @return
     */
    int deleteByRoleIds(List<String> sysRoleIds);

    /**
     * 删除多个主键对应的关联记录
     *
     * @param sysResourceIds 资源唯一标识列表
     * @return
     */
    int deleteByResourceIds(List<String> sysResourceIds);
}