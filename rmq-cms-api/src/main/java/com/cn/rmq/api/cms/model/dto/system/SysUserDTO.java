package com.cn.rmq.api.cms.model.dto.system;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>系统用户记录</p>
 *
 * @author Chen Nan
 */
@Data
public class SysUserDTO implements Serializable {
    private String sysUserId;

    private Integer areaId;

    private String areaName;

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userPwd;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Byte userStatus;
    private Integer page;
    private Integer rows;
}