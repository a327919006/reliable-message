package com.cn.rmq.api.cms.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRole implements Serializable {
    private String roleId;

    private String roleName;

    private Byte status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}