package com.cn.rmq.api.service;

import com.cn.rmq.api.model.dto.cms.DataGrid;
import com.cn.rmq.api.model.dto.cms.system.SysResourceDTO;
import com.cn.rmq.api.model.dto.cms.system.SysUserDTO;
import com.cn.rmq.api.model.po.SysUser;
import com.cn.rmq.api.model.po.UserRole;

import java.util.List;

/**
 * 系统用户服务接口
 */
public interface ISysUserService extends IBaseService<SysUser, String> {

    /**
     * <p>根据用户名和密码获取用户信息</p>
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link SysUser} 用户信息
     */
    SysUser selectByUserNameAndPassWord(String username, String password);

    /**
     * <p>根据用户名获取用户信息</p>
     *
     * @param username 用户名
     * @return {@link SysUser} 用户信息
     */
    List<SysUser> selectByUserName(String username);

    /**
     * <p>删除多个主键对应的用户记录</p>
     *
     * @param userIds 用户id列表
     * @return
     */
    int deleteByPrimaryKeys(List<String> userIds);

    /**
     * <p>根据查询条件获取用户分页</p>
     *
     * @param model 查询条件
     */
    DataGrid selectByConditionPage(SysUserDTO model);

    /**
     * <p>根据查询条件统计用户记录数</p>
     *
     * @param model 查询条件
     */
    int countByCondition(SysUserDTO model);

    /**
     * <p>根据用户唯一标识查询用户角色关联记录</p>
     *
     * @param userId 用户唯一标识
     * @return
     */
    List<UserRole> selectUserRoleByUserId(String userId);

    /**
     * <p>分配用户角色</p>
     *
     * @param sysUserId  用户唯一标识
     * @param sysRoleIds 角色唯一标识列表
     * @return
     */
    int allotUserRole(String sysUserId, List<String> sysRoleIds);

    /**
     * <p>根据用户唯一标识获取用户的菜单</p>
     *
     * @param sysUserId 用户唯一标识
     * @return {@link List< SysResourceDTO >} 用户菜单列表
     */
    List<SysResourceDTO> selectMenuByUserId(String sysUserId);

    List<SysResourceDTO> selectMenuByImsUserId(String imsUserId);


}
