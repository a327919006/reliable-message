package com.cn.rmq.service.impl;

import com.cn.rmq.dal.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: BaseServiceImpl</p>
 * <p>Description: 基本服务实现类</p>
 * <p>
 * M mapper
 * T 对象类型
 * PK 主键类型
 *
 * @author Chen Nan
 */
public abstract class BaseServiceImpl<M extends BaseMapper, T, PK> {
    /**
     * 持久层对象
     */
    @Autowired
    protected M mapper;

    public int deleteByPrimaryKey(PK id) {
        return mapper.deleteByPrimaryKey(id);
    }

    public int insert(T record) {
        return mapper.insert(record);
    }

    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    public T selectByPrimaryKey(PK id) {
        return (T) mapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    public int count(T record) {
        return mapper.count(record);
    }

    public T get(T record) {
        return (T) mapper.get(record);
    }

    public List<T> list(T record) {
        return mapper.list(record);
    }
}
