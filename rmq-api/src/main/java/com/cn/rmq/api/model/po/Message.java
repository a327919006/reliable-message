package com.cn.rmq.api.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String id;

    private String consumerQueue;

    private Short resendTimes;

    private Byte alreadyDead;

    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime confirmTime;

    private LocalDateTime updateTime;

    private String messageBody;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsumerQueue() {
        return consumerQueue;
    }

    public void setConsumerQueue(String consumerQueue) {
        this.consumerQueue = consumerQueue;
    }

    public Short getResendTimes() {
        return resendTimes;
    }

    public void setResendTimes(Short resendTimes) {
        this.resendTimes = resendTimes;
    }

    public Byte getAlreadyDead() {
        return alreadyDead;
    }

    public void setAlreadyDead(Byte alreadyDead) {
        this.alreadyDead = alreadyDead;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(LocalDateTime confirmTime) {
        this.confirmTime = confirmTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}