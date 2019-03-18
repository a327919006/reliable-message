package com.cn.rmq.dal.mapper;

import java.util.List;

/**
 * <p>Title: BaseMapper</p>
 * <p>Description: 基本mapper接口</p>
 *
 * @param <T>  记录数据类型
 * @param <PK> 记录主键类型
 * @author ChenNan
 */
public interface BaseMapper<T, PK> {

    /**
     * 根据主键删除
     * @param id 根据主键删除
     * @return 操作数量
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 根据条件删除
     * @param record 删除条件
     * @return 操作数量
     */
    int deleteByCondition(T record);

    /**
     * 插入数据
     * @param record 对象数据
     * @return 操作数量
     */
    int insert(T record);

    /**
     * 选择性插入数据
     * @param record 对象数据
     * @return 操作数量
     */
    int insertSelective(T record);

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 对象
     */
    T selectByPrimaryKey(PK id);

    /**
     * 根据主键选择性更新数据
     * @param record 对象数据
     * @return 操作数量
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 根据主键更新数据
     * @param record 对象数据
     * @return 操作数量
     */
    int updateByPrimaryKey(T record);

    /**
     * 获取数量
     * @param record 对象数据
     * @return 数量
     */
    int count(T record);

    /**
     * 获取单条数据
     * @param record 对象数据
     * @return 对象
     */
    T get(T record);

    /**
     * 获取列表
     * @param record 对象数据
     * @return 对象列表
     */
    List<T> list(T record);
}
