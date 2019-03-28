package com.cn.rmq.service.impl;

import cn.hutool.core.util.IdUtil;
import com.cn.rmq.api.cms.model.dto.DataGrid;
import com.cn.rmq.api.cms.model.dto.system.SysRoleDTO;
import com.cn.rmq.api.cms.model.po.RoleResource;
import com.cn.rmq.api.cms.model.po.SysRole;
import com.cn.rmq.api.cms.service.ISysRoleService;
import com.cn.rmq.api.model.Constants;
import com.cn.rmq.dal.mapper.RoleResourceMapper;
import com.cn.rmq.dal.mapper.SysRoleMapper;
import com.cn.rmq.dal.mapper.UserRoleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>角色服务实现类</p>
 *
 * @author Chen Nan
 * @date 2019/3/11.
 */
@Service(timeout = Constants.SERVICE_TIMEOUT)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole, String>
        implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public DataGrid selectByConditionPage(SysRoleDTO model) {
        PageHelper.startPage(model.getPage(), model.getRows());
        List<SysRoleDTO> list = sysRoleMapper.selectByConditionPage(model);
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(list);
        dataGrid.setTotal((int) ((Page) list).getTotal());
        return dataGrid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SysRole> selectByConditionAll(SysRoleDTO model) {
        return sysRoleMapper.selectByConditionAll(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCondition(SysRoleDTO model) {
        return sysRoleMapper.countByCondition(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByPrimaryKeys(List<String> sysRoleIds) {
        roleResourceMapper.deleteByRoleIds(sysRoleIds);
        userRoleMapper.deleteByRoleIds(sysRoleIds);
        return sysRoleMapper.deleteByPrimaryKeys(sysRoleIds);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int deleteByPrimaryKey(String id) {
        return sysRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertSelective(SysRole record) {
        return sysRoleMapper.insertSelective(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysRole selectByPrimaryKey(String id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateByPrimaryKeySelective(SysRole record) {
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateByPrimaryKey(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleResource> getRoleResources(String roleId) {
        return roleResourceMapper.selectByRoleId(roleId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int allotRoleResources(String roleId, List<String> resourceIds) {
        roleResourceMapper.deleteByRoleIds(Arrays.asList(roleId));
        int ret = 0;
        for (String resourceId : resourceIds) {
            RoleResource roleResource = new RoleResource();
            roleResource.setRoleResourceId(IdUtil.simpleUUID());
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(resourceId);
            roleResource.setCreateTime(new Date());
            roleResourceMapper.insert(roleResource);
            ret++;
        }
        return ret;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SysRole> selectByUserId(String userId) {
        return sysRoleMapper.selectByUserId(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SysRole> selectByRoleName(String roleName) {
        return sysRoleMapper.selectByRoleName(roleName);
    }

    @Override
    public List<SysRole> selectByImsUserId(String imsUserId) {
        return sysRoleMapper.selectByImsUserId(imsUserId);
    }
}
