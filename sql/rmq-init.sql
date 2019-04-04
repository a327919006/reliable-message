/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.21 : Database - reliable-message
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`reliable-message` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `reliable-message`;

/*Table structure for table `t_message` */

DROP TABLE IF EXISTS `t_message`;

CREATE TABLE `t_message` (
  `id` char(32) NOT NULL,
  `consumer_queue` varchar(64) DEFAULT NULL COMMENT '消费队列',
  `message_body` longtext COMMENT '消息内容',
  `resend_times` smallint(5) unsigned DEFAULT '0' COMMENT '重发次数',
  `already_dead` tinyint(2) unsigned DEFAULT '0' COMMENT '消息是否死亡 1是0否',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '消息状态 0待确认 1发送中',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认发送时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `create_time_index` (`create_time`),
  KEY `consumer_queue_index` (`consumer_queue`),
  KEY `confirm_time_index` (`confirm_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_message` */

/*Table structure for table `t_queue` */

DROP TABLE IF EXISTS `t_queue`;

CREATE TABLE `t_queue` (
  `id` char(32) NOT NULL,
  `business_name` varchar(32) DEFAULT NULL COMMENT '业务名称',
  `consumer_queue` varchar(64) DEFAULT NULL COMMENT '消费队列',
  `check_url` varchar(256) DEFAULT NULL COMMENT '消息确认url',
  `check_duration` int(8) unsigned DEFAULT NULL COMMENT '消息确认条件，多长时间未确认的消息需进行确认（毫秒）',
  `check_timeout` smallint(4) unsigned DEFAULT NULL COMMENT '消息确认超时时间（毫秒）',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_queue` */

insert  into `t_queue`(`id`,`business_name`,`consumer_queue`,`check_url`,`check_duration`,`check_timeout`,`create_user`,`create_time`,`update_user`,`update_time`) values ('035249bb704d4c63a53c957fe6f9a435','支付业务','pay.queue','http://127.0.0.1:10010/pay/check',30000,1000,'admin','2019-03-16 20:13:12','admin','2019-03-18 17:08:34');

/*Table structure for table `t_role_resource` */

DROP TABLE IF EXISTS `t_role_resource`;

CREATE TABLE `t_role_resource` (
  `role_resource_id` varchar(32) NOT NULL DEFAULT '' COMMENT '角色资源唯一标识',
  `role_id` varchar(32) DEFAULT NULL COMMENT '角色唯一标识',
  `resource_id` varchar(32) DEFAULT NULL COMMENT '资源唯一标识',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '记录更新时间',
  PRIMARY KEY (`role_resource_id`),
  KEY `FK_ROLE_RESOURCE` (`role_id`),
  KEY `FK_RESOURCE_ROLE` (`resource_id`),
  CONSTRAINT `t_role_resource_ibfk_1` FOREIGN KEY (`resource_id`) REFERENCES `t_sys_resource` (`resource_id`),
  CONSTRAINT `t_role_resource_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `t_sys_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色资源关联表';

/*Data for the table `t_role_resource` */

insert  into `t_role_resource`(`role_resource_id`,`role_id`,`resource_id`,`create_time`,`update_time`) values ('04dc08af7619406097fc3ca39b7deb03','d382c33b1c0b11e6948f6c0b84680048','daa751edb8b64b518800b58e3f0c7594','2019-03-16 19:44:31',NULL),('088f634405ff4eb8ad0f86acd9e511c1','d382c33b1c0b11e6948f6c0b84680048','48704d77059245d4a9d20ba54d809cb7','2019-03-16 19:44:31',NULL),('60a00c7e74144418b1e8176dc3c31301','d382c33b1c0b11e6948f6c0b84680048','f58cb594a5aa486cadd1e6fb43e5116e','2019-03-16 19:44:31',NULL),('63a36422e1f4477fa12b45813e4521f5','d382c33b1c0b11e6948f6c0b84680048','9ac6f35bcdfc400e82430a08bd59edc3','2019-03-16 19:44:31',NULL),('67dbd7c5e1b5451397701ee34fc683e0','d382c33b1c0b11e6948f6c0b84680048','d69d329ebd3c41c2afaa48fe2ffb75d8','2019-03-16 19:44:31',NULL),('6dca0e6692764af8882be1da83deb28d','d382c33b1c0b11e6948f6c0b84680048','407c31d4a3d944b79182f8a2a754277b','2019-03-16 19:44:30',NULL),('90a940aff2ce403d9a1f8b2e92b2c4b2','d382c33b1c0b11e6948f6c0b84680048','493d929bef7a4be6bda5558722690dae','2019-03-16 19:44:30',NULL),('93e8a97b2f8542ec8927ad9c765ad219','d382c33b1c0b11e6948f6c0b84680048','7a1bd1851a8b4e13beed2c10e79a88b6','2019-03-16 19:44:31',NULL),('b2d543d60ccc46a49e9806b11c3263ef','d382c33b1c0b11e6948f6c0b84680048','3d561af000294a51abf2aa1e7a475341','2019-03-16 19:44:30',NULL),('be07c0d3f61740a787cce2beefcaf05f','d382c33b1c0b11e6948f6c0b84680048','b832b89a9aa7496cbd92281eefafdd8e','2019-03-16 19:44:31',NULL),('d7f1402cb93e45c985a3ffd268c0ffb0','d382c33b1c0b11e6948f6c0b84680048','4e2c72c62dff46db91ec291093f02433','2019-03-16 19:44:31',NULL),('dfdf1f646f844903b3cbe7b37f87558b','d382c33b1c0b11e6948f6c0b84680048','ec9bcf670edb495c9565e45b99469725','2019-03-16 19:44:31',NULL),('e3f53105247b4c32a1825d2fb9c35319','d382c33b1c0b11e6948f6c0b84680048','b791487c6bec48e39d09c48bf7224c65','2019-03-16 19:44:31',NULL),('e7b01bf3bc5746a98d3851862276e241','d382c33b1c0b11e6948f6c0b84680048','54d0193c578a4b32abed8cbfe8f75ef1','2019-03-16 19:44:31',NULL),('f50750b981e7430194f08e7447d8b90d','d382c33b1c0b11e6948f6c0b84680048','4847ac7ec54344bda4d54e40e7683174','2019-03-16 19:44:30',NULL),('fdfb01476fa547c9b325e80cd8b5e8bc','d382c33b1c0b11e6948f6c0b84680048','4d0244b0d8b14df28d7c7d5a39b48a4f','2019-03-16 19:44:31',NULL);

/*Table structure for table `t_sys_resource` */

DROP TABLE IF EXISTS `t_sys_resource`;

CREATE TABLE `t_sys_resource` (
  `resource_id` varchar(32) NOT NULL DEFAULT '' COMMENT '资源唯一标识',
  `name` varchar(64) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(256) DEFAULT NULL COMMENT '资源路径',
  `type` tinyint(11) DEFAULT NULL COMMENT '资源类型（0：菜单；1：按钮）',
  `icon` varchar(256) DEFAULT NULL COMMENT '资源图标',
  `priority` int(11) DEFAULT NULL COMMENT '资源显示顺序',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '资源父编号',
  `permission` varchar(128) DEFAULT NULL COMMENT '权限字符串',
  `status` tinyint(11) DEFAULT NULL COMMENT '资源状态（0：禁用；1：启用）',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '资源创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '资源更新时间',
  PRIMARY KEY (`resource_id`),
  KEY `resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `t_sys_resource` */

insert  into `t_sys_resource`(`resource_id`,`name`,`url`,`type`,`icon`,`priority`,`parent_id`,`permission`,`status`,`create_user`,`create_time`,`update_user`,`update_time`) values ('3d561af000294a51abf2aa1e7a475341','角色分配','role/allot',1,'images/icon/yellow_tag.png',1,'407c31d4a3d944b79182f8a2a754277b','user-role:allot',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('407c31d4a3d944b79182f8a2a754277b','管理员管理','sys_user/page?name=list',0,'images/icon/user.png',4,'493d929bef7a4be6bda5558722690dae','user:search',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('4847ac7ec54344bda4d54e40e7683174','资源管理','sys_resource/page?name=list',0,'images/icon/resource.png',5,'493d929bef7a4be6bda5558722690dae','resource:search',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('48704d77059245d4a9d20ba54d809cb7','查询资源','resource/search',1,'images/icon/yellow_tag.png',1,'4847ac7ec54344bda4d54e40e7683174','resource:search',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('493d929bef7a4be6bda5558722690dae','系统管理','',0,'images/icon/setting.png',20,'','',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('4d0244b0d8b14df28d7c7d5a39b48a4f','消息管理','page/message/list',0,'images/icon/yellow_tag.png',1,'','',1,NULL,'2019-03-15 10:31:37','admin','2019-03-15 15:34:22'),('4e2c72c62dff46db91ec291093f02433','删除角色','role/delete',1,'images/icon/yellow_tag.png',1,'ec9bcf670edb495c9565e45b99469725','role:delete',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('54d0193c578a4b32abed8cbfe8f75ef1','修改资源','resource/update',1,'images/icon/yellow_tag.png',1,'4847ac7ec54344bda4d54e40e7683174','resource:update',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('7a1bd1851a8b4e13beed2c10e79a88b6','新增资源','resource/create',1,'images/icon/yellow_tag.png',1,'4847ac7ec54344bda4d54e40e7683174','resource:create',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('9ac6f35bcdfc400e82430a08bd59edc3','查询角色','role/search',1,'images/icon/yellow_tag.png',1,'ec9bcf670edb495c9565e45b99469725','role:search',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('b791487c6bec48e39d09c48bf7224c65','修改角色','role:update',1,'images/icon/yellow_tag.png',1,'ec9bcf670edb495c9565e45b99469725','role:update',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('b832b89a9aa7496cbd92281eefafdd8e','资源分配','resource/allot',1,'images/icon/yellow_tag.png',1,'ec9bcf670edb495c9565e45b99469725','role-resource:allot',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('d69d329ebd3c41c2afaa48fe2ffb75d8','新增角色','role/create',1,'images/icon/yellow_tag.png',1,'ec9bcf670edb495c9565e45b99469725','role:create',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('daa751edb8b64b518800b58e3f0c7594','删除资源','resource/delete',1,'images/icon/yellow_tag.png',1,'4847ac7ec54344bda4d54e40e7683174','resource:delete',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('ec9bcf670edb495c9565e45b99469725','角色管理','sys_role/page?name=list',0,'images/icon/role.png',5,'493d929bef7a4be6bda5558722690dae','',1,NULL,'2019-01-01 00:00:00',NULL,'2019-01-01 00:00:00'),('f58cb594a5aa486cadd1e6fb43e5116e','队列管理','page/queue/list',0,'images/icon/yellow_tag.png',2,'','',1,'admin','2019-03-16 19:44:25',NULL,NULL);

/*Table structure for table `t_sys_role` */

DROP TABLE IF EXISTS `t_sys_role`;

CREATE TABLE `t_sys_role` (
  `role_id` varchar(32) NOT NULL COMMENT '角色编号',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态(1:正常 -1:停用)',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`role_id`,`role_name`,`status`,`create_user`,`create_time`,`update_user`,`update_time`) values ('d382c33b1c0b11e6948f6c0b84680048','超级管理员',1,NULL,'2016-05-17 16:46:44',NULL,'2017-04-21 21:33:50');

/*Table structure for table `t_sys_user` */

DROP TABLE IF EXISTS `t_sys_user`;

CREATE TABLE `t_sys_user` (
  `sys_user_id` varchar(32) NOT NULL COMMENT '系统用户编号',
  `area_id` int(11) DEFAULT NULL COMMENT '地区编号',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `user_status` tinyint(4) DEFAULT NULL COMMENT '用户状态(0:待审核 1:审核通过 2:审核不通过 -1:停用)',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '用户电话',
  `user_email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `user_type` tinyint(2) DEFAULT '2' COMMENT '用户类型 2:管理员',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sys_user_id`),
  KEY `FK_user_area` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `t_sys_user` */

insert  into `t_sys_user`(`sys_user_id`,`area_id`,`user_name`,`user_status`,`user_phone`,`user_email`,`user_pwd`,`user_type`,`create_user`,`create_time`,`update_user`,`update_time`) values ('512c06d31bff11e6948f6c0b84680048',100000,'admin',1,'111','','21232f297a57a5a743894a0e4a801fc3',2,NULL,'2016-05-17 15:17:40','admin','2018-10-17 15:33:28');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `id` varchar(32) NOT NULL COMMENT '系统编号',
  `sys_user_id` varchar(32) NOT NULL COMMENT '系统用户编号',
  `role_id` varchar(32) NOT NULL COMMENT '角色编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_user` (`sys_user_id`),
  KEY `FK_role_2` (`role_id`),
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `t_sys_role` (`role_id`),
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`sys_user_id`) REFERENCES `t_sys_user` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`id`,`sys_user_id`,`role_id`,`create_time`,`update_time`) values ('1','512c06d31bff11e6948f6c0b84680048','d382c33b1c0b11e6948f6c0b84680048','2018-11-01 15:15:39',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
