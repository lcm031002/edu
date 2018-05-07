-- ----------------------------
-- Table structure for `jbpm4_execution`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_execution`;
CREATE TABLE `jbpm4_execution` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ACTIVITYNAME_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `HASVARS_` bit(1) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `HISACTINST_` bigint(20) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `INSTANCE_` bigint(20) DEFAULT NULL,
  `SUPEREXEC_` bigint(20) DEFAULT NULL,
  `SUBPROCINST_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  UNIQUE KEY `ID_` (`ID_`),
  KEY `IDX_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  KEY `IDX_EXEC_PARENT` (`PARENT_`),
  KEY `IDX_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `IDX_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `FK_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `FK_EXEC_PARENT` (`PARENT_`),
  KEY `FK_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `FK_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  CONSTRAINT `FK_EXEC_INSTANCE` FOREIGN KEY (`INSTANCE_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUBPI` FOREIGN KEY (`SUBPROCINST_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUPEREXEC` FOREIGN KEY (`SUPEREXEC_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*
Navicat MySQL Data Transfer

Source Server         : erp
Source Server Version : 50544
Source Host           : localhost:3306
Source Database       : erp

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2018-03-22 00:11:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ebs_accountorg_rel
-- ----------------------------
DROP TABLE IF EXISTS `ebs_accountorg_rel`;
CREATE TABLE `ebs_accountorg_rel` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `accountid` bigint(10) NOT NULL COMMENT '用户id',
  `orgid` bigint(10) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ebs_accountorg_rel
-- ----------------------------
INSERT INTO `ebs_accountorg_rel` VALUES (5, 1, 5);

-- ----------------------------
-- Table structure for ebs_rolemenu_rel
-- ----------------------------
DROP TABLE IF EXISTS `ebs_rolemenu_rel`;
CREATE TABLE `ebs_rolemenu_rel` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(10) NOT NULL COMMENT '角色id',
  `menu_id` varchar(15) NOT NULL COMMENT '模板id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of ebs_rolemenu_rel
-- ----------------------------
INSERT INTO `ebs_rolemenu_rel` VALUES ('1', '1', 'A17004');
INSERT INTO `ebs_rolemenu_rel` VALUES ('2', '1', 'A1100101');
INSERT INTO `ebs_rolemenu_rel` VALUES ('3', '1', 'A1100102');
INSERT INTO `ebs_rolemenu_rel` VALUES ('4', '1', 'A1100103');
INSERT INTO `ebs_rolemenu_rel` VALUES ('5', '1', 'A1100104');
INSERT INTO `ebs_rolemenu_rel` VALUES ('6', '1', 'A12002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('7', '1', 'A1200301');
INSERT INTO `ebs_rolemenu_rel` VALUES ('8', '1', 'A1200302');
INSERT INTO `ebs_rolemenu_rel` VALUES ('9', '1', 'A1200303');
INSERT INTO `ebs_rolemenu_rel` VALUES ('10', '1', 'A1200305');
INSERT INTO `ebs_rolemenu_rel` VALUES ('11', '1', 'A1200306');
INSERT INTO `ebs_rolemenu_rel` VALUES ('12', '1', 'A1200307');
INSERT INTO `ebs_rolemenu_rel` VALUES ('13', '1', 'A1200701');
INSERT INTO `ebs_rolemenu_rel` VALUES ('14', '1', 'A1200702');
INSERT INTO `ebs_rolemenu_rel` VALUES ('15', '1', 'A1200703');
INSERT INTO `ebs_rolemenu_rel` VALUES ('16', '1', 'A1200704');
INSERT INTO `ebs_rolemenu_rel` VALUES ('17', '1', 'A1200705');
INSERT INTO `ebs_rolemenu_rel` VALUES ('18', '1', 'A1200706');
INSERT INTO `ebs_rolemenu_rel` VALUES ('19', '1', 'A1200707');
INSERT INTO `ebs_rolemenu_rel` VALUES ('20', '1', 'A1200708');
INSERT INTO `ebs_rolemenu_rel` VALUES ('21', '1', 'A1200709');
INSERT INTO `ebs_rolemenu_rel` VALUES ('22', '1', 'A1200710');
INSERT INTO `ebs_rolemenu_rel` VALUES ('23', '1', 'A1200711');
INSERT INTO `ebs_rolemenu_rel` VALUES ('24', '1', 'A1200712');
INSERT INTO `ebs_rolemenu_rel` VALUES ('25', '1', 'A1300201');
INSERT INTO `ebs_rolemenu_rel` VALUES ('26', '1', 'A13003');
INSERT INTO `ebs_rolemenu_rel` VALUES ('27', '1', 'A13004');
INSERT INTO `ebs_rolemenu_rel` VALUES ('28', '1', 'A1300501');
INSERT INTO `ebs_rolemenu_rel` VALUES ('29', '1', 'A1300502');
INSERT INTO `ebs_rolemenu_rel` VALUES ('30', '1', 'A1300503');
INSERT INTO `ebs_rolemenu_rel` VALUES ('31', '1', 'A1300504');
INSERT INTO `ebs_rolemenu_rel` VALUES ('32', '1', 'A1300505');
INSERT INTO `ebs_rolemenu_rel` VALUES ('33', '1', 'A1300506');
INSERT INTO `ebs_rolemenu_rel` VALUES ('34', '1', 'A1300507');
INSERT INTO `ebs_rolemenu_rel` VALUES ('35', '1', 'A1300508');
INSERT INTO `ebs_rolemenu_rel` VALUES ('36', '1', 'A14001');
INSERT INTO `ebs_rolemenu_rel` VALUES ('37', '1', 'A1400206');
INSERT INTO `ebs_rolemenu_rel` VALUES ('38', '1', 'A1400207');
INSERT INTO `ebs_rolemenu_rel` VALUES ('39', '1', 'A1400208');
INSERT INTO `ebs_rolemenu_rel` VALUES ('40', '1', 'A1400209');
INSERT INTO `ebs_rolemenu_rel` VALUES ('41', '1', 'A1400210');
INSERT INTO `ebs_rolemenu_rel` VALUES ('42', '1', 'A1400211');
INSERT INTO `ebs_rolemenu_rel` VALUES ('43', '1', 'A1400212');
INSERT INTO `ebs_rolemenu_rel` VALUES ('44', '1', 'A14003');
INSERT INTO `ebs_rolemenu_rel` VALUES ('45', '1', 'A1400401');
INSERT INTO `ebs_rolemenu_rel` VALUES ('46', '1', 'A1400402');
INSERT INTO `ebs_rolemenu_rel` VALUES ('47', '1', 'A1400403');
INSERT INTO `ebs_rolemenu_rel` VALUES ('48', '1', 'A1400404');
INSERT INTO `ebs_rolemenu_rel` VALUES ('49', '1', 'A1400405');
INSERT INTO `ebs_rolemenu_rel` VALUES ('50', '1', 'A1400406');
INSERT INTO `ebs_rolemenu_rel` VALUES ('51', '1', 'A1400407');
INSERT INTO `ebs_rolemenu_rel` VALUES ('52', '1', 'A1400411');
INSERT INTO `ebs_rolemenu_rel` VALUES ('53', '1', 'A1400412');
INSERT INTO `ebs_rolemenu_rel` VALUES ('54', '1', 'A1400413');
INSERT INTO `ebs_rolemenu_rel` VALUES ('55', '1', 'A1400414');
INSERT INTO `ebs_rolemenu_rel` VALUES ('56', '1', 'A1400415');
INSERT INTO `ebs_rolemenu_rel` VALUES ('57', '1', 'A140050101');
INSERT INTO `ebs_rolemenu_rel` VALUES ('58', '1', 'A140050102');
INSERT INTO `ebs_rolemenu_rel` VALUES ('59', '1', 'A140050106');
INSERT INTO `ebs_rolemenu_rel` VALUES ('60', '1', 'A1400502');
INSERT INTO `ebs_rolemenu_rel` VALUES ('61', '1', 'A140050201');
INSERT INTO `ebs_rolemenu_rel` VALUES ('62', '1', 'A1400503');
INSERT INTO `ebs_rolemenu_rel` VALUES ('63', '1', 'A1400601');
INSERT INTO `ebs_rolemenu_rel` VALUES ('64', '1', 'A1400602');
INSERT INTO `ebs_rolemenu_rel` VALUES ('65', '1', 'A140060201');
INSERT INTO `ebs_rolemenu_rel` VALUES ('66', '1', 'A1400603');
INSERT INTO `ebs_rolemenu_rel` VALUES ('67', '1', 'A140060301');
INSERT INTO `ebs_rolemenu_rel` VALUES ('68', '1', 'A1400604');
INSERT INTO `ebs_rolemenu_rel` VALUES ('69', '1', 'A1400605');
INSERT INTO `ebs_rolemenu_rel` VALUES ('70', '1', 'A140060501');
INSERT INTO `ebs_rolemenu_rel` VALUES ('71', '1', 'A1400606');
INSERT INTO `ebs_rolemenu_rel` VALUES ('72', '1', 'A140060601');
INSERT INTO `ebs_rolemenu_rel` VALUES ('73', '1', 'A140060701');
INSERT INTO `ebs_rolemenu_rel` VALUES ('74', '1', 'A1400611');
INSERT INTO `ebs_rolemenu_rel` VALUES ('75', '1', 'A1400612');
INSERT INTO `ebs_rolemenu_rel` VALUES ('76', '1', 'A14007');
INSERT INTO `ebs_rolemenu_rel` VALUES ('77', '1', 'A1400801');
INSERT INTO `ebs_rolemenu_rel` VALUES ('78', '1', 'A1400802');
INSERT INTO `ebs_rolemenu_rel` VALUES ('79', '1', 'A140080301');
INSERT INTO `ebs_rolemenu_rel` VALUES ('80', '1', 'A1400804');
INSERT INTO `ebs_rolemenu_rel` VALUES ('81', '1', 'A1400805');
INSERT INTO `ebs_rolemenu_rel` VALUES ('82', '1', 'A14009');
INSERT INTO `ebs_rolemenu_rel` VALUES ('83', '1', 'A140100101');
INSERT INTO `ebs_rolemenu_rel` VALUES ('84', '1', 'A140100102');
INSERT INTO `ebs_rolemenu_rel` VALUES ('85', '1', 'A1401002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('86', '1', 'A140100202');
INSERT INTO `ebs_rolemenu_rel` VALUES ('87', '1', 'A140100203');
INSERT INTO `ebs_rolemenu_rel` VALUES ('88', '1', 'A140100204');
INSERT INTO `ebs_rolemenu_rel` VALUES ('89', '1', 'A140100205');
INSERT INTO `ebs_rolemenu_rel` VALUES ('90', '1', 'A140100206');
INSERT INTO `ebs_rolemenu_rel` VALUES ('91', '1', 'A140100207');
INSERT INTO `ebs_rolemenu_rel` VALUES ('92', '1', 'A140100208');
INSERT INTO `ebs_rolemenu_rel` VALUES ('93', '1', 'A140100209');
INSERT INTO `ebs_rolemenu_rel` VALUES ('94', '1', 'A140100210');
INSERT INTO `ebs_rolemenu_rel` VALUES ('95', '1', 'A140100211');
INSERT INTO `ebs_rolemenu_rel` VALUES ('96', '1', 'A140100212');
INSERT INTO `ebs_rolemenu_rel` VALUES ('97', '1', 'A140100213');
INSERT INTO `ebs_rolemenu_rel` VALUES ('98', '1', 'A140100214');
INSERT INTO `ebs_rolemenu_rel` VALUES ('99', '1', 'A140100215');
INSERT INTO `ebs_rolemenu_rel` VALUES ('100', '1', 'A140100216');
INSERT INTO `ebs_rolemenu_rel` VALUES ('101', '1', 'A140100217');
INSERT INTO `ebs_rolemenu_rel` VALUES ('102', '1', 'A140100218');
INSERT INTO `ebs_rolemenu_rel` VALUES ('103', '1', 'A140100219');
INSERT INTO `ebs_rolemenu_rel` VALUES ('104', '1', 'A140100220');
INSERT INTO `ebs_rolemenu_rel` VALUES ('105', '1', 'A140100221');
INSERT INTO `ebs_rolemenu_rel` VALUES ('106', '1', 'A140100222');
INSERT INTO `ebs_rolemenu_rel` VALUES ('107', '1', 'A140100223');
INSERT INTO `ebs_rolemenu_rel` VALUES ('108', '1', 'A140100224');
INSERT INTO `ebs_rolemenu_rel` VALUES ('109', '1', 'A140100225');
INSERT INTO `ebs_rolemenu_rel` VALUES ('110', '1', 'A140100301');
INSERT INTO `ebs_rolemenu_rel` VALUES ('111', '1', 'A140100401');
INSERT INTO `ebs_rolemenu_rel` VALUES ('112', '1', 'A1401101');
INSERT INTO `ebs_rolemenu_rel` VALUES ('113', '1', 'A1401102');
INSERT INTO `ebs_rolemenu_rel` VALUES ('114', '1', 'A1401201');
INSERT INTO `ebs_rolemenu_rel` VALUES ('115', '1', 'A1401202');
INSERT INTO `ebs_rolemenu_rel` VALUES ('116', '1', 'A140120301');
INSERT INTO `ebs_rolemenu_rel` VALUES ('117', '1', 'A15002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('118', '1', 'A15003');
INSERT INTO `ebs_rolemenu_rel` VALUES ('119', '1', 'A15004');
INSERT INTO `ebs_rolemenu_rel` VALUES ('120', '1', 'A15005');
INSERT INTO `ebs_rolemenu_rel` VALUES ('121', '1', 'A15006');
INSERT INTO `ebs_rolemenu_rel` VALUES ('122', '1', 'A16001');
INSERT INTO `ebs_rolemenu_rel` VALUES ('123', '1', 'A1600201');
INSERT INTO `ebs_rolemenu_rel` VALUES ('124', '1', 'A1600202');
INSERT INTO `ebs_rolemenu_rel` VALUES ('125', '1', 'A1600203');
INSERT INTO `ebs_rolemenu_rel` VALUES ('126', '1', 'A1600204');
INSERT INTO `ebs_rolemenu_rel` VALUES ('127', '1', 'A1600205');
INSERT INTO `ebs_rolemenu_rel` VALUES ('128', '1', 'A1600206');
INSERT INTO `ebs_rolemenu_rel` VALUES ('129', '1', 'A1600208');
INSERT INTO `ebs_rolemenu_rel` VALUES ('130', '1', 'A16004');
INSERT INTO `ebs_rolemenu_rel` VALUES ('131', '1', 'A1600501');
INSERT INTO `ebs_rolemenu_rel` VALUES ('132', '1', 'A1600502');
INSERT INTO `ebs_rolemenu_rel` VALUES ('133', '1', 'A1600503');
INSERT INTO `ebs_rolemenu_rel` VALUES ('134', '1', 'A1600504');
INSERT INTO `ebs_rolemenu_rel` VALUES ('135', '1', 'A1600505');
INSERT INTO `ebs_rolemenu_rel` VALUES ('136', '1', 'A1600506');
INSERT INTO `ebs_rolemenu_rel` VALUES ('137', '1', 'A1600601');
INSERT INTO `ebs_rolemenu_rel` VALUES ('138', '1', 'A1600602');
INSERT INTO `ebs_rolemenu_rel` VALUES ('139', '1', 'A17001');
INSERT INTO `ebs_rolemenu_rel` VALUES ('140', '1', 'A17002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('141', '1', 'A17003');
INSERT INTO `ebs_rolemenu_rel` VALUES ('142', '1', 'A1700301');
INSERT INTO `ebs_rolemenu_rel` VALUES ('143', '1', 'A17005');
INSERT INTO `ebs_rolemenu_rel` VALUES ('144', '1', 'A17006');
INSERT INTO `ebs_rolemenu_rel` VALUES ('145', '1', 'A17007');
INSERT INTO `ebs_rolemenu_rel` VALUES ('146', '1', 'A17008');
INSERT INTO `ebs_rolemenu_rel` VALUES ('147', '1', 'A17009');
INSERT INTO `ebs_rolemenu_rel` VALUES ('148', '1', 'A19001');
INSERT INTO `ebs_rolemenu_rel` VALUES ('149', '1', 'A19002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('150', '1', 'A19003');
INSERT INTO `ebs_rolemenu_rel` VALUES ('151', '1', 'A19004');
INSERT INTO `ebs_rolemenu_rel` VALUES ('152', '1', 'A19005');
INSERT INTO `ebs_rolemenu_rel` VALUES ('153', '1', 'A1900501');
INSERT INTO `ebs_rolemenu_rel` VALUES ('154', '1', 'A1900502');
INSERT INTO `ebs_rolemenu_rel` VALUES ('155', '1', 'A1900601');
INSERT INTO `ebs_rolemenu_rel` VALUES ('156', '1', 'A190060101');
INSERT INTO `ebs_rolemenu_rel` VALUES ('157', '1', 'A190060105');
INSERT INTO `ebs_rolemenu_rel` VALUES ('158', '1', 'A1900602');
INSERT INTO `ebs_rolemenu_rel` VALUES ('159', '1', 'A1900603');
INSERT INTO `ebs_rolemenu_rel` VALUES ('160', '1', 'A1900604');
INSERT INTO `ebs_rolemenu_rel` VALUES ('161', '1', 'A19007');
INSERT INTO `ebs_rolemenu_rel` VALUES ('162', '1', 'A19008');
INSERT INTO `ebs_rolemenu_rel` VALUES ('163', '1', 'A1900901');
INSERT INTO `ebs_rolemenu_rel` VALUES ('164', '1', 'A1900902');
INSERT INTO `ebs_rolemenu_rel` VALUES ('165', '1', 'A1900903');
INSERT INTO `ebs_rolemenu_rel` VALUES ('166', '1', 'A1900904');
INSERT INTO `ebs_rolemenu_rel` VALUES ('167', '1', 'A1900905');
INSERT INTO `ebs_rolemenu_rel` VALUES ('168', '1', 'A19010');
INSERT INTO `ebs_rolemenu_rel` VALUES ('169', '1', 'A19011');
INSERT INTO `ebs_rolemenu_rel` VALUES ('170', '1', 'A19012');
INSERT INTO `ebs_rolemenu_rel` VALUES ('171', '1', 'A19013');
INSERT INTO `ebs_rolemenu_rel` VALUES ('172', '1', 'A19014');
INSERT INTO `ebs_rolemenu_rel` VALUES ('173', '1', 'A19015');
INSERT INTO `ebs_rolemenu_rel` VALUES ('174', '1', 'A19016');
INSERT INTO `ebs_rolemenu_rel` VALUES ('175', '1', 'A20002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('176', '1', 'A20003');
INSERT INTO `ebs_rolemenu_rel` VALUES ('177', '1', 'A3102');
INSERT INTO `ebs_rolemenu_rel` VALUES ('178', '1', 'A3103');
INSERT INTO `ebs_rolemenu_rel` VALUES ('179', '1', 'A3104');
INSERT INTO `ebs_rolemenu_rel` VALUES ('180', '1', 'A3105');
INSERT INTO `ebs_rolemenu_rel` VALUES ('181', '1', 'A3106');
INSERT INTO `ebs_rolemenu_rel` VALUES ('182', '1', 'A3107');
INSERT INTO `ebs_rolemenu_rel` VALUES ('183', '1', 'A3302');
INSERT INTO `ebs_rolemenu_rel` VALUES ('184', '1', 'A3303');
INSERT INTO `ebs_rolemenu_rel` VALUES ('185', '1', 'A3304');
INSERT INTO `ebs_rolemenu_rel` VALUES ('186', '1', 'A3411');
INSERT INTO `ebs_rolemenu_rel` VALUES ('187', '1', 'A3412');
INSERT INTO `ebs_rolemenu_rel` VALUES ('188', '1', 'A3413');
INSERT INTO `ebs_rolemenu_rel` VALUES ('189', '1', 'A3414');
INSERT INTO `ebs_rolemenu_rel` VALUES ('190', '1', 'A3415');
INSERT INTO `ebs_rolemenu_rel` VALUES ('191', '1', 'A3416');
INSERT INTO `ebs_rolemenu_rel` VALUES ('192', '1', 'A3417');
INSERT INTO `ebs_rolemenu_rel` VALUES ('193', '1', 'A3501');
INSERT INTO `ebs_rolemenu_rel` VALUES ('194', '1', 'A3502');
INSERT INTO `ebs_rolemenu_rel` VALUES ('195', '1', 'A3601');
INSERT INTO `ebs_rolemenu_rel` VALUES ('196', '1', 'A3602');
INSERT INTO `ebs_rolemenu_rel` VALUES ('197', '1', 'A3701');
INSERT INTO `ebs_rolemenu_rel` VALUES ('198', '1', 'A3702');
INSERT INTO `ebs_rolemenu_rel` VALUES ('199', '1', 'A3703');
INSERT INTO `ebs_rolemenu_rel` VALUES ('200', '1', 'A3704');
INSERT INTO `ebs_rolemenu_rel` VALUES ('201', '1', 'A3705');
INSERT INTO `ebs_rolemenu_rel` VALUES ('202', '1', 'A3801');
INSERT INTO `ebs_rolemenu_rel` VALUES ('203', '1', 'A3802');
INSERT INTO `ebs_rolemenu_rel` VALUES ('204', '1', 'A3803');
INSERT INTO `ebs_rolemenu_rel` VALUES ('205', '1', 'A3804');
INSERT INTO `ebs_rolemenu_rel` VALUES ('206', '1', 'A3805');
INSERT INTO `ebs_rolemenu_rel` VALUES ('207', '1', 'A3806');
INSERT INTO `ebs_rolemenu_rel` VALUES ('208', '1', 'A3807');
INSERT INTO `ebs_rolemenu_rel` VALUES ('209', '1', 'A3808');
INSERT INTO `ebs_rolemenu_rel` VALUES ('210', '1', 'A50001');
INSERT INTO `ebs_rolemenu_rel` VALUES ('211', '1', 'A50002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('212', '1', 'A60004');
INSERT INTO `ebs_rolemenu_rel` VALUES ('213', '1', 'A60005');
INSERT INTO `ebs_rolemenu_rel` VALUES ('214', '1', 'C10001');
INSERT INTO `ebs_rolemenu_rel` VALUES ('215', '1', 'C10002');
INSERT INTO `ebs_rolemenu_rel` VALUES ('216', '1', 'C10003');

-- ----------------------------
-- Table structure for jbpm4_deployment
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployment`;
CREATE TABLE `jbpm4_deployment` (
  `DBID_` bigint(20) NOT NULL,
  `NAME_` longtext,
  `TIMESTAMP_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_deployprop
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployprop`;
CREATE TABLE `jbpm4_deployprop` (
  `DBID_` bigint(20) NOT NULL,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `OBJNAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `STRINGVAL_` varchar(255) DEFAULT NULL,
  `LONGVAL_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  KEY `FK_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  CONSTRAINT `FK_DEPLPROP_DEPL` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_deployprop
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_ext_business_entrust
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_ext_business_entrust`;
CREATE TABLE `jbpm4_ext_business_entrust` (
  `ID` bigint(20) NOT NULL,
  `CONSIGNOR_ROLE` varchar(255) NOT NULL,
  `CONSIGNEE_ROLE` varchar(255) NOT NULL,
  `CONSIGNEE_NAME` varchar(255) NOT NULL,
  `CONSIGNEE_TASK_ID` varchar(255) NOT NULL,
  `TASK_NAME` varchar(255) NOT NULL,
  `CREATE_TIME` date NOT NULL,
  `STATUS` int(11) NOT NULL,
  `BEGIN_TIME` date NOT NULL,
  `END_TIME` date NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_ext_business_entrust
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_ext_businessrole_mapping
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_ext_businessrole_mapping`;
CREATE TABLE `jbpm4_ext_businessrole_mapping` (
  `ID` bigint(20) NOT NULL,
  `PROCESS_ROLE_DEF_ID` bigint(20) DEFAULT NULL,
  `BUSINESS_ROLE` varchar(255) DEFAULT NULL,
  `BUSINESS_ROLE_ID` varchar(255) DEFAULT NULL,
  `ISDEFAULT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK1166D7C32CF2A24E` (`PROCESS_ROLE_DEF_ID`),
  CONSTRAINT `FK1166D7C32CF2A24E` FOREIGN KEY (`PROCESS_ROLE_DEF_ID`) REFERENCES `jbpm4_ext_processrole_def` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_ext_businessrole_mapping
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_ext_process_def
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_ext_process_def`;
CREATE TABLE `jbpm4_ext_process_def` (
  `ID` bigint(20) NOT NULL,
  `PROCESS_ZIP` varchar(255) DEFAULT NULL,
  `PROCESS_JPDL` varchar(255) DEFAULT NULL,
  `PROCESS_PNG` varchar(255) DEFAULT NULL,
  `PROCESS_KEY` varchar(255) DEFAULT NULL,
  `PROCESS_NAME` varchar(255) DEFAULT NULL,
  `PROCESS_STATE` int(11) DEFAULT NULL,
  `PROCESS_DEPLOYID` varchar(255) DEFAULT NULL,
  `PROCESS_CREATETIME` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_ext_process_def
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_ext_processrole_def
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_ext_processrole_def`;
CREATE TABLE `jbpm4_ext_processrole_def` (
  `ID` bigint(20) NOT NULL,
  `PROCESS_KEY` varchar(255) DEFAULT NULL,
  `PROCESS_TASK` varchar(255) DEFAULT NULL,
  `REMARK` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_ext_processrole_def
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_actinst
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_actinst`;
CREATE TABLE `jbpm4_hist_actinst` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TRANSITION_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HTI_HTASK` (`HTASK_`),
  KEY `IDX_HACTI_HPROCI` (`HPROCI_`),
  KEY `FK_HACTI_HPROCI` (`HPROCI_`),
  KEY `FK_HTI_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HACTI_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HTI_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_hist_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_detail
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_detail`;
CREATE TABLE `jbpm4_hist_detail` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HPROCIIDX_` int(11) DEFAULT NULL,
  `HACTI_` bigint(20) DEFAULT NULL,
  `HACTIIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  `HTASKIDX_` int(11) DEFAULT NULL,
  `HVAR_` bigint(20) DEFAULT NULL,
  `HVARIDX_` int(11) DEFAULT NULL,
  `MESSAGE_` longtext,
  `OLD_STR_` varchar(255) DEFAULT NULL,
  `NEW_STR_` varchar(255) DEFAULT NULL,
  `OLD_INT_` int(11) DEFAULT NULL,
  `NEW_INT_` int(11) DEFAULT NULL,
  `OLD_TIME_` datetime DEFAULT NULL,
  `NEW_TIME_` datetime DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HDET_HACTI` (`HACTI_`),
  KEY `IDX_HDET_HPROCI` (`HPROCI_`),
  KEY `IDX_HDET_HTASK` (`HTASK_`),
  KEY `IDX_HDET_HVAR` (`HVAR_`),
  KEY `FK_HDETAIL_HVAR` (`HVAR_`),
  KEY `FK_HDETAIL_HPROCI` (`HPROCI_`),
  KEY `FK_HDETAIL_HTASK` (`HTASK_`),
  KEY `FK_HDETAIL_HACTI` (`HACTI_`),
  CONSTRAINT `FK_HDETAIL_HACTI` FOREIGN KEY (`HACTI_`) REFERENCES `jbpm4_hist_actinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HVAR` FOREIGN KEY (`HVAR_`) REFERENCES `jbpm4_hist_var` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_hist_detail
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_procinst
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_procinst`;
CREATE TABLE `jbpm4_hist_procinst` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ENDACTIVITY_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_hist_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_task
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_task`;
CREATE TABLE `jbpm4_hist_task` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `OUTCOME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HSUPERT_SUB` (`SUPERTASK_`),
  KEY `FK_HSUPERT_SUB` (`SUPERTASK_`),
  CONSTRAINT `FK_HSUPERT_SUB` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_hist_task
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_hist_var
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_var`;
CREATE TABLE `jbpm4_hist_var` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `PROCINSTID_` varchar(255) DEFAULT NULL,
  `EXECUTIONID_` varchar(255) DEFAULT NULL,
  `VARNAME_` varchar(255) DEFAULT NULL,
  `VALUE_` varchar(255) DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HVAR_HPROCI` (`HPROCI_`),
  KEY `IDX_HVAR_HTASK` (`HTASK_`),
  KEY `FK_HVAR_HPROCI` (`HPROCI_`),
  KEY `FK_HVAR_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HVAR_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HVAR_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_hist_var
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_id_group
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_group`;
CREATE TABLE `jbpm4_id_group` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_GROUP_PARENT` (`PARENT_`),
  KEY `FK_GROUP_PARENT` (`PARENT_`),
  CONSTRAINT `FK_GROUP_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_id_group` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_id_group
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_membership`;
CREATE TABLE `jbpm4_id_membership` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USER_` bigint(20) DEFAULT NULL,
  `GROUP_` bigint(20) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_MEM_USER` (`USER_`),
  KEY `IDX_MEM_GROUP` (`GROUP_`),
  KEY `FK_MEM_USER` (`USER_`),
  KEY `FK_MEM_GROUP` (`GROUP_`),
  CONSTRAINT `FK_MEM_GROUP` FOREIGN KEY (`GROUP_`) REFERENCES `jbpm4_id_group` (`DBID_`),
  CONSTRAINT `FK_MEM_USER` FOREIGN KEY (`USER_`) REFERENCES `jbpm4_id_user` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_id_user
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_user`;
CREATE TABLE `jbpm4_id_user` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PASSWORD_` varchar(255) DEFAULT NULL,
  `GIVENNAME_` varchar(255) DEFAULT NULL,
  `FAMILYNAME_` varchar(255) DEFAULT NULL,
  `BUSINESSEMAIL_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_id_user
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_job
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_job`;
CREATE TABLE `jbpm4_job` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ISEXCLUSIVE_` bit(1) DEFAULT NULL,
  `LOCKOWNER_` varchar(255) DEFAULT NULL,
  `LOCKEXPTIME_` datetime DEFAULT NULL,
  `EXCEPTION_` longtext,
  `RETRIES_` int(11) DEFAULT NULL,
  `PROCESSINSTANCE_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `CFG_` bigint(20) DEFAULT NULL,
  `SIGNAL_` varchar(255) DEFAULT NULL,
  `EVENT_` varchar(255) DEFAULT NULL,
  `REPEAT_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_JOBDUEDATE` (`DUEDATE_`),
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `IDX_JOB_EXE` (`EXECUTION_`),
  KEY `IDX_JOB_CFG` (`CFG_`),
  KEY `IDX_JOBLOCKEXP` (`LOCKEXPTIME_`),
  KEY `IDX_JOBRETRIES` (`RETRIES_`),
  KEY `FK_JOB_CFG` (`CFG_`),
  CONSTRAINT `FK_JOB_CFG` FOREIGN KEY (`CFG_`) REFERENCES `jbpm4_lob` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_job
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_lob
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_lob`;
CREATE TABLE `jbpm4_lob` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `BLOB_VALUE_` longblob,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `NAME_` longtext,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  KEY `FK_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  CONSTRAINT `FK_LOB_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_lob
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_participation
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_participation`;
CREATE TABLE `jbpm4_participation` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `GROUPID_` varchar(255) DEFAULT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_PART_TASK` (`TASK_`),
  KEY `FK_PART_TASK` (`TASK_`),
  KEY `FK_PART_SWIMLANE` (`SWIMLANE_`),
  CONSTRAINT `FK_PART_SWIMLANE` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`),
  CONSTRAINT `FK_PART_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_participation
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_property
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_property`;
CREATE TABLE `jbpm4_property` (
  `KEY_` varchar(100) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `VALUE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_property
-- ----------------------------
INSERT INTO `jbpm4_property` VALUES ('next.dbid', '0', '1');

-- ----------------------------
-- Table structure for jbpm4_swimlane
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_swimlane`;
CREATE TABLE `jbpm4_swimlane` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_SWIMLANE_EXEC` (`EXECUTION_`),
  KEY `FK_SWIMLANE_EXEC` (`EXECUTION_`),
  CONSTRAINT `FK_SWIMLANE_EXEC` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_swimlane
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_task
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_task`;
CREATE TABLE `jbpm4_task` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` char(1) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DESCR_` longtext,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `FORM_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `PROGRESS_` int(11) DEFAULT NULL,
  `SIGNALLING_` bit(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `HASVARS_` bit(1) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `PROCINST_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  `TASKDEFNAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_TASK_SUPERTASK` (`SUPERTASK_`),
  KEY `FK_TASK_SUPERTASK` (`SUPERTASK_`),
  KEY `FK_TASK_SWIML` (`SWIMLANE_`),
  CONSTRAINT `FK_TASK_SUPERTASK` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_task` (`DBID_`),
  CONSTRAINT `FK_TASK_SWIML` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_task
-- ----------------------------

-- ----------------------------
-- Table structure for jbpm4_variable
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_variable`;
CREATE TABLE `jbpm4_variable` (
  `DBID_` bigint(20) NOT NULL,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CONVERTER_` varchar(255) DEFAULT NULL,
  `HIST_` bit(1) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `LOB_` bigint(20) DEFAULT NULL,
  `DATE_VALUE_` datetime DEFAULT NULL,
  `DOUBLE_VALUE_` double DEFAULT NULL,
  `CLASSNAME_` varchar(255) DEFAULT NULL,
  `LONG_VALUE_` bigint(20) DEFAULT NULL,
  `STRING_VALUE_` varchar(255) DEFAULT NULL,
  `TEXT_VALUE_` longtext,
  `EXESYS_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_VAR_EXECUTION` (`EXECUTION_`),
  KEY `IDX_VAR_LOB` (`LOB_`),
  KEY `IDX_VAR_EXESYS` (`EXESYS_`),
  KEY `IDX_VAR_TASK` (`TASK_`),
  KEY `FK_VAR_LOB` (`LOB_`),
  KEY `FK_VAR_EXESYS` (`EXESYS_`),
  KEY `FK_VAR_TASK` (`TASK_`),
  KEY `FK_VAR_EXECUTION` (`EXECUTION_`),
  CONSTRAINT `FK_VAR_EXECUTION` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_EXESYS` FOREIGN KEY (`EXESYS_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_LOB` FOREIGN KEY (`LOB_`) REFERENCES `jbpm4_lob` (`DBID_`),
  CONSTRAINT `FK_VAR_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of jbpm4_variable
-- ----------------------------

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` bigint(10) NOT NULL COMMENT '学生ID',
  `branch_id` bigint(10) DEFAULT NULL COMMENT '校区ID',
  `fee_amount` decimal(14,2) DEFAULT NULL COMMENT '账户余额',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_account
-- ----------------------------

-- ----------------------------
-- Table structure for t_account_change
-- ----------------------------
DROP TABLE IF EXISTS `t_account_change`;
CREATE TABLE `t_account_change` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `account_id` bigint(10) NOT NULL COMMENT '账户ID',
  `order_id` bigint(10) DEFAULT NULL COMMENT '订单ID',
  `encoder_id` bigint(10) DEFAULT NULL COMMENT '单据ID',
  `change_flag` int(1) DEFAULT NULL COMMENT '变更标识:0存入,1取出',
  `change_type` int(1) DEFAULT NULL COMMENT '变更类型:-2v3账户迁移到v5新增记录,0客户充值,1订单收费取出,2订单退费存入,3客户取出,4一元转校,5:转账6:理赔7:转班转入8:充值作废 9：理赔作废 10：取款作废 11:报班作废 12:退费作废 13:冻结',
  `change_amount` decimal(14,2) DEFAULT NULL COMMENT '变更金额',
  `pre_amount` decimal(14,2) DEFAULT NULL COMMENT '变更前金额',
  `next_amount` decimal(14,2) DEFAULT NULL COMMENT '变更后金额',
  `pay_mode` int(1) DEFAULT NULL COMMENT '收付费类型:0现金,1内部转账,2银行转账',
  `dynamic_id` bigint(10) DEFAULT NULL COMMENT '动户记录ID',
  `change_time` datetime DEFAULT NULL COMMENT '交易时间',
  `account_type` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_account_change
-- ----------------------------

-- ----------------------------
-- Table structure for t_account_dymanic
-- ----------------------------
DROP TABLE IF EXISTS `t_account_dymanic`;
CREATE TABLE `t_account_dymanic` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dynamic_type` int(1) DEFAULT NULL COMMENT '类型 :-1:初始数据 1：充值 2：转账  3：理赔 4：取款  5:充值作废 6：理赔作废 7：取款作废 8:一元转校',
  `branch_id` bigint(10) DEFAULT NULL COMMENT '校区',
  `student_id` bigint(10) NOT NULL COMMENT '学生',
  `account_id` bigint(10) NOT NULL COMMENT '账户',
  `money` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `pay_flag` int(1) DEFAULT NULL COMMENT '收付费类型 tp_fee_flag',
  `pay_mode` int(1) DEFAULT NULL COMMENT '收付费方式 tp_pay_mode',
  `input_user` bigint(10) DEFAULT NULL COMMENT '操作人',
  `input_time` datetime DEFAULT NULL COMMENT '操作时间',
  `approve_user` bigint(10) DEFAULT NULL COMMENT '审批人',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `confirm_user` bigint(10) DEFAULT NULL COMMENT '确认人',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `status` int(1) DEFAULT NULL COMMENT '状态 1:待审批 2：未通过 3：生效',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `encoding` varchar(200) DEFAULT NULL COMMENT '编号',
  `dynamic_id` bigint(10) DEFAULT NULL COMMENT '作废记录',
  `money_fee` decimal(10,0) DEFAULT NULL,
  `account_type` int(1) DEFAULT NULL,
  `refund_change_no` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_account_dymanic
-- ----------------------------

-- ----------------------------
-- Table structure for t_bas_invoice_company
-- ----------------------------
DROP TABLE IF EXISTS `t_bas_invoice_company`;
CREATE TABLE `t_bas_invoice_company` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `company_name` varchar(50) NOT NULL COMMENT '公司名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_bas_invoice_company
-- ----------------------------

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `course_no` varchar(50) DEFAULT NULL COMMENT '课程编号',
  `course_name` varchar(200) DEFAULT NULL COMMENT '课程名称',
  `business_type` int(1) DEFAULT NULL COMMENT '业务模型  1：班级课  2：一对一 3：晚辅导',
  `target` int(5) DEFAULT NULL COMMENT '课程目标',
  `branch_id` bigint(10) DEFAULT NULL COMMENT '所属校区',
  `season_id` bigint(10) DEFAULT NULL COMMENT '课程季',
  `grade_id` bigint(10) DEFAULT NULL COMMENT '年级Id',
  `subject_id` bigint(10) DEFAULT NULL COMMENT '科目',
  `teacher_id` bigint(10) DEFAULT NULL COMMENT '教师ID',
  `unit_price` decimal(14,2) DEFAULT NULL COMMENT '课时单价',
  `sum_price` decimal(14,2) DEFAULT NULL COMMENT '课程总价',
  `course_count` int(5) DEFAULT NULL COMMENT '课时数量',
  `status` int(1) DEFAULT NULL COMMENT '课程状态  0：删除   1：上架    2：下架',
  `product_type` int(1) DEFAULT NULL COMMENT '销售类型 1：正价 2：促销 3：赠送',
  `validaty_date` varchar(30) DEFAULT NULL COMMENT '上架时间:同审批通过时间',
  `invalidity_date` varchar(30) DEFAULT NULL COMMENT '下架日期',
  `start_date` varchar(30) DEFAULT NULL COMMENT '开课日期',
  `end_date` varchar(30) DEFAULT NULL COMMENT '结课日期',
  `start_time` varchar(30) DEFAULT NULL COMMENT '上课时间',
  `end_time` varchar(30) DEFAULT NULL COMMENT '下课时间',
  `hour_len` decimal(14,2) DEFAULT NULL COMMENT '课时长度(分钟)',
  `course_surplus` int(5) DEFAULT NULL COMMENT '剩余课时',
  `people_count` int(5) DEFAULT NULL COMMENT '计划上课人数',
  `actual_people_count` int(5) DEFAULT NULL COMMENT '实际上课人数',
  `attend_class_period` varchar(100) DEFAULT NULL COMMENT '上课周期(1,3,5)',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `proceed_status` int(1) DEFAULT NULL COMMENT '进行状态 1：未开始  2：进行中  3：已结束',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_textbooks` int(1) DEFAULT NULL,
  `num_textbooks` int(5) DEFAULT NULL,
  `short_or_long` int(1) DEFAULT NULL COMMENT '1:长期班 2：短期班',
  `assteacher_id` bigint(10) DEFAULT NULL,
  `performance_belong_type` int(1) DEFAULT NULL COMMENT '业绩归属类型     1:代办校区；2：课程校区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_course
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `str_key` varchar(128) NOT NULL COMMENT 'KEY',
  `str_value` text NOT NULL COMMENT '值',
  `memo` varchar(1000) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `operate` varchar(255) NOT NULL COMMENT '操作名称',
  `content` text COMMENT '内容(执行的SQL语句等)',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_module
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_module`;
CREATE TABLE `t_sys_module` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `encoding` varchar(50) NOT NULL COMMENT '菜单code',
  `module_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `module_url` varchar(100) NOT NULL COMMENT 'URL',
  `sort_num` int(5) NOT NULL COMMENT '排序',
  `parent_id` bigint(10) DEFAULT NULL COMMENT '父模块ID',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `is_menu` int(1) NOT NULL COMMENT '是否是菜单',
  `menu_img` varchar(100) NOT NULL COMMENT '菜单图片路径',
  `style_class` varchar(100) DEFAULT NULL COMMENT '菜单class',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sys_module
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_role_menu_ref
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu_ref`;
CREATE TABLE `t_sys_role_menu_ref` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(10) NOT NULL COMMENT '角色id',
  `menu_id` varchar(15) NOT NULL COMMENT '模板id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sys_role_menu_ref
-- ----------------------------
INSERT INTO `t_sys_role_menu_ref` VALUES ('1', '1', 'A17004');
INSERT INTO `t_sys_role_menu_ref` VALUES ('2', '1', 'A1100101');
INSERT INTO `t_sys_role_menu_ref` VALUES ('3', '1', 'A1100102');
INSERT INTO `t_sys_role_menu_ref` VALUES ('4', '1', 'A1100103');
INSERT INTO `t_sys_role_menu_ref` VALUES ('5', '1', 'A1100104');
INSERT INTO `t_sys_role_menu_ref` VALUES ('6', '1', 'A12002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('7', '1', 'A1200301');
INSERT INTO `t_sys_role_menu_ref` VALUES ('8', '1', 'A1200302');
INSERT INTO `t_sys_role_menu_ref` VALUES ('9', '1', 'A1200303');
INSERT INTO `t_sys_role_menu_ref` VALUES ('10', '1', 'A1200305');
INSERT INTO `t_sys_role_menu_ref` VALUES ('11', '1', 'A1200306');
INSERT INTO `t_sys_role_menu_ref` VALUES ('12', '1', 'A1200307');
INSERT INTO `t_sys_role_menu_ref` VALUES ('13', '1', 'A1200701');
INSERT INTO `t_sys_role_menu_ref` VALUES ('14', '1', 'A1200702');
INSERT INTO `t_sys_role_menu_ref` VALUES ('15', '1', 'A1200703');
INSERT INTO `t_sys_role_menu_ref` VALUES ('16', '1', 'A1200704');
INSERT INTO `t_sys_role_menu_ref` VALUES ('17', '1', 'A1200705');
INSERT INTO `t_sys_role_menu_ref` VALUES ('18', '1', 'A1200706');
INSERT INTO `t_sys_role_menu_ref` VALUES ('19', '1', 'A1200707');
INSERT INTO `t_sys_role_menu_ref` VALUES ('20', '1', 'A1200708');
INSERT INTO `t_sys_role_menu_ref` VALUES ('21', '1', 'A1200709');
INSERT INTO `t_sys_role_menu_ref` VALUES ('22', '1', 'A1200710');
INSERT INTO `t_sys_role_menu_ref` VALUES ('23', '1', 'A1200711');
INSERT INTO `t_sys_role_menu_ref` VALUES ('24', '1', 'A1200712');
INSERT INTO `t_sys_role_menu_ref` VALUES ('25', '1', 'A1300201');
INSERT INTO `t_sys_role_menu_ref` VALUES ('26', '1', 'A13003');
INSERT INTO `t_sys_role_menu_ref` VALUES ('27', '1', 'A13004');
INSERT INTO `t_sys_role_menu_ref` VALUES ('28', '1', 'A1300501');
INSERT INTO `t_sys_role_menu_ref` VALUES ('29', '1', 'A1300502');
INSERT INTO `t_sys_role_menu_ref` VALUES ('30', '1', 'A1300503');
INSERT INTO `t_sys_role_menu_ref` VALUES ('31', '1', 'A1300504');
INSERT INTO `t_sys_role_menu_ref` VALUES ('32', '1', 'A1300505');
INSERT INTO `t_sys_role_menu_ref` VALUES ('33', '1', 'A1300506');
INSERT INTO `t_sys_role_menu_ref` VALUES ('34', '1', 'A1300507');
INSERT INTO `t_sys_role_menu_ref` VALUES ('35', '1', 'A1300508');
INSERT INTO `t_sys_role_menu_ref` VALUES ('36', '1', 'A14001');
INSERT INTO `t_sys_role_menu_ref` VALUES ('37', '1', 'A1400206');
INSERT INTO `t_sys_role_menu_ref` VALUES ('38', '1', 'A1400207');
INSERT INTO `t_sys_role_menu_ref` VALUES ('39', '1', 'A1400208');
INSERT INTO `t_sys_role_menu_ref` VALUES ('40', '1', 'A1400209');
INSERT INTO `t_sys_role_menu_ref` VALUES ('41', '1', 'A1400210');
INSERT INTO `t_sys_role_menu_ref` VALUES ('42', '1', 'A1400211');
INSERT INTO `t_sys_role_menu_ref` VALUES ('43', '1', 'A1400212');
INSERT INTO `t_sys_role_menu_ref` VALUES ('44', '1', 'A14003');
INSERT INTO `t_sys_role_menu_ref` VALUES ('45', '1', 'A1400401');
INSERT INTO `t_sys_role_menu_ref` VALUES ('46', '1', 'A1400402');
INSERT INTO `t_sys_role_menu_ref` VALUES ('47', '1', 'A1400403');
INSERT INTO `t_sys_role_menu_ref` VALUES ('48', '1', 'A1400404');
INSERT INTO `t_sys_role_menu_ref` VALUES ('49', '1', 'A1400405');
INSERT INTO `t_sys_role_menu_ref` VALUES ('50', '1', 'A1400406');
INSERT INTO `t_sys_role_menu_ref` VALUES ('51', '1', 'A1400407');
INSERT INTO `t_sys_role_menu_ref` VALUES ('52', '1', 'A1400411');
INSERT INTO `t_sys_role_menu_ref` VALUES ('53', '1', 'A1400412');
INSERT INTO `t_sys_role_menu_ref` VALUES ('54', '1', 'A1400413');
INSERT INTO `t_sys_role_menu_ref` VALUES ('55', '1', 'A1400414');
INSERT INTO `t_sys_role_menu_ref` VALUES ('56', '1', 'A1400415');
INSERT INTO `t_sys_role_menu_ref` VALUES ('57', '1', 'A140050101');
INSERT INTO `t_sys_role_menu_ref` VALUES ('58', '1', 'A140050102');
INSERT INTO `t_sys_role_menu_ref` VALUES ('59', '1', 'A140050106');
INSERT INTO `t_sys_role_menu_ref` VALUES ('60', '1', 'A1400502');
INSERT INTO `t_sys_role_menu_ref` VALUES ('61', '1', 'A140050201');
INSERT INTO `t_sys_role_menu_ref` VALUES ('62', '1', 'A1400503');
INSERT INTO `t_sys_role_menu_ref` VALUES ('63', '1', 'A1400601');
INSERT INTO `t_sys_role_menu_ref` VALUES ('64', '1', 'A1400602');
INSERT INTO `t_sys_role_menu_ref` VALUES ('65', '1', 'A140060201');
INSERT INTO `t_sys_role_menu_ref` VALUES ('66', '1', 'A1400603');
INSERT INTO `t_sys_role_menu_ref` VALUES ('67', '1', 'A140060301');
INSERT INTO `t_sys_role_menu_ref` VALUES ('68', '1', 'A1400604');
INSERT INTO `t_sys_role_menu_ref` VALUES ('69', '1', 'A1400605');
INSERT INTO `t_sys_role_menu_ref` VALUES ('70', '1', 'A140060501');
INSERT INTO `t_sys_role_menu_ref` VALUES ('71', '1', 'A1400606');
INSERT INTO `t_sys_role_menu_ref` VALUES ('72', '1', 'A140060601');
INSERT INTO `t_sys_role_menu_ref` VALUES ('73', '1', 'A140060701');
INSERT INTO `t_sys_role_menu_ref` VALUES ('74', '1', 'A1400611');
INSERT INTO `t_sys_role_menu_ref` VALUES ('75', '1', 'A1400612');
INSERT INTO `t_sys_role_menu_ref` VALUES ('76', '1', 'A14007');
INSERT INTO `t_sys_role_menu_ref` VALUES ('77', '1', 'A1400801');
INSERT INTO `t_sys_role_menu_ref` VALUES ('78', '1', 'A1400802');
INSERT INTO `t_sys_role_menu_ref` VALUES ('79', '1', 'A140080301');
INSERT INTO `t_sys_role_menu_ref` VALUES ('80', '1', 'A1400804');
INSERT INTO `t_sys_role_menu_ref` VALUES ('81', '1', 'A1400805');
INSERT INTO `t_sys_role_menu_ref` VALUES ('82', '1', 'A14009');
INSERT INTO `t_sys_role_menu_ref` VALUES ('83', '1', 'A140100101');
INSERT INTO `t_sys_role_menu_ref` VALUES ('84', '1', 'A140100102');
INSERT INTO `t_sys_role_menu_ref` VALUES ('85', '1', 'A1401002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('86', '1', 'A140100202');
INSERT INTO `t_sys_role_menu_ref` VALUES ('87', '1', 'A140100203');
INSERT INTO `t_sys_role_menu_ref` VALUES ('88', '1', 'A140100204');
INSERT INTO `t_sys_role_menu_ref` VALUES ('89', '1', 'A140100205');
INSERT INTO `t_sys_role_menu_ref` VALUES ('90', '1', 'A140100206');
INSERT INTO `t_sys_role_menu_ref` VALUES ('91', '1', 'A140100207');
INSERT INTO `t_sys_role_menu_ref` VALUES ('92', '1', 'A140100208');
INSERT INTO `t_sys_role_menu_ref` VALUES ('93', '1', 'A140100209');
INSERT INTO `t_sys_role_menu_ref` VALUES ('94', '1', 'A140100210');
INSERT INTO `t_sys_role_menu_ref` VALUES ('95', '1', 'A140100211');
INSERT INTO `t_sys_role_menu_ref` VALUES ('96', '1', 'A140100212');
INSERT INTO `t_sys_role_menu_ref` VALUES ('97', '1', 'A140100213');
INSERT INTO `t_sys_role_menu_ref` VALUES ('98', '1', 'A140100214');
INSERT INTO `t_sys_role_menu_ref` VALUES ('99', '1', 'A140100215');
INSERT INTO `t_sys_role_menu_ref` VALUES ('100', '1', 'A140100216');
INSERT INTO `t_sys_role_menu_ref` VALUES ('101', '1', 'A140100217');
INSERT INTO `t_sys_role_menu_ref` VALUES ('102', '1', 'A140100218');
INSERT INTO `t_sys_role_menu_ref` VALUES ('103', '1', 'A140100219');
INSERT INTO `t_sys_role_menu_ref` VALUES ('104', '1', 'A140100220');
INSERT INTO `t_sys_role_menu_ref` VALUES ('105', '1', 'A140100221');
INSERT INTO `t_sys_role_menu_ref` VALUES ('106', '1', 'A140100222');
INSERT INTO `t_sys_role_menu_ref` VALUES ('107', '1', 'A140100223');
INSERT INTO `t_sys_role_menu_ref` VALUES ('108', '1', 'A140100224');
INSERT INTO `t_sys_role_menu_ref` VALUES ('109', '1', 'A140100225');
INSERT INTO `t_sys_role_menu_ref` VALUES ('110', '1', 'A140100301');
INSERT INTO `t_sys_role_menu_ref` VALUES ('111', '1', 'A140100401');
INSERT INTO `t_sys_role_menu_ref` VALUES ('112', '1', 'A1401101');
INSERT INTO `t_sys_role_menu_ref` VALUES ('113', '1', 'A1401102');
INSERT INTO `t_sys_role_menu_ref` VALUES ('114', '1', 'A1401201');
INSERT INTO `t_sys_role_menu_ref` VALUES ('115', '1', 'A1401202');
INSERT INTO `t_sys_role_menu_ref` VALUES ('116', '1', 'A140120301');
INSERT INTO `t_sys_role_menu_ref` VALUES ('117', '1', 'A15002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('118', '1', 'A15003');
INSERT INTO `t_sys_role_menu_ref` VALUES ('119', '1', 'A15004');
INSERT INTO `t_sys_role_menu_ref` VALUES ('120', '1', 'A15005');
INSERT INTO `t_sys_role_menu_ref` VALUES ('121', '1', 'A15006');
INSERT INTO `t_sys_role_menu_ref` VALUES ('122', '1', 'A16001');
INSERT INTO `t_sys_role_menu_ref` VALUES ('123', '1', 'A1600201');
INSERT INTO `t_sys_role_menu_ref` VALUES ('124', '1', 'A1600202');
INSERT INTO `t_sys_role_menu_ref` VALUES ('125', '1', 'A1600203');
INSERT INTO `t_sys_role_menu_ref` VALUES ('126', '1', 'A1600204');
INSERT INTO `t_sys_role_menu_ref` VALUES ('127', '1', 'A1600205');
INSERT INTO `t_sys_role_menu_ref` VALUES ('128', '1', 'A1600206');
INSERT INTO `t_sys_role_menu_ref` VALUES ('129', '1', 'A1600208');
INSERT INTO `t_sys_role_menu_ref` VALUES ('130', '1', 'A16004');
INSERT INTO `t_sys_role_menu_ref` VALUES ('131', '1', 'A1600501');
INSERT INTO `t_sys_role_menu_ref` VALUES ('132', '1', 'A1600502');
INSERT INTO `t_sys_role_menu_ref` VALUES ('133', '1', 'A1600503');
INSERT INTO `t_sys_role_menu_ref` VALUES ('134', '1', 'A1600504');
INSERT INTO `t_sys_role_menu_ref` VALUES ('135', '1', 'A1600505');
INSERT INTO `t_sys_role_menu_ref` VALUES ('136', '1', 'A1600506');
INSERT INTO `t_sys_role_menu_ref` VALUES ('137', '1', 'A1600601');
INSERT INTO `t_sys_role_menu_ref` VALUES ('138', '1', 'A1600602');
INSERT INTO `t_sys_role_menu_ref` VALUES ('139', '1', 'A17001');
INSERT INTO `t_sys_role_menu_ref` VALUES ('140', '1', 'A17002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('141', '1', 'A17003');
INSERT INTO `t_sys_role_menu_ref` VALUES ('142', '1', 'A1700301');
INSERT INTO `t_sys_role_menu_ref` VALUES ('143', '1', 'A17005');
INSERT INTO `t_sys_role_menu_ref` VALUES ('144', '1', 'A17006');
INSERT INTO `t_sys_role_menu_ref` VALUES ('145', '1', 'A17007');
INSERT INTO `t_sys_role_menu_ref` VALUES ('146', '1', 'A17008');
INSERT INTO `t_sys_role_menu_ref` VALUES ('147', '1', 'A17009');
INSERT INTO `t_sys_role_menu_ref` VALUES ('148', '1', 'A19001');
INSERT INTO `t_sys_role_menu_ref` VALUES ('149', '1', 'A19002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('150', '1', 'A19003');
INSERT INTO `t_sys_role_menu_ref` VALUES ('151', '1', 'A19004');
INSERT INTO `t_sys_role_menu_ref` VALUES ('152', '1', 'A19005');
INSERT INTO `t_sys_role_menu_ref` VALUES ('153', '1', 'A1900501');
INSERT INTO `t_sys_role_menu_ref` VALUES ('154', '1', 'A1900502');
INSERT INTO `t_sys_role_menu_ref` VALUES ('155', '1', 'A1900601');
INSERT INTO `t_sys_role_menu_ref` VALUES ('156', '1', 'A190060101');
INSERT INTO `t_sys_role_menu_ref` VALUES ('157', '1', 'A190060105');
INSERT INTO `t_sys_role_menu_ref` VALUES ('158', '1', 'A1900602');
INSERT INTO `t_sys_role_menu_ref` VALUES ('159', '1', 'A1900603');
INSERT INTO `t_sys_role_menu_ref` VALUES ('160', '1', 'A1900604');
INSERT INTO `t_sys_role_menu_ref` VALUES ('161', '1', 'A19007');
INSERT INTO `t_sys_role_menu_ref` VALUES ('162', '1', 'A19008');
INSERT INTO `t_sys_role_menu_ref` VALUES ('163', '1', 'A1900901');
INSERT INTO `t_sys_role_menu_ref` VALUES ('164', '1', 'A1900902');
INSERT INTO `t_sys_role_menu_ref` VALUES ('165', '1', 'A1900903');
INSERT INTO `t_sys_role_menu_ref` VALUES ('166', '1', 'A1900904');
INSERT INTO `t_sys_role_menu_ref` VALUES ('167', '1', 'A1900905');
INSERT INTO `t_sys_role_menu_ref` VALUES ('168', '1', 'A19010');
INSERT INTO `t_sys_role_menu_ref` VALUES ('169', '1', 'A19011');
INSERT INTO `t_sys_role_menu_ref` VALUES ('170', '1', 'A19012');
INSERT INTO `t_sys_role_menu_ref` VALUES ('171', '1', 'A19013');
INSERT INTO `t_sys_role_menu_ref` VALUES ('172', '1', 'A19014');
INSERT INTO `t_sys_role_menu_ref` VALUES ('173', '1', 'A19015');
INSERT INTO `t_sys_role_menu_ref` VALUES ('174', '1', 'A19016');
INSERT INTO `t_sys_role_menu_ref` VALUES ('175', '1', 'A20002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('176', '1', 'A20003');
INSERT INTO `t_sys_role_menu_ref` VALUES ('177', '1', 'A3102');
INSERT INTO `t_sys_role_menu_ref` VALUES ('178', '1', 'A3103');
INSERT INTO `t_sys_role_menu_ref` VALUES ('179', '1', 'A3104');
INSERT INTO `t_sys_role_menu_ref` VALUES ('180', '1', 'A3105');
INSERT INTO `t_sys_role_menu_ref` VALUES ('181', '1', 'A3106');
INSERT INTO `t_sys_role_menu_ref` VALUES ('182', '1', 'A3107');
INSERT INTO `t_sys_role_menu_ref` VALUES ('183', '1', 'A3302');
INSERT INTO `t_sys_role_menu_ref` VALUES ('184', '1', 'A3303');
INSERT INTO `t_sys_role_menu_ref` VALUES ('185', '1', 'A3304');
INSERT INTO `t_sys_role_menu_ref` VALUES ('186', '1', 'A3411');
INSERT INTO `t_sys_role_menu_ref` VALUES ('187', '1', 'A3412');
INSERT INTO `t_sys_role_menu_ref` VALUES ('188', '1', 'A3413');
INSERT INTO `t_sys_role_menu_ref` VALUES ('189', '1', 'A3414');
INSERT INTO `t_sys_role_menu_ref` VALUES ('190', '1', 'A3415');
INSERT INTO `t_sys_role_menu_ref` VALUES ('191', '1', 'A3416');
INSERT INTO `t_sys_role_menu_ref` VALUES ('192', '1', 'A3417');
INSERT INTO `t_sys_role_menu_ref` VALUES ('193', '1', 'A3501');
INSERT INTO `t_sys_role_menu_ref` VALUES ('194', '1', 'A3502');
INSERT INTO `t_sys_role_menu_ref` VALUES ('195', '1', 'A3601');
INSERT INTO `t_sys_role_menu_ref` VALUES ('196', '1', 'A3602');
INSERT INTO `t_sys_role_menu_ref` VALUES ('197', '1', 'A3701');
INSERT INTO `t_sys_role_menu_ref` VALUES ('198', '1', 'A3702');
INSERT INTO `t_sys_role_menu_ref` VALUES ('199', '1', 'A3703');
INSERT INTO `t_sys_role_menu_ref` VALUES ('200', '1', 'A3704');
INSERT INTO `t_sys_role_menu_ref` VALUES ('201', '1', 'A3705');
INSERT INTO `t_sys_role_menu_ref` VALUES ('202', '1', 'A3801');
INSERT INTO `t_sys_role_menu_ref` VALUES ('203', '1', 'A3802');
INSERT INTO `t_sys_role_menu_ref` VALUES ('204', '1', 'A3803');
INSERT INTO `t_sys_role_menu_ref` VALUES ('205', '1', 'A3804');
INSERT INTO `t_sys_role_menu_ref` VALUES ('206', '1', 'A3805');
INSERT INTO `t_sys_role_menu_ref` VALUES ('207', '1', 'A3806');
INSERT INTO `t_sys_role_menu_ref` VALUES ('208', '1', 'A3807');
INSERT INTO `t_sys_role_menu_ref` VALUES ('209', '1', 'A3808');
INSERT INTO `t_sys_role_menu_ref` VALUES ('210', '1', 'A50001');
INSERT INTO `t_sys_role_menu_ref` VALUES ('211', '1', 'A50002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('212', '1', 'A60004');
INSERT INTO `t_sys_role_menu_ref` VALUES ('213', '1', 'A60005');
INSERT INTO `t_sys_role_menu_ref` VALUES ('214', '1', 'C10001');
INSERT INTO `t_sys_role_menu_ref` VALUES ('215', '1', 'C10002');
INSERT INTO `t_sys_role_menu_ref` VALUES ('216', '1', 'C10003');

-- ----------------------------
-- Table structure for t_sys_role_module_ref
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_module_ref`;
CREATE TABLE `t_sys_role_module_ref` (
  `id` bigint(10) NOT NULL COMMENT '主键id',
  `role_id` bigint(10) NOT NULL COMMENT '角色id',
  `module_id` bigint(10) NOT NULL COMMENT '模板id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_sys_role_module_ref
-- ----------------------------

-- ----------------------------
-- Table structure for tab_user_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_role_ref`;
CREATE TABLE `tab_user_role_ref` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(10) NOT NULL COMMENT '用户id',
  `role_id` bigint(10) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_user_role_ref
-- ----------------------------
INSERT INTO `tab_user_role_ref` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for tab_data_company_account
-- ----------------------------
DROP TABLE IF EXISTS `tab_data_company_account`;
CREATE TABLE `tab_data_company_account` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `encoding` varchar(50) NOT NULL COMMENT '公司账号',
  `account_name` varchar(50) NOT NULL COMMENT '公司名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_data_company_account
-- ----------------------------

-- ----------------------------
-- Table structure for tab_data_device
-- ----------------------------
DROP TABLE IF EXISTS `tab_data_device`;
CREATE TABLE `tab_data_device` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `encoding` varchar(50) NOT NULL COMMENT 'POS编码',
  `pos_name` varchar(50) NOT NULL COMMENT 'POS名称',
  `short_pos_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `account_id` bigint(10) NOT NULL COMMENT '公司账号',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_data_device
-- ----------------------------

-- ----------------------------
-- Table structure for tab_data_grade
-- ----------------------------
DROP TABLE IF EXISTS `t_bas_grade`;
CREATE TABLE `t_bas_grade` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `encoding` varchar(50) NOT NULL COMMENT '年级编码',
  `grade_name` varchar(50) NOT NULL COMMENT '年级名称',
  `last_id` bigint(10) DEFAULT NULL COMMENT '上一级年级编码',
  `last_encoding` varchar(50) DEFAULT NULL COMMENT '上一级年级编码',
  `last_grade_name` varchar(50) DEFAULT NULL COMMENT '上一级年级名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `sort_num` int(5) DEFAULT NULL COMMENT '排序',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_data_grade
-- ----------------------------

-- ----------------------------
-- Table structure for tab_data_invoice_company
-- ----------------------------
DROP TABLE IF EXISTS `tab_data_invoice_company`;
CREATE TABLE `tab_data_invoice_company` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `company_name` varchar(50) NOT NULL COMMENT '公司名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_data_invoice_company
-- ----------------------------

-- ----------------------------
-- Table structure for tab_data_school
-- ----------------------------
DROP TABLE IF EXISTS `tab_data_school`;
CREATE TABLE `tab_data_school` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `school_name` varchar(50) NOT NULL COMMENT '学校名称',
  `short_school_name` varchar(50) DEFAULT NULL COMMENT '简称',
  `school_type` int(1) DEFAULT NULL COMMENT '学校类型(1=幼儿园 2=小学 3=初中 4=高中 5=初高中-完全中学 6=九年一贯制 7=十二年一贯制 8=职业教育 9=特殊教育 10=成人教育 11=其它)',
  `org_id` bigint(10) DEFAULT NULL COMMENT '组织ID',
  `link_man` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `province_id` bigint(10) DEFAULT NULL COMMENT '省份',
  `city_id` bigint(10) DEFAULT NULL COMMENT '城市',
  `area_id` bigint(10) DEFAULT NULL COMMENT '县/区',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_data_school
-- ----------------------------

-- ----------------------------
-- Table structure for tab_employee_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_employee_info`;
CREATE TABLE `tab_employee_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `encoding` varchar(50) NOT NULL COMMENT '员工编码',
  `employee_name` varchar(50) NOT NULL COMMENT '员工姓名',
  `Sex` int(1) NOT NULL COMMENT '性别(1=男 2=女)',
  `id_card` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '电话',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `user_type` int(1) NOT NULL COMMENT '员工类型(1=正式员工 2=试用员工 3=实习员工)',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信',
  `dept` varchar(50) DEFAULT NULL COMMENT '部门',
  `org_id` bigint(10) DEFAULT NULL COMMENT '组织id',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `entry_date` datetime DEFAULT NULL COMMENT '入职日期',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_employee_info
-- ----------------------------

-- ----------------------------
-- Table structure for tab_organization_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_organization_info`;
CREATE TABLE `tab_organization_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `org_name` varchar(50) NOT NULL COMMENT '组织名称',
  `short_org_name` varchar(50) DEFAULT NULL COMMENT '组织机构简称',
  `parent_id` bigint(10) DEFAULT NULL COMMENT '上级组织',
  `org_type` int(1) NOT NULL COMMENT '组织级别 1=地区级别 2=部门级别 3=团队级别 4=校区级别 ',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `sort_num` int(5) DEFAULT NULL COMMENT '排序',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `Logo` varchar(100) DEFAULT NULL COMMENT 'logo',
  `phone` varchar(15) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  `product_line` int(1) DEFAULT NULL,
  `org_kind` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_organization_info
-- ----------------------------
INSERT INTO `tab_organization_info` VALUES (1, '厝边素高事业部', '厝边素高', 0, NULL, 1, 10000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tab_organization_info` VALUES (2, '厦门地区', '厦门地区', 1, 2, 1, 11000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tab_organization_info` VALUES (3, '龙岩地区', '龙岩地区', 1, 2, 1, 12000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tab_organization_info` VALUES (4, '厦门运营团队', '厦门运营团队', 2, 3, 1, 11100, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tab_organization_info` VALUES (5, '水晶湖郡校区', '水晶湖郡校区', 4, 4, 1, 11101, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tab_privilege_rule
-- ----------------------------
DROP TABLE IF EXISTS `tab_privilege_rule`;
CREATE TABLE `tab_privilege_rule` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rule_name` varchar(200) NOT NULL COMMENT '优惠规则名称',
  `coupon_type` int(1) NOT NULL COMMENT '优惠规则 1折扣优惠   2 优惠金额  3每课时优惠',
  `coupon_content` decimal(10,2) NOT NULL COMMENT '优惠',
  `start_date` varchar(30) DEFAULT NULL COMMENT '开始日期',
  `end_date` varchar(30) DEFAULT NULL COMMENT '结束日期',
  `use_scope` int(1) DEFAULT NULL COMMENT '适用范围1通用 2老学员3新学员',
  `product_line` int(1) DEFAULT NULL COMMENT '产品线',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_privilege_rule
-- ----------------------------

-- ----------------------------
-- Table structure for tab_role_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_role_info`;
CREATE TABLE `tab_role_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_code` varchar(30) NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_role_info
-- ----------------------------
INSERT INTO `tab_role_info` VALUES ('1', 'admin', '超级管理员', '1', null, null, null, null);
INSERT INTO `tab_role_info` VALUES ('2', 'student_manager', '学管师', '1', null, null, null, null);
INSERT INTO `tab_role_info` VALUES ('3', 'school_master', '校长', '1', null, null, null, null);
INSERT INTO `tab_role_info` VALUES ('4', 'course_adviser', '课程顾问', '1', null, null, null, null);

-- ----------------------------
-- Table structure for tab_student_contact
-- ----------------------------
DROP TABLE IF EXISTS `tab_student_contact`;
CREATE TABLE `tab_student_contact` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `student_id` bigint(10) NOT NULL COMMENT '学生ID',
  `link_name` varchar(100) DEFAULT NULL COMMENT '联系人姓名',
  `link_phone` varchar(30) DEFAULT NULL COMMENT '联系人电话',
  `relation` int(1) DEFAULT NULL COMMENT '1父亲  2母亲  3亲戚 4自己 5其他',
  `is_valid` int(1) DEFAULT NULL COMMENT '是否有效',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_student_contact
-- ----------------------------

-- ----------------------------
-- Table structure for tab_student_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_student_info`;
CREATE TABLE `tab_student_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID ',
  `encoding` varchar(50) NOT NULL COMMENT '学生code',
  `student_name` varchar(50) NOT NULL COMMENT '姓名',
  `sex` int(1) DEFAULT NULL COMMENT '性别(1=男 2=女)',
  `birthday` varchar(50) DEFAULT NULL COMMENT '出生日期',
  `student_status` int(1) NOT NULL COMMENT '学生状态1:正常 2:重复 3:在读 4:沉睡  5:停课   6:结课',
  `address` varchar(300) DEFAULT NULL COMMENT '家庭地址',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ号码',
  `phone` varchar(30) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `grade_id` bigint(10) DEFAULT NULL COMMENT '年级',
  `head_pic` varchar(300) DEFAULT NULL COMMENT '头像',
  `attend_school_id` bigint(10) DEFAULT NULL COMMENT '就读学校',
  `branch_id` bigint(10) DEFAULT NULL COMMENT '开户校区',
  `is_old_student` int(1) DEFAULT NULL COMMENT '1：是  0：不是',
  `password` varchar(50) DEFAULT NULL COMMENT '九宫格密码',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `contact_id` bigint(10) DEFAULT NULL COMMENT '学生联系方式ID',
  `relation_name` varchar(50) DEFAULT NULL COMMENT '学生联系人称谓',
  `birthday_type` int(1) DEFAULT NULL COMMENT '1：阳历；2：阴历',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '学生昵称',
  `login_no` varchar(20) DEFAULT NULL COMMENT 'APP登录名',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_student_info
-- ----------------------------

-- ----------------------------
-- Table structure for tab_teacher_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_teacher_info`;
CREATE TABLE `tab_teacher_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `encoding` varchar(50) NOT NULL COMMENT '教师code',
  `teacher_name` varchar(50) NOT NULL COMMENT '教师姓名',
  `sex` int(1) DEFAULT NULL COMMENT '性别(1=男 2=女)',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机',
  `employee_id` bigint(10) DEFAULT NULL COMMENT '员工ID',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `teacher_type` int(1) DEFAULT NULL COMMENT '教师身份 1-主讲老师 2-辅导老师-双师  3-普通老师 4-辅导老师-培英',
  `photo` varchar(300) DEFAULT NULL COMMENT '头像',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tab_teacher_info
-- ----------------------------

-- ----------------------------
-- Table structure for tab_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_info`;
CREATE TABLE `tab_user_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `employee_id` bigint(10) NOT NULL COMMENT '员工id',
  `status` bigint(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `update_pwd_time` datetime DEFAULT NULL COMMENT '密码更新时间',
  `sys_update` int(1) DEFAULT NULL COMMENT '是否管理员更新',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Records of tab_user_info
-- ----------------------------
INSERT INTO `tab_user_info` VALUES ('1', 'root', '5870c1cdca81568aaeb5a180ebcbc34f97e45f45', '1', '1', null, null, null, null, null, null);
INSERT INTO `tab_user_info` VALUES ('2', 'lincm', '5870c1cdca81568aaeb5a180ebcbc34f97e45f45', '2', '1', null, null, null, '2018-03-11 17:26:40', null, null);

-- ----------------------------
-- Table structure for tp_subject
-- ----------------------------
DROP TABLE IF EXISTS `tp_subject`;
CREATE TABLE `tp_subject` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `encoding` varchar(50) NOT NULL COMMENT '科目编码',
  `subject_name` varchar(50) NOT NULL COMMENT '科目名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tp_subject
-- ----------------------------

-- ----------------------------
-- Table structure for user_personal_settings
-- ----------------------------
DROP TABLE IF EXISTS `user_personal_settings`;
CREATE TABLE `user_personal_settings` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(10) NOT NULL COMMENT '用户id',
  `param_name` varchar(100) DEFAULT NULL,
  `param_val` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_personal_settings
-- ----------------------------
INSERT INTO `user_personal_settings` VALUES (6, 1, 'V5_DEFAULT_ORG', '5');


-- ----------------------------
-- Table structure for bu_dict_rel
-- ----------------------------
DROP TABLE IF EXISTS `bu_dict_rel`;
CREATE TABLE `bu_dict_rel` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bu_id` bigint(10) NOT NULL COMMENT '团队ID',
  `dict_id` bigint(10) NOT NULL COMMENT '字典ID',
  `dict_type` varchar(50) NOT NULL COMMENT '字典类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tab_region`;
CREATE TABLE `tab_region` (
  `rid` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint(10) NOT NULL COMMENT '父id',
  `rname` varchar(100) DEFAULT NULL,
  `display_order` bigint(10) DEFAULT NULL,
  `catagery` varchar(100) DEFAULT NULL,
  `postal_code` varchar(100) DEFAULT NULL,
  `old_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tp_dict_data`;
CREATE TABLE `tp_dict_data` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(100) NOT NULL COMMENT '编码',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `order_no` bigint(10) DEFAULT NULL COMMENT '',
  `type_id` bigint(10) DEFAULT NULL COMMENT '',
  `descdtl` varchar(300) DEFAULT NULL COMMENT '',
  `bu_id` bigint(10) NOT NULL  COMMENT '组织id',
  `simp_name` varchar(100) DEFAULT NULL COMMENT '',
  `orig_id` bigint(10) DEFAULT NULL COMMENT '',
  `product_line` bigint(10) DEFAULT NULL COMMENT '',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tp_dict_type`;
CREATE TABLE `tp_dict_type` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(100) NOT NULL COMMENT '编码',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `status` int(1) NOT NULL COMMENT '状态(1=有效 0=逻辑删除)',
  `descdtl` varchar(300) DEFAULT NULL COMMENT '',
  `create_user` bigint(10) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(10) DEFAULT NULL COMMENT '修改用户',
  `update_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tab_role_org_ref`;
CREATE TABLE `tab_role_org_ref` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(10) NOT NULL COMMENT '角色id',
  `org_id` bigint(10) NOT NULL COMMENT '组织id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

