package com.cn.rmq.api.cms.model.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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

    private LocalDateTime createTime;

    private String updateUser;

    private LocalDateTime updateTime;
}