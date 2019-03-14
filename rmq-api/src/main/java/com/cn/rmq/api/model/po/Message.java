package com.cn.rmq.api.model.po;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String id;

    private String consumerQueue;

    private Short resendTimes;

    private Byte alreadyDead;

    private Byte status;

    private Date createTime;

    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}