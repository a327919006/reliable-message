package com.cn.rmq.api.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Queue implements Serializable {
    private String id;

    private String businessName;

    private String consumerQueue;

    private String checkUrl;

    private Integer checkDuration;

    private Short checkTimeout;

    private String createUser;

    private LocalDateTime createTime;

    private String updateUser;

    private LocalDateTime updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getConsumerQueue() {
        return consumerQueue;
    }

    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
    }

    public String getCheckUrl() {
        return checkUrl;
    }

    public void setCheckUrl(String checkUrl) {
        this.checkUrl = checkUrl;
    }

    public Integer getCheckDuration() {
        return checkDuration;
    }

    public void setCheckDuration(Integer checkDuration) {
        this.checkDuration = checkDuration;
    }

    public Short getCheckTimeout() {
        return checkTimeout;
    }

    public void setCheckTimeout(Short checkTimeout) {
        this.checkTimeout = checkTimeout;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}