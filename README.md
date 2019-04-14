<p align="center">
<img src="https://www.showdoc.cc/server/api/common/visitfile/sign/51a6c9fb88d79eb8adfcd4256e7f6a6e?showdoc=.jpg" ></img>
</p>

<p align="center">
    <a target="_blank" href="https://search.maven.org/search?q=g:%22com.gitee.nuliing%22%20AND%20a:%22rmq-api%22">
        <img src="https://img.shields.io/maven-central/v/com.gitee.nuliing/rmq-api.svg?label=Maven%20Central" ></img>
    </a>
    <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0.html">
        <img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg" ></img>
    </a>
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html">
        <img src="https://img.shields.io/badge/JDK-1.8+-green.svg" ></img>
    </a>
</p>

------------

#### 介绍

**RMQ**（reliable-message-queue）是**基于可靠消息的最终一致性**的分布式事务解决方案。

# [中文文档](https://www.showdoc.cc/rmq "中文文档")
RMQ对接示例：
- https://gitee.com/NuLiing/reliable-message-samples
- https://github.com/a327919006/reliable-message-samples

## 框架定位
- RMQ本身不生产消息队列，只是消息的搬运工。
- RMQ框架提供消息预发送、消息发送、消息确认、消息恢复、消息管理等功能，结合成熟的消息中间件，解决分布式事务，达到数据最终一致性。

------------

#### Maven模块描述

| 模块名称 | 描述 |
| --- | --- |
| rmq-api | 提供业务系统调用的RMQ服务接口 |
| rmq-service-api | 基础消息服务接口、系统工具类、实体类封装 |
| rmq-service | RMQ服务接口实现、基础消息服务接口实现、消息管理子系统服务接口实现 |
| rmq-schedule-api | 消息确认子系统、消息恢复子系统服务接口 |
| rmq-schedule | 消息确认子系统，与上游业务系统确认消息是否发送<br>消息恢复子系统，重新发送消息给下游业务 |
| rmq-cms-api | 消息管理子系统服务接口、实体类封装 |
| rmq-cms | 消息管理子系统，提供消息管理后台 |
| rmq-dal | 数据库访问层： sql语句|

------------

#### 更多

| 框架 | 描述 | 状态 |
| --- | --- |
| [cn-rmq](https://gitee.com/NuLiing/reliable-message "cn-rmq") | 基于可靠消息的最终一致性方案 | 已发布 |
| [cn-ben](https://gitee.com/NuLiing/cn-ben "cn-ben") | 最大努力通知方案 | 开发中 |
| cn-tcc | TCC方案 | 计划中 |
| cn-dts | 三种方案整合 | 计划中 |
