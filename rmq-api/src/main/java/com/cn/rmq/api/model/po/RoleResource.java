package com.cn.rmq.api.model.po;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoleResource implements Serializable {
    private String roleResourceId;

    private String roleId;

    private String resourceId;

    private Date createTime;

    private Date updateTime;
}