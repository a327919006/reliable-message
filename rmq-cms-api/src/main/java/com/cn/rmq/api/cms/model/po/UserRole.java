package com.cn.rmq.api.cms.model.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserRole implements Serializable {
    private String id;

    private String sysUserId;

    private String roleId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}