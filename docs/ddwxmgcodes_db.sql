/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : ddwxmgcodes_db

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2018-04-10 13:57:59
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `sec_admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `sec_admin_user`;
CREATE TABLE `sec_admin_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `logini_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录账号',
  `pwd` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
  `qq` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ',
  `weixinNo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '微信号码',
  `headImg` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '用户图像',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `enable_flag` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of sec_admin_user
-- ----------------------------
INSERT INTO `sec_admin_user` VALUES ('1', 'admin01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '11@qq.com', '158308023805', null, '1', null, null, null, null);
INSERT INTO `sec_admin_user` VALUES ('3', null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `upm_groups`
-- ----------------------------
DROP TABLE IF EXISTS `upm_groups`;
CREATE TABLE `upm_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '分组名称',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `enable_flag` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of upm_groups
-- ----------------------------

-- ----------------------------
-- Table structure for `upm_role`
-- ----------------------------
DROP TABLE IF EXISTS `upm_role`;
CREATE TABLE `upm_role` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT 'id',
  `role_code` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '角色编码',
  `role_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `enable_flag` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否有效',
  `lock_status` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '是否加锁',
  `role_desc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of upm_role
-- ----------------------------

-- ----------------------------
-- Table structure for `upm_user`
-- ----------------------------
DROP TABLE IF EXISTS `upm_user`;
CREATE TABLE `upm_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `logini_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录账号',
  `pwd` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
  `user_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `address` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `mobile` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `enable_flag` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `org_desc` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构',
  `email` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of upm_user
-- ----------------------------
INSERT INTO `upm_user` VALUES ('1', null, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', null, null, '15838028035', null, null, null, null, null, null, null);
INSERT INTO `upm_user` VALUES ('2', 'user2', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', null, null, '15838028033', null, null, null, null, null, null, null);


-- ----------------------------
-- Table structure for `upm_groups`
-- ----------------------------
DROP TABLE IF EXISTS `sec_groups`;
CREATE TABLE `sec_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '分组名称',
  `create_by` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `enable_flag` datetime DEFAULT NULL,
  `parent_id` int(11)  NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
