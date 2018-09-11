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

 Date: 11/09/2018 16:42:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for spec_test
-- ----------------------------
DROP TABLE IF EXISTS `spec_test`;
CREATE TABLE `spec_test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `casename` varchar(45) DEFAULT NULL,
  `host` varchar(300) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `method` varchar(45) DEFAULT NULL,
  `request_params` varchar(450) DEFAULT NULL,
  `request_body` varchar(450) DEFAULT NULL,
  `response_code` int(11) DEFAULT NULL,
  `response_body` varchar(450) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of spec_test
-- ----------------------------
BEGIN;
INSERT INTO `spec_test` VALUES (28, NULL, '192.168.15.43', 10081, '/api/v1/product', 'GET', 'area=FOREIGN&productCode=&productName=&productType=&startTime=&endTime=&operatorRealName=&riskManagerRealName=&page=', '', 200, NULL, 'create at : 2017-10-13 16:47:06');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
