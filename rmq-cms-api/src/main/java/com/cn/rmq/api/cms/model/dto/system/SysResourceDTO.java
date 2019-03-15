package com.cn.rmq.api.cms.model.dto.system;

import com.cn.rmq.api.utils.json.deserializer.DateTimeDeserializer;
import com.cn.rmq.api.utils.json.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>资源记录</p>
 */
public class SysResourceDTO implements Serializable {

    private String resourceId;
    private String name;
    private String url;
    private Byte type;
    private String icon;
    private Integer priority;
    private String parentId;
    private String parentName;
    private String permission;
    private Byte status;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updateUser;
    private Integer page;
    private Integer rows;

    public String getResourceId() {
        return resourceId;
    }
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Byte getType() {
        return type;
    }
    public void setType(Byte type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Byte getStatus() {
        return status;
    }
    public void setStatus(Byte status) {
        this.status = status;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
        return "SysResourceDTO{" +
                "resourceId='" + resourceId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", icon='" + icon + '\'' +
                ", priority=" + priority +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", permission='" + permission + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", page=" + page +
                ", rows=" + rows +
                '}';
    }
}