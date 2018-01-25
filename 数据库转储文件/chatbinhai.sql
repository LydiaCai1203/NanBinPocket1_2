/*
Navicat MySQL Data Transfer

Source Server         : caiqj
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : chatbinhai

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-01-02 22:28:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for avatar
-- ----------------------------
DROP TABLE IF EXISTS `avatar`;
CREATE TABLE `avatar` (
  `imgID` int(11) NOT NULL AUTO_INCREMENT,
  `imgName` varchar(255) NOT NULL,
  PRIMARY KEY (`imgID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of avatar
-- ----------------------------
INSERT INTO `avatar` VALUES ('1', 'apple_pic');
INSERT INTO `avatar` VALUES ('2', 'banana_pic');
INSERT INTO `avatar` VALUES ('3', 'cherry_pic');
INSERT INTO `avatar` VALUES ('4', 'grape_pic');
INSERT INTO `avatar` VALUES ('5', 'mango_pic');
INSERT INTO `avatar` VALUES ('6', 'orange_pic');
INSERT INTO `avatar` VALUES ('7', 'pear_pic');
INSERT INTO `avatar` VALUES ('8', 'pineapple_pic');
INSERT INTO `avatar` VALUES ('9', 'watermelon_pic');

-- ----------------------------
-- Table structure for chatlog
-- ----------------------------
DROP TABLE IF EXISTS `chatlog`;
CREATE TABLE `chatlog` (
  `logID` int(255) NOT NULL AUTO_INCREMENT,
  `senderID` char(8) NOT NULL,
  `receiverID` char(8) NOT NULL,
  `content` varchar(200) NOT NULL,
  `pubTime` datetime NOT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chatlog
-- ----------------------------
INSERT INTO `chatlog` VALUES ('1', '15990001', '15990046', 'Hello,Jerry', '2017-12-18 19:44:23');
INSERT INTO `chatlog` VALUES ('2', '15990046', '15990001', 'Hello,Tom', '2017-12-18 19:44:44');
INSERT INTO `chatlog` VALUES ('3', '15990001', '15990046', 'How are you?', '2017-12-18 19:45:20');
INSERT INTO `chatlog` VALUES ('4', '15990046', '15990001', 'I am fine,Thanks', '2017-12-18 19:46:38');
INSERT INTO `chatlog` VALUES ('8', '15990002', '15990046', 'I am bulky', '2017-12-28 19:08:04');
INSERT INTO `chatlog` VALUES ('9', '15990002', '15990001', 'I love programming', '2017-12-28 19:19:02');
INSERT INTO `chatlog` VALUES ('10', '15990003', '15990046', 'look for my dog', '2017-12-28 19:20:14');
INSERT INTO `chatlog` VALUES ('11', '15990046', '15990001', 'littleFrag', '2017-12-28 20:07:57');
INSERT INTO `chatlog` VALUES ('25', '15990046', '15990001', 'hello!Dude~', '2018-01-02 12:51:33');
INSERT INTO `chatlog` VALUES ('31', '15990046', '15990001', '', '2018-01-02 13:00:27');
INSERT INTO `chatlog` VALUES ('32', '15990046', '15990001', '....', '2018-01-02 13:00:35');
INSERT INTO `chatlog` VALUES ('33', '15990046', '15990001', 'Hi!Dude~', '2018-01-02 13:20:20');
INSERT INTO `chatlog` VALUES ('34', '15990001', '15990046', 'Hizzzzzzzzcai', '2018-01-02 13:21:03');
INSERT INTO `chatlog` VALUES ('35', '15990046', '15990001', '?oop', '2018-01-02 13:21:21');
INSERT INTO `chatlog` VALUES ('36', '15990001', '15990046', 'Yes', '2018-01-02 13:22:09');
INSERT INTO `chatlog` VALUES ('37', '15990046', '15990001', 'eneneb', '2018-01-02 13:22:18');

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `friendImgID` int(11) NOT NULL,
  `friendName` varchar(255) NOT NULL,
  `friendOfWho` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('3', 'Tom', '15990046', '1');
INSERT INTO `friend` VALUES ('6', 'Jerry', '15990046', '2');
INSERT INTO `friend` VALUES ('4', 'Sara', '15990046', '3');

-- ----------------------------
-- Table structure for stuinfo
-- ----------------------------
DROP TABLE IF EXISTS `stuinfo`;
CREATE TABLE `stuinfo` (
  `stuID` char(8) NOT NULL,
  `stuName` varchar(20) NOT NULL,
  `stuImg` int(255) DEFAULT '0',
  `stuPwd` char(6) NOT NULL,
  `stuStatus` char(1) DEFAULT 'N',
  PRIMARY KEY (`stuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stuinfo
-- ----------------------------
INSERT INTO `stuinfo` VALUES ('15990001', 'Tom', '3', 'Tom', 'N');
INSERT INTO `stuinfo` VALUES ('15990002', 'Jerry', '6', 'Jerry', 'N');
INSERT INTO `stuinfo` VALUES ('15990003', 'Sara', '4', 'Sara', 'N');
INSERT INTO `stuinfo` VALUES ('15990046', '蔡箐菁', '3', '蔡箐菁', 'N');
