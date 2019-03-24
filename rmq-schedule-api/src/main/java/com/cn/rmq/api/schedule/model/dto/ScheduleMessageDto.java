package com.cn.rmq.api.schedule.model.dto;

import cn.hutool.json.JSONUtil;
import com.cn.rmq.api.model.po.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/18.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class ScheduleMessageDto extends Message {
    /**
     * 创建时间起
     */
    private String createStartTime;

    /**
     * 创建时间止
     */
    private String createEndTime;

    /**
     * 分页页码
     */
    private Integer pageNum;
    /**
     * 分页数量
     */
    private Integer pageSize;
    /**
     * 是否需要计算总数
     */
    private Boolean count;
    /**
     * 排序
     */
    private String orderBy;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
