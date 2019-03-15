package com.cn.rmq.api.service;

import com.cn.rmq.api.model.dto.cms.DataGrid;
import com.cn.rmq.api.model.dto.cms.system.SysResourceDTO;
import com.cn.rmq.api.model.po.SysResource;

import java.util.List;

/**
 * <p>资源服务接口</p>
 */
public interface ISysResourceService extends IBaseService<SysResource, String> {

    /**
     * <p>根据条件获取资源分页</p>
     *
     * @param resourceReq 查询条件
     */
    DataGrid selectByConditionPage(SysResourceDTO resourceReq);

    /**
     * <p>根据条件获取资源列表</p>
     *
     * @param resourceReq 查询条件
     */
    List<SysResourceDTO> selectByConditionAll(SysResourceDTO resourceReq);

    /**
     * <p>根据条件统计资源记录数</p>
     *
     * @param resourceReq 查询条件
     */
    int countByCondition(SysResourceDTO resourceReq);

    /**
     * <p>根据主键列表删除指定的资源记录</p>
     *
     * @param sysResourceIds 资源主键列表
     * @return
     */
    int deleteByPrimaryKeys(List<String> sysResourceIds);

    /**
     * <p>根据用户唯一标识获取用户的菜单</p>
     *
     * @param sysUserId 用户唯一标识
     * @return {@link List< SysResource >} 用户菜单列表
     */
    List<SysResourceDTO> selectMenuByUserId(String sysUserId);

    /**
     * <p>获取指定类型的资源</p>
     *
     * @param type 资源类型
     * @return {@link List<SysResource>} 资源列表
     */
    List<SysResourceDTO> selectByType(Byte type);

    /**
     * <p>根据用户唯一标识获取对应资源列表</p>
     *
     * @param sysUserId 用户唯一标识
     * @return 资源列表
     */
    List<SysResource> selectByUserId(String sysUserId);

    /**
     * <p>根据资源名称获取对应资源列表</p>
     *
     * @param name 资源名称
     * @return 资源列表
     */
    List<SysResourceDTO> selectByName(String name);

    List<SysResource> selectByImsUserId(String imsUserId);


}
