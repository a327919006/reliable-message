package com.cn.rmq.dal.mapper;

import com.cn.rmq.api.cms.model.dto.system.SysUserDTO;
import com.cn.rmq.api.cms.model.po.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser, String> {
    /**
     * <p>根据用户名和密码获取用户信息</p>
     *
     * @param username 用户名
     * @param password 密码
     * @return {@link SysUser} 用户信息
     */
    SysUser selectByUserNameAndPassWord(@Param("username") String username, @Param("password") String password);

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
    List<SysUserDTO> selectByConditionPage(@Param("model") SysUserDTO model);

    /**
     * <p>根据查询条件统计用户记录数</p>
     *
     * @param model 查询条件
     */
    int countByCondition(SysUserDTO model);
}