package com.cn.rmq.api.cms.model.dto.system;

import com.cn.rmq.api.utils.json.deserializer.DateTimeDeserializer;
import com.cn.rmq.api.utils.json.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>系统用户记录</p>
 */
public class SysUserDTO implements Serializable {

    private String sysUserId;

    private Integer areaId;

    private String areaName;

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userPwd;

    private Date createTime;

    private Date updateTime;

    private Byte userStatus;
    private Integer page;
    private Integer rows;

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonSerialize(using = DateTimeSerializer.class)
    public Date getUpdateTime() {
        return updateTime;
    }
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Byte userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "SysUserDTO{" +
                "sysUserId='" + sysUserId + '\'' +
                ", areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userStatus=" + userStatus +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}