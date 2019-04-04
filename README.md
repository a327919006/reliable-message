# reliable-message

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/a327919006/reliable-message/blob/master/LICENSE)

#### 介绍

**reliable-message**（简称：RMQ）是**基于可靠消息的最终一致性**的分布式事务解决方案。文档编写中。

# [中文文档](https://www.showdoc.cc/rmq "中文文档")

###框架定位
RMQ本身不生产消息队列，只是消息的搬运工。
RMQ框架提供消息预发送、消息发送、消息确认、消息恢复、消息管理等功能，结合成熟的消息中间件，解决分布式事务，达到数据最终一致性。

------------

#### Maven模块描述

| 名称 | 描述 |
| --- | --- |
| rmq-api | 业务层RMQ服务接口 |
| rmq-service-api | 基础消息服务接口、系统工具类、实体类封装 |
| rmq-service | 基础消息服务接口实现 |
| rmq-schedule-api | 消息确认子系统、消息恢复子系统服务接口 |
| rmq-schedule | 消息确认子系统，与上游业务系统确认消息是否发送<br>消息恢复子系统，重新发送消息给下游业务 |
| rmq-cms-api | 消息管理子系统服务接口、实体类封装 |
| rmq-cms | 消息管理子系统，提供消息管理后台 |
| rmq-dal | 数据库访问层： sql语句|

------------




#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

