package com.cn.rmq.api.cms.service;

import com.cn.rmq.api.cms.model.po.RoleResource;
import com.cn.rmq.api.cms.model.po.SysRole;
import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.system.SysRoleDTO;
import com.cn.rmq.api.service.IBaseService;

import java.util.List;

/**
 * <p>角色服务接口</p>
 */
public interface ISysRoleService extends IBaseService<SysRole, String> {

    /**
     * <p>根据查询条件获取用户分页</p>
     */
    DataGrid selectByConditionPage(SysRoleDTO model);

    /**
     * <p>根据查询条件获取用户列表</p>
     *
     * @param model 查询条件
     */
    List<SysRole> selectByConditionAll(SysRoleDTO model);

    /**
     * <p>根据查询条件统计用户记录数</p>
     *
     * @param model 查询条件
     */
    int countByCondition(SysRoleDTO model);

    /**
     * <p>根据主键列表删除指定的角色记录</p>
     *
     * @param sysRoleIds 角色主键列表
     * @return
     */
    int deleteByPrimaryKeys(List<String> sysRoleIds);

    /**
     * <p>根据角色唯一标识获取对应的角色资源</p>
     *
     * @param sysRoleId 角色唯一标识
     * @return
     */
    List<RoleResource> getRoleResources(String sysRoleId);

    /**
     * <p>分配角色资源</p>
     *
     * @param roleId         角色唯一标识
     * @param sysResourceIds 资源唯一标识列表
     * @return
     */
    int allotRoleResources(String roleId, List<String> sysResourceIds);

    /**
     * <p>根据用户唯一标识获取对应角色列表</p>
     *
     * @param sysUserId 用户唯一标识
     * @return 角色列表
     */
    List<SysRole> selectByUserId(String sysUserId);

    /**
     * <p>根据角色名称获取对应角色列表</p>
     *
     * @param roleName 角色名称
     * @return 角色列表
     */
    List<SysRole> selectByRoleName(String roleName);

    /**
     * 根据ims用户获取对应解决列表
     * @param imsUserId
     * @return
     */
    List<SysRole> selectByImsUserId(String imsUserId);
}
