package com.cn.rmq.api.cms.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {
    private String sysUserId;

    private Integer areaId;

    private String userName;

    private Byte userStatus;

    private String userPhone;

    private String userEmail;

    private String userPwd;

    private Byte userType;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
}