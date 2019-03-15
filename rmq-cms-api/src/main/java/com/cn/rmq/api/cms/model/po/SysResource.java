package com.cn.rmq.api.cms.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysResource implements Serializable {
    private String resourceId;

    private String name;

    private String url;

    private Byte type;

    private String icon;

    private Integer priority;

    private String parentId;

    private String permission;

    private Byte status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

}