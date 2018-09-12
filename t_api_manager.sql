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

 Date: 12/09/2018 20:58:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_api_manager
-- ----------------------------
DROP TABLE IF EXISTS `t_api_manager`;
CREATE TABLE `t_api_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `casename` varchar(100) NOT NULL,
  `path` varchar(200) DEFAULT NULL,
  `request_method` varchar(50) DEFAULT NULL,
  `request_params` varchar(500) DEFAULT NULL,
  `request_body` text,
  `response_code` int(11) DEFAULT NULL,
  `response_body` text,
  `jsonpath` varchar(100) DEFAULT NULL,
  `except_value` varchar(100) DEFAULT NULL,
  `available` bit(1) DEFAULT b'1',
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_api_manager
-- ----------------------------
BEGIN;
INSERT INTO `t_api_manager` VALUES (2, 'getCorrelators', 'api/v1/apama/component', 'GET', 'ComponentEnum=CORRELATOR', '', 200, NULL, '.code', '0', b'1', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
