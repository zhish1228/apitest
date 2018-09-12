/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.15.41
 Source Server Type    : MySQL
 Source Server Version : 50636
 Source Host           : 192.168.15.41:20010
 Source Schema         : apitest

 Target Server Type    : MySQL
 Target Server Version : 50636
 File Encoding         : 65001

 Date: 12/09/2018 20:58:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_api_config
-- ----------------------------
DROP TABLE IF EXISTS `t_api_config`;
CREATE TABLE `t_api_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL COMMENT '测试服务ip地址',
  `port` int(11) NOT NULL COMMENT '测试服务端口',
  `environment` varchar(100) DEFAULT NULL COMMENT '环境说明',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_api_config
-- ----------------------------
BEGIN;
INSERT INTO `t_api_config` VALUES (1, '192.168.15.195', 8009, 'dev', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
