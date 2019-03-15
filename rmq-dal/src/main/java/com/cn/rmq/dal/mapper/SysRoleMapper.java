package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.model.dto.cms.system.SysRoleDTO;
import com.cn.rmq.api.model.po.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole, String> {
    /**
     * <p>根据用户Id获取角色记录列表</p>
     *
     * @param userId 用户唯一标识
     * @return
     */
    List<SysRole> selectByUserId(String userId);

    /**
     * <p>根据查询条件获取角色分页</p>
     *
     * @param model 查询条件
     */
    List<SysRoleDTO> selectByConditionPage(@Param("model") SysRoleDTO model);

    /**
     * <p>根据查询条件获取角色列表</p>
     *
     * @param model 查询条件
     */
    List<SysRole> selectByConditionAll(SysRoleDTO model);

    /**
     * <p>根据查询条件统计角色记录数</p>
     *
     * @param model 查询条件
     */
    int countByCondition(SysRoleDTO model);

    /**
     * <p>根据主键列表删除指定的角色记录</p>
     *
     * @param roleIds 角色主键列表
     * @return
     */
    int deleteByPrimaryKeys(List<String> roleIds);

    /**
     * <p>根据角色名称获取对应角色列表</p>
     *
     * @param roleName 角色名称
     * @return 角色列表
     */
    List<SysRole> selectByRoleName(String roleName);

    List<SysRole> selectByImsUserId(String imsUserId);
}