package com.cn.rmq.api.cms.model.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RoleResource implements Serializable {
    private String roleResourceId;

    private String roleId;

    private String resourceId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}