/*
Navicat MySQL Data Transfer

Source Server         : 118.190.52.54
Source Server Version : 50728
Source Host           : 118.190.52.54:3306
Source Database       : myvend

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2019-12-16 11:44:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_log
-- ----------------------------
DROP TABLE IF EXISTS `account_log`;
CREATE TABLE `account_log` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `accountLog` varchar(255) NOT NULL COMMENT '流水号',
  `memberId` int(11) NOT NULL COMMENT '员工Id',
  `memberName` varchar(255) NOT NULL COMMENT '员工名称',
  `moneyType` tinyint(11) NOT NULL COMMENT '积分',
  `money` decimal(9,2) NOT NULL COMMENT '彩票',
  `dataSrc` varchar(255) NOT NULL,
  `createDate` datetime NOT NULL COMMENT '拨款时间',
  `tradeType` tinyint(4) NOT NULL COMMENT '交易类型(1.充值 2消费 3 退货 4进 5出 6 退款)',
  `comsumeLog` varchar(255) NOT NULL COMMENT '交易号',
  PRIMARY KEY (`id`),
  KEY `FK_comsume_memberId` (`memberId`) USING BTREE,
  CONSTRAINT `account_log_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_log
-- ----------------------------

-- ----------------------------
-- Table structure for agent
-- ----------------------------
DROP TABLE IF EXISTS `agent`;
CREATE TABLE `agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理商',
  `name` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `loginName` varchar(255) NOT NULL,
  `rate` decimal(11,4) DEFAULT NULL COMMENT '费率',
  `linkName` varchar(255) DEFAULT NULL,
  `linkPhone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `leftMoney` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '余额',
  `lockMoney` decimal(9,2) NOT NULL DEFAULT '0.00' COMMENT '冻结资金',
  `score` int(11) NOT NULL DEFAULT '0' COMMENT '积分',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0:未审核1:审核通过2:禁用',
  `agentRoleId` int(11) DEFAULT NULL COMMENT '角色',
  `agentRoleName` varchar(255) DEFAULT NULL COMMENT '代理角色',
  PRIMARY KEY (`id`),
  KEY `agentRoleId` (`agentRoleId`),
  CONSTRAINT `agent_ibfk_1` FOREIGN KEY (`agentRoleId`) REFERENCES `agent_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agent
-- ----------------------------
INSERT INTO `agent` VALUES ('18', '李四', 'e10adc3949ba59abbe56e057f20f883e', 'rx123', '0.0200', '瘦子', '15986413735', '上海', '0.00', '0.00', '64', '1', '3', '一级代理');
INSERT INTO `agent` VALUES ('19', '华亦', 'e10adc3949ba59abbe56e057f20f883e', 'hywlw', '0.0060', null, null, null, '0.00', '0.00', '0', '1', '3', '一级代理');

-- ----------------------------
-- Table structure for agent_role
-- ----------------------------
DROP TABLE IF EXISTS `agent_role`;
CREATE TABLE `agent_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(11) NOT NULL COMMENT '名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of agent_role
-- ----------------------------
INSERT INTO `agent_role` VALUES ('3', '一级代理');
INSERT INTO `agent_role` VALUES ('4', '二级代理');

-- ----------------------------
-- Table structure for cash_apply
-- ----------------------------
DROP TABLE IF EXISTS `cash_apply`;
CREATE TABLE `cash_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` decimal(9,2) NOT NULL COMMENT '金额',
  `income` decimal(9,2) DEFAULT NULL COMMENT '到账金额',
  `adminMoney` decimal(9,2) DEFAULT NULL COMMENT '运营商到账',
  `agentId` int(11) NOT NULL COMMENT '代理商Id',
  `agentName` varchar(255) NOT NULL,
  `typeName` varchar(255) NOT NULL COMMENT '方式',
  `accNo` varchar(255) NOT NULL COMMENT '收款号码',
  `accUser` varchar(255) NOT NULL COMMENT '收款人',
  `area` varchar(255) DEFAULT NULL COMMENT '区域',
  `createDate` datetime NOT NULL COMMENT '申请时间',
  `handlerDate` datetime DEFAULT NULL COMMENT '处理时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(255) NOT NULL COMMENT '状态(0:待审核1:驳回2:已完成)',
  PRIMARY KEY (`id`),
  KEY `agentId` (`agentId`),
  CONSTRAINT `cash_apply_ibfk_1` FOREIGN KEY (`agentId`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cash_apply
-- ----------------------------

-- ----------------------------
-- Table structure for cash_config
-- ----------------------------
DROP TABLE IF EXISTS `cash_config`;
CREATE TABLE `cash_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `agentId` int(11) NOT NULL COMMENT '代理商Id',
  `type` varchar(255) NOT NULL COMMENT '类型',
  `accNo` varchar(255) NOT NULL COMMENT '收款号码',
  `accUser` varchar(255) NOT NULL COMMENT '收款人',
  `area` varchar(255) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `agentId` (`agentId`),
  CONSTRAINT `cash_config_ibfk_1` FOREIGN KEY (`agentId`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cash_config
-- ----------------------------

-- ----------------------------
-- Table structure for comsume_log
-- ----------------------------
DROP TABLE IF EXISTS `comsume_log`;
CREATE TABLE `comsume_log` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '消费记录id',
  `comsumeLog` varchar(255) DEFAULT NULL COMMENT '订单流水编号',
  `orderId` varchar(100) NOT NULL COMMENT '订单号',
  `memberId` int(11) DEFAULT NULL COMMENT '会员ID',
  `memberName` varchar(255) DEFAULT NULL COMMENT '会员名称',
  `devName` varchar(255) NOT NULL COMMENT '设备名称',
  `devNum` varchar(100) NOT NULL COMMENT '设备号',
  `productId` int(11) DEFAULT NULL COMMENT '商品ID',
  `productName` varchar(255) DEFAULT NULL COMMENT '商品名',
  `userId` int(11) DEFAULT NULL COMMENT '设备所属商户',
  `userName` varchar(255) DEFAULT NULL COMMENT '商户名',
  `agentName` varchar(255) DEFAULT NULL COMMENT '代理商名',
  `agentId` int(11) DEFAULT NULL COMMENT '商户所属代理商',
  `payType` tinyint(255) DEFAULT '0' COMMENT '支付方式（1微信、2支付宝）',
  `payAmount` decimal(16,2) DEFAULT NULL COMMENT '消费金额',
  `time` varchar(255) DEFAULT NULL COMMENT '洗车时长',
  `createDate` datetime NOT NULL COMMENT '交易时间',
  `tradeType` tinyint(2) DEFAULT NULL COMMENT '交易类型(1消费 2充值 3退款)',
  `payStatus` tinyint(2) DEFAULT NULL COMMENT '支付状态(1成功 2异常 3进行中 4失败)',
  `modifyTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_comsume_memberId` (`memberId`) USING BTREE,
  KEY `orderId` (`orderId`),
  CONSTRAINT `comsume_log_ibfk_1` FOREIGN KEY (`memberId`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4407 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comsume_log
-- ----------------------------
INSERT INTO `comsume_log` VALUES ('4367', '4200000424201912068986646672', '1202965735685754880', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-06 22:59:16', '1', '1', '2019-12-07 10:53:55');
INSERT INTO `comsume_log` VALUES ('4368', '4200000446201912077959458768', '1203160687183138816', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-07 11:53:56', '1', '1', '2019-12-07 11:54:07');
INSERT INTO `comsume_log` VALUES ('4369', '4200000426201912072677894061', '1203232454157275136', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-07 16:39:07', '1', '1', '2019-12-07 16:39:17');
INSERT INTO `comsume_log` VALUES ('4370', '4200000428201912091146048239', '1203938153426325504', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-09 15:23:19', '1', '1', '2019-12-09 15:37:35');
INSERT INTO `comsume_log` VALUES ('4371', null, '1203939865801265152', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-09 15:30:07', '1', '3', '2019-12-09 15:30:06');
INSERT INTO `comsume_log` VALUES ('4372', '4200000430201912098006017122', '1203941625286627328', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-09 15:37:06', '1', '1', '2019-12-09 15:37:15');
INSERT INTO `comsume_log` VALUES ('4373', null, '1203948237745295360', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-09 16:03:23', '1', '3', '2019-12-09 16:03:22');
INSERT INTO `comsume_log` VALUES ('4374', null, '1203948599130722304', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-09 16:04:49', '1', '3', '2019-12-09 16:04:48');
INSERT INTO `comsume_log` VALUES ('4375', null, '1203949561769627648', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-09 16:08:39', '1', '3', '2019-12-09 16:08:38');
INSERT INTO `comsume_log` VALUES ('4376', '4200000443201912091597571785', '1203950915649015808', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-09 16:14:01', '1', '1', '2019-12-09 16:14:10');
INSERT INTO `comsume_log` VALUES ('4377', '4200000444201912106239418648', '1204224624376942592', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 10:21:39', '1', '2', '2019-12-10 11:03:24');
INSERT INTO `comsume_log` VALUES ('4378', '4200000429201912105906710382', '1204241387386834944', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 11:28:15', '1', '1', '2019-12-10 11:28:25');
INSERT INTO `comsume_log` VALUES ('4379', '4200000439201912104661993968', '1204242352336801792', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 11:32:05', '3', '1', '2019-12-10 16:37:18');
INSERT INTO `comsume_log` VALUES ('4380', '4200000433201912107367560917', '1204323161194369024', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 16:53:12', '1', '1', '2019-12-10 16:54:08');
INSERT INTO `comsume_log` VALUES ('4381', '4200000423201912100432356355', '1204324418990641152', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 16:58:11', '1', '1', '2019-12-10 16:59:09');
INSERT INTO `comsume_log` VALUES ('4382', '4200000440201912104881967716', '1204326110163374080', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 17:04:55', '1', '1', '2019-12-10 17:05:38');
INSERT INTO `comsume_log` VALUES ('4383', '4200000422201912105024598260', '1204327100627292160', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 17:08:51', '1', '1', '2019-12-10 17:09:31');
INSERT INTO `comsume_log` VALUES ('4384', '4200000439201912108775941821', '1204330372020834304', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-10 17:21:51', '1', '1', '2019-12-10 17:22:31');
INSERT INTO `comsume_log` VALUES ('4385', '4200000443201912114431591184', '1204607379388502016', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-11 11:42:34', '1', '1', '2019-12-11 11:43:23');
INSERT INTO `comsume_log` VALUES ('4386', '4200000444201912127283908849', '1205110006652080128', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-12 20:59:50', '1', '3', '2019-12-12 21:00:03');
INSERT INTO `comsume_log` VALUES ('4387', null, '1205110174998859776', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-12 21:00:30', '1', '3', '2019-12-12 21:00:30');
INSERT INTO `comsume_log` VALUES ('4388', null, '1205125144931209216', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-12 21:59:59', '1', '3', '2019-12-12 21:59:59');
INSERT INTO `comsume_log` VALUES ('4389', null, '1205303356462206976', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 09:48:08', '1', '3', '2019-12-13 09:48:08');
INSERT INTO `comsume_log` VALUES ('4390', null, '1205307528020496384', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 10:04:43', '1', '3', '2019-12-13 10:04:42');
INSERT INTO `comsume_log` VALUES ('4391', null, '1205311675969048576', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 10:21:12', '1', '3', '2019-12-13 10:21:11');
INSERT INTO `comsume_log` VALUES ('4392', null, '1205315763054055424', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 10:37:26', '1', '3', '2019-12-13 10:37:26');
INSERT INTO `comsume_log` VALUES ('4393', null, '1205320559219707904', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 10:56:30', '1', '3', '2019-12-13 10:56:29');
INSERT INTO `comsume_log` VALUES ('4394', null, '1205335308535861248', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 11:55:06', '1', '3', '2019-12-13 11:55:06');
INSERT INTO `comsume_log` VALUES ('4395', null, '1205366298285182976', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-13 13:58:15', '1', '3', '2019-12-13 13:58:14');
INSERT INTO `comsume_log` VALUES ('4396', '2019121322001408290530703554', '1205374124470571008', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 14:29:21', '1', '1', '2019-12-13 15:55:46');
INSERT INTO `comsume_log` VALUES ('4397', '2019121322001408290533155716', '1205383267877130240', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 15:05:41', '1', '1', '2019-12-13 16:31:41');
INSERT INTO `comsume_log` VALUES ('4398', '2019121322001408290533226969', '1205389965639553024', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 15:32:18', '1', '1', '2019-12-13 15:57:36');
INSERT INTO `comsume_log` VALUES ('4399', '2019121322001408290533069545', '1205395744597741568', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 15:55:15', '1', '3', '2019-12-13 17:24:02');
INSERT INTO `comsume_log` VALUES ('4400', '2019121322001408290533334910', '1205427377170288640', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 18:00:57', '1', '1', '2019-12-13 18:01:12');
INSERT INTO `comsume_log` VALUES ('4401', '2019121322001408290533193354', '1205430996934332416', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 18:15:20', '1', '1', '2019-12-13 18:15:29');
INSERT INTO `comsume_log` VALUES ('4402', '2019121322001408290533480535', '1205435065883168768', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-13 18:31:30', '1', '1', '2019-12-13 18:31:38');
INSERT INTO `comsume_log` VALUES ('4403', null, '1205729276008337408', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-14 14:00:35', '1', '3', '2019-12-14 14:00:35');
INSERT INTO `comsume_log` VALUES ('4404', null, '1205729319813648384', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '0', '0.01', null, '2019-12-14 14:00:46', '1', '3', '2019-12-14 14:00:45');
INSERT INTO `comsume_log` VALUES ('4405', '4200000431201912144572691382', '1205731628182409216', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '1', '0.01', null, '2019-12-14 14:09:56', '3', '2', '2019-12-14 14:10:51');
INSERT INTO `comsume_log` VALUES ('4406', '2019121422001408290533553668', '1205734980404383744', null, null, '李四', '1000101', '6', '李四', '55', '赵六', '李四', '18', '2', '0.01', null, '2019-12-14 14:23:15', '3', '2', '2019-12-14 14:23:59');

-- ----------------------------
-- Table structure for counting_log
-- ----------------------------
DROP TABLE IF EXISTS `counting_log`;
CREATE TABLE `counting_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品Id',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `productNum` int(5) NOT NULL COMMENT '商品编号',
  `productId` int(11) NOT NULL,
  `count` int(255) NOT NULL DEFAULT '0' COMMENT '数量',
  `devId` int(11) NOT NULL,
  `devNum` int(11) NOT NULL COMMENT '设备编号',
  `agentId` int(11) NOT NULL,
  `type` tinyint(255) NOT NULL,
  `createTime` datetime NOT NULL COMMENT '计数时间',
  PRIMARY KEY (`id`),
  KEY `FK_productLog_productId` (`productId`) USING BTREE,
  KEY `agentId` (`devId`),
  KEY `agentId_2` (`agentId`),
  CONSTRAINT `counting_log_ibfk_2` FOREIGN KEY (`devId`) REFERENCES `dev` (`id`),
  CONSTRAINT `counting_log_ibfk_3` FOREIGN KEY (`agentId`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of counting_log
-- ----------------------------

-- ----------------------------
-- Table structure for dev
-- ----------------------------
DROP TABLE IF EXISTS `dev`;
CREATE TABLE `dev` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备管理',
  `name` varchar(255) NOT NULL COMMENT '设备名字',
  `num` int(10) NOT NULL COMMENT '设备编号',
  `shortNum` int(5) NOT NULL COMMENT '设备自编号',
  `token` int(32) DEFAULT NULL COMMENT 'Token令牌',
  `userId` int(11) NOT NULL COMMENT '商户ID',
  `userName` varchar(255) NOT NULL COMMENT '商户名',
  `agentId` int(11) DEFAULT NULL COMMENT '商户所属代理商',
  `phone` varchar(11) DEFAULT NULL COMMENT '客服电话',
  `speed1` int(5) DEFAULT NULL COMMENT '购买速度',
  `speed2` int(5) DEFAULT NULL COMMENT '退货速度',
  `KD` int(5) DEFAULT NULL COMMENT '购买脉冲',
  `KD2` int(5) DEFAULT NULL COMMENT '退货脉冲',
  `CSL` float(16,3) NOT NULL DEFAULT '0.000' COMMENT '感应扣款金额',
  `TF` bit(1) DEFAULT b'0' COMMENT '是否可退货(1为可退，2为不可退）',
  `TFType` bit(1) DEFAULT NULL COMMENT '退货模式(0为高电平，1为代电平）',
  `TBPercentage` int(255) DEFAULT NULL COMMENT '退货比例',
  `TFMS` int(255) DEFAULT NULL COMMENT '购买比例',
  `commodity` int(5) DEFAULT NULL COMMENT '绑定的商品数量',
  `payType` varchar(20) DEFAULT NULL COMMENT '支付类型(售货机使用）(1为金币、2为彩票、3为积分、4为门票、5为年票.支付多种支付类型用逗号分隔',
  `tradeWay` int(255) DEFAULT NULL,
  `state` int(1) DEFAULT NULL COMMENT '设备状态(根据心跳包为主，每3分种通信一次）（状态分3种：在线、离线、下架）1.在线 2.离线 3.下架',
  `Obtain` int(1) DEFAULT NULL COMMENT '获取参数开关（开则为该设备参数可获取，反之不能获取）1.开启 2.停用',
  `devStatus` int(1) DEFAULT NULL COMMENT '设备状态(1代表空闲，2代表忙碌)',
  `frothStatus` int(1) DEFAULT NULL COMMENT '设备泡沫状态（1为充足，2为不足）',
  `beatTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `num` (`num`),
  KEY `userId` (`userId`),
  CONSTRAINT `dev_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dev
-- ----------------------------
INSERT INTO `dev` VALUES ('62', '李四', '1000101', '1', '12345678', '55', '赵六', '18', '15986413731', null, null, null, null, '0.020', '\0', null, null, null, null, null, null, '1', null, '1', '1', '2019-11-20 22:54:54');
INSERT INTO `dev` VALUES ('64', '1号机', '1000102', '1', '12345678', '56', '新水坑', '19', '15986413732', null, null, null, null, '0.025', '\0', null, null, null, null, null, null, '2', null, '1', '1', '2019-11-21 09:28:27');
INSERT INTO `dev` VALUES ('65', '12345678', '77777', '1', '12345678', '55', '赵六', '18', '15986413733', null, null, null, null, '0.020', '\0', null, null, null, null, null, null, '2', null, '2', '1', '2019-11-22 15:12:35');
INSERT INTO `dev` VALUES ('66', '12345678', '10011', '1', '123456', '55', '赵六', '18', '15986413734', null, null, null, null, '0.020', '\0', null, null, null, null, null, null, '2', null, '2', '1', '2019-11-23 12:56:36');
INSERT INTO `dev` VALUES ('67', 'ppp', '888888', '1', '12345678', '55', '赵六', '18', '15986413735', null, null, null, null, '0.020', '\0', null, null, null, null, null, null, '2', null, '2', '1', '2019-12-10 13:48:16');
INSERT INTO `dev` VALUES ('68', 'aaaa', '333333', '1', '12345678', '55', '赵六', '18', '15986413736', null, null, null, null, '0.050', '\0', null, null, null, null, null, null, '2', null, '1', '1', '2019-12-11 14:35:59');

-- ----------------------------
-- Table structure for dev_auth
-- ----------------------------
DROP TABLE IF EXISTS `dev_auth`;
CREATE TABLE `dev_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备授权',
  `userId` int(11) DEFAULT NULL COMMENT '代理商Id',
  `userName` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '2' COMMENT '1:已使用2:未使用',
  `devNum` varchar(100) NOT NULL COMMENT '设备号',
  PRIMARY KEY (`id`),
  KEY `FK_agentId` (`userId`) USING BTREE,
  CONSTRAINT `dev_auth_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dev_auth
-- ----------------------------
INSERT INTO `dev_auth` VALUES ('89', '55', '赵六', '1', '1000101');
INSERT INTO `dev_auth` VALUES ('97', '55', '赵六', '1', '77777');
INSERT INTO `dev_auth` VALUES ('98', '55', '赵六', '1', '10011');
INSERT INTO `dev_auth` VALUES ('99', '56', '新水坑', '1', '1000102');
INSERT INTO `dev_auth` VALUES ('100', '55', '赵六', '1', '888888');
INSERT INTO `dev_auth` VALUES ('101', '55', '赵六', '1', '333333');

-- ----------------------------
-- Table structure for meal
-- ----------------------------
DROP TABLE IF EXISTS `meal`;
CREATE TABLE `meal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '套餐名',
  `memey` decimal(16,2) NOT NULL COMMENT '充值金额',
  `gift` decimal(16,2) DEFAULT NULL COMMENT '赠送金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meal
-- ----------------------------
INSERT INTO `meal` VALUES ('1', '套餐1', '1.00', '100.00');
INSERT INTO `meal` VALUES ('2', '套餐2', '0.01', '2.00');
INSERT INTO `meal` VALUES ('3', '套餐3', '0.03', '3.00');
INSERT INTO `meal` VALUES ('4', '套餐4', '0.04', '4.00');
INSERT INTO `meal` VALUES ('6', '套餐5', '0.05', '5.00');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `account` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `loginName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `balance` decimal(20,2) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `memberMsg` varchar(255) DEFAULT NULL COMMENT '卡号',
  `score` int(255) NOT NULL DEFAULT '0',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '微信用户头像',
  `status` tinyint(255) NOT NULL COMMENT '状态(0正常 1冻结)',
  `registeDate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('74', '449c0c3e12b', 'Somree', null, null, '10.00', 'ox6231Kl1oBHydE9QbIZwa2AHry8', '微信用户', '0', 'http://thirdwx.qlogo.cn/mmopen/vi_32/S7LEdPRdeCvlb3FcQpQFC2HoElUbr1ZwKsCLHldnuNvbL9US3t75r9lBjOCZ5YzUPgB0ATTwJroywicPa3PKic5A/132', '1', '2019-11-20');
INSERT INTO `member` VALUES ('75', '7d345a6f78a', '嗯嗯', null, null, '10.00', 'ox6231HnpqceDdGRXs_z71r0bGng', '微信用户', '0', 'http://thirdwx.qlogo.cn/mmopen/vi_32/khSCxaCDAVZ8FqC7AzMorKrZvXNghicXE6msFL05pvmr4bE3zgsMKa7gAcnMYbXmhmAe16IqCeRpdKcnqbVRezw/132', '1', '2019-11-20');
INSERT INTO `member` VALUES ('76', '60661d926c3', '售后服务专用', '13725386112', '052982ee5fa24eae779485b781d5a66a', '0.00', 'ox6231GLNJN2HWFmJTppb-ZIThEo', '微信用户', '0', 'http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIJqoCHH8ib9g5WL2K4J9zE1PQPouGbyO08CibZnL2KDoHcbuX6vbfBfPwGlSfdNIIJSDZsasfd9R2Q/132', '1', '2019-11-21');
INSERT INTO `member` VALUES ('77', '5c6dc6ce8ee', '云万售货机', '15603003881', '052982ee5fa24eae779485b781d5a66a', '8.27', 'ox6231O8qL50QleqmbBZN72eLYfQ', '微信用户', '0', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Hbg9S52nF2UkhGjvZ6QEaxLeoGYMHuib0B6LhK74LIcBSpxozn4OfHSMVmwaticg1IOc3icp7oKj5rBNW4AyOUsxA/132', '1', '2019-11-22');
INSERT INTO `member` VALUES ('79', '4a97f7b6457', '阿乐', '15986413735', '25d55ad283aa400af464c76d713c07ad', '8.21', 'ox6231IA1qpLHuGpqMNWgoDMl_Do', '微信用户', '0', 'http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJ0oa4xRepp2ObTRGjNIGSuohhgoluGQGBxz6HcMfPoT7QDHviaquNdQYWy7UZPKDcttEEVlKnypMA/132', '1', '2019-11-23');

-- ----------------------------
-- Table structure for order_account
-- ----------------------------
DROP TABLE IF EXISTS `order_account`;
CREATE TABLE `order_account` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `agentId` int(11) NOT NULL COMMENT '代理Id',
  `agentName` varchar(255) NOT NULL COMMENT '代理名称',
  `finishCount` int(255) DEFAULT NULL,
  `errorCount` int(255) DEFAULT NULL,
  `payAmount` decimal(16,2) NOT NULL COMMENT '订单金额',
  `createDate` varchar(10) NOT NULL COMMENT '统计时间',
  PRIMARY KEY (`id`),
  KEY `agentId` (`agentId`),
  CONSTRAINT `order_account_ibfk_2` FOREIGN KEY (`agentId`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_account
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `productNum` varchar(15) NOT NULL,
  `price` decimal(9,2) NOT NULL,
  `storeCount` int(11) NOT NULL,
  `img` varchar(255) NOT NULL,
  `productGroupId` int(11) NOT NULL,
  `productGroupName` varchar(255) NOT NULL,
  `status` int(2) NOT NULL COMMENT '商品状态（1：上架 2：下架）',
  `userId` int(11) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `agentId` int(11) NOT NULL,
  `agentName` varchar(255) NOT NULL,
  `orderNum` int(3) NOT NULL COMMENT '货道',
  `threshold` int(11) NOT NULL COMMENT '阀值（库存低于阀值为缺货）',
  `countStatus` int(2) DEFAULT NULL COMMENT '缺货状态(1：不缺2：缺货)',
  PRIMARY KEY (`id`),
  KEY `productGroupId` (`productGroupId`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`productGroupId`) REFERENCES `productgroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('2', 'qqwwe', '000000', '5.00', '10', '2019/11/27\\\\2019_11_27_6c9c1edd-f353-446b-a5ea-68af44736839.jpg', '3', 'gfdg', '2', '55', '赵六', '18', '李四', '2', '5', '1');
INSERT INTO `product` VALUES ('3', 'qqww', '111111', '5.00', '10', '2019/11/28\\\\2019_11_28_17fa3177-0719-4739-9788-8dbe81eca232.jpg', '5', 'gfdg', '2', '55', '赵六', '18', '李四', '1', '5', '1');
INSERT INTO `product` VALUES ('4', 'qq', '999999', '5.00', '10', '2019/11/28\\\\2019_11_28_58332bab-a448-49db-9666-ae387cd43fe8.jpg', '5', 'gfdg', '2', '55', '赵六', '18', '李四', '1', '5', '1');
INSERT INTO `product` VALUES ('5', 'zz', '999999', '0.01', '10', '2019/12/03//2019_12_03_b178c08f-6bf9-457b-ae86-35e563124caa.jpg', '4', 'jhgj', '2', '55', '赵六', '18', '李四', '1', '5', '1');
INSERT INTO `product` VALUES ('6', '李四', '333333', '0.01', '100', '2019/12/03//2019_12_03_4d1e3057-3282-4159-873a-7e5d67fadfed.jpg', '4', 'jhgj', '1', '55', '赵六', '18', '李四', '2', '5', '1');
INSERT INTO `product` VALUES ('7', 'yy', '999999', '0.01', '3', '2019/12/14\\\\2019_12_14_e83f953a-eb61-4102-be3a-4ce47f2dbc0a.jpg', '4', 'jhgj', '2', '55', '赵六', '18', '李四', '3', '5', '2');
INSERT INTO `product` VALUES ('8', 'eee', '222222', '0.01', '3', '2019/12/14\\\\2019_12_14_77e8ff1f-9783-4d65-b533-95a470b35b2e.jpg', '5', 'gfdg', '2', '55', '赵六', '18', '李四', '4', '5', '2');
INSERT INTO `product` VALUES ('9', 'gfdg', '111111', '5.00', '3', '2019/12/14\\\\2019_12_14_5810eaa7-e3ea-430e-b4da-bf1568e65bb8.jpg', '4', 'jhgj', '2', '55', '赵六', '18', '李四', '4', '5', '2');
INSERT INTO `product` VALUES ('10', 'jhgj', '999999', '0.01', '3', '2019/12/14\\\\2019_12_14_5bb71156-5a77-4d93-86a6-edc2b2894de3.jpg', '5', 'gfdg', '2', '55', '赵六', '18', '李四', '4', '5', '2');

-- ----------------------------
-- Table structure for productgroup
-- ----------------------------
DROP TABLE IF EXISTS `productgroup`;
CREATE TABLE `productgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `num` int(11) NOT NULL COMMENT '商品组自编号',
  `devId` int(11) NOT NULL,
  `devNum` varchar(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `agentId` int(11) NOT NULL,
  `agentName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `devId` (`devId`),
  KEY `userId` (`userId`),
  CONSTRAINT `productgroup_ibfk_1` FOREIGN KEY (`devId`) REFERENCES `dev` (`id`),
  CONSTRAINT `productgroup_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productgroup
-- ----------------------------
INSERT INTO `productgroup` VALUES ('3', 'gfdg', '2', '65', '77777', '55', '赵六', '18', '李四');
INSERT INTO `productgroup` VALUES ('4', 'jhgj', '1', '62', '1000101', '55', '赵六', '18', '李四');
INSERT INTO `productgroup` VALUES ('5', 'gfdg', '2', '66', '10011', '55', '赵六', '18', '李四');

-- ----------------------------
-- Table structure for recharge_log
-- ----------------------------
DROP TABLE IF EXISTS `recharge_log`;
CREATE TABLE `recharge_log` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `rechargelog` varchar(255) DEFAULT NULL COMMENT '自己生成的订单号',
  `memberId` int(11) NOT NULL COMMENT '会员id',
  `memberAccount` varchar(255) NOT NULL COMMENT '会员账号',
  `memberName` varchar(255) NOT NULL COMMENT '会员名',
  `recharge_momey` decimal(16,2) NOT NULL COMMENT '充值金额',
  `balance` decimal(16,2) NOT NULL COMMENT '充值后的余额',
  `gift` decimal(16,2) DEFAULT NULL COMMENT '赠送金额',
  `payType` tinyint(255) DEFAULT NULL COMMENT '支付方式（1微信 2支付宝）',
  `payStatus` tinyint(2) NOT NULL COMMENT '支付状态(1成功 2进行中 3未进行 4失败)',
  `remark` varchar(255) DEFAULT NULL COMMENT '充值备注',
  `createDate` datetime NOT NULL COMMENT '充值时间',
  PRIMARY KEY (`id`),
  KEY `memberId` (`memberId`),
  CONSTRAINT `recharge_log_ibfk_1` FOREIGN KEY (`memberId`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recharge_log
-- ----------------------------
INSERT INTO `recharge_log` VALUES ('51', '1198070163195564032', '79', '4a97f7b6457', '阿乐', '0.01', '10.11', '2.00', '1', '1', null, '2019-11-23 10:46:01');

-- ----------------------------
-- Table structure for t_agent_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_agent_role_menu`;
CREATE TABLE `t_agent_role_menu` (
  `menuId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`,`menuId`),
  KEY `menuId` (`menuId`),
  CONSTRAINT `t_agent_role_menu_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `agent_role` (`id`),
  CONSTRAINT `t_agent_role_menu_ibfk_3` FOREIGN KEY (`menuId`) REFERENCES `t_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_agent_role_menu
-- ----------------------------
INSERT INTO `t_agent_role_menu` VALUES ('1', '3');
INSERT INTO `t_agent_role_menu` VALUES ('2', '3');
INSERT INTO `t_agent_role_menu` VALUES ('3', '3');
INSERT INTO `t_agent_role_menu` VALUES ('5', '3');
INSERT INTO `t_agent_role_menu` VALUES ('6', '3');
INSERT INTO `t_agent_role_menu` VALUES ('7', '3');
INSERT INTO `t_agent_role_menu` VALUES ('23', '3');
INSERT INTO `t_agent_role_menu` VALUES ('24', '3');
INSERT INTO `t_agent_role_menu` VALUES ('26', '3');
INSERT INTO `t_agent_role_menu` VALUES ('37', '3');
INSERT INTO `t_agent_role_menu` VALUES ('38', '3');
INSERT INTO `t_agent_role_menu` VALUES ('39', '3');
INSERT INTO `t_agent_role_menu` VALUES ('58', '3');
INSERT INTO `t_agent_role_menu` VALUES ('59', '3');
INSERT INTO `t_agent_role_menu` VALUES ('60', '3');
INSERT INTO `t_agent_role_menu` VALUES ('71', '3');
INSERT INTO `t_agent_role_menu` VALUES ('72', '3');
INSERT INTO `t_agent_role_menu` VALUES ('80', '3');
INSERT INTO `t_agent_role_menu` VALUES ('81', '3');
INSERT INTO `t_agent_role_menu` VALUES ('82', '3');
INSERT INTO `t_agent_role_menu` VALUES ('84', '3');
INSERT INTO `t_agent_role_menu` VALUES ('85', '3');
INSERT INTO `t_agent_role_menu` VALUES ('86', '3');
INSERT INTO `t_agent_role_menu` VALUES ('87', '3');
INSERT INTO `t_agent_role_menu` VALUES ('88', '3');
INSERT INTO `t_agent_role_menu` VALUES ('89', '3');
INSERT INTO `t_agent_role_menu` VALUES ('90', '3');
INSERT INTO `t_agent_role_menu` VALUES ('91', '3');
INSERT INTO `t_agent_role_menu` VALUES ('92', '3');
INSERT INTO `t_agent_role_menu` VALUES ('93', '3');
INSERT INTO `t_agent_role_menu` VALUES ('94', '3');

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('27', '授权卡状态', '1', null);
INSERT INTO `t_dict` VALUES ('28', '订单状态', '2', null);
INSERT INTO `t_dict` VALUES ('29', '退货模式', '3', null);
INSERT INTO `t_dict` VALUES ('30', '是否可退货', '4', null);
INSERT INTO `t_dict` VALUES ('31', '支付类型', '5', null);
INSERT INTO `t_dict` VALUES ('32', '设备状态', '6', null);
INSERT INTO `t_dict` VALUES ('34', '获取参数开关', '8', null);
INSERT INTO `t_dict` VALUES ('35', '代理商审核状态', '9', null);
INSERT INTO `t_dict` VALUES ('36', '代理商设备使用状态', '10', null);
INSERT INTO `t_dict` VALUES ('37', '交易类型', '11', 'test');
INSERT INTO `t_dict` VALUES ('38', '计数类型', '12', '代表出货还是进货');
INSERT INTO `t_dict` VALUES ('39', '交易方式', '13', '售货机使用');
INSERT INTO `t_dict` VALUES ('40', '提现状态', '14', null);
INSERT INTO `t_dict` VALUES ('41', '支付状态', '15', '会员充值');

-- ----------------------------
-- Table structure for t_dictval
-- ----------------------------
DROP TABLE IF EXISTS `t_dictval`;
CREATE TABLE `t_dictval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `dictId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4db94djtdae9nx54gfd91wa64` (`dictId`) USING BTREE,
  CONSTRAINT `t_dictval_ibfk_1` FOREIGN KEY (`dictId`) REFERENCES `t_dict` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictval
-- ----------------------------
INSERT INTO `t_dictval` VALUES ('42', '可退', '0', '30');
INSERT INTO `t_dictval` VALUES ('43', '不可退', '1', '30');
INSERT INTO `t_dictval` VALUES ('49', '在线', '1', '32');
INSERT INTO `t_dictval` VALUES ('50', '离线', '2', '32');
INSERT INTO `t_dictval` VALUES ('55', '开启', '1', '34');
INSERT INTO `t_dictval` VALUES ('56', '停用', '2', '34');
INSERT INTO `t_dictval` VALUES ('57', '未审核', '3', '35');
INSERT INTO `t_dictval` VALUES ('58', '审核通过', '1', '35');
INSERT INTO `t_dictval` VALUES ('59', '禁用', '2', '35');
INSERT INTO `t_dictval` VALUES ('60', '未使用', '0', '36');
INSERT INTO `t_dictval` VALUES ('61', '已使用', '1', '36');
INSERT INTO `t_dictval` VALUES ('62', '已使用', '1', '27');
INSERT INTO `t_dictval` VALUES ('63', '未使用', '2', '27');
INSERT INTO `t_dictval` VALUES ('78', '异常订单', '4', '28');
INSERT INTO `t_dictval` VALUES ('79', '无效', '3', '28');
INSERT INTO `t_dictval` VALUES ('80', '交易完成', '2', '28');
INSERT INTO `t_dictval` VALUES ('81', '已支付', '1', '28');
INSERT INTO `t_dictval` VALUES ('82', '待支付', '0', '28');
INSERT INTO `t_dictval` VALUES ('85', '积分', '3', '31');
INSERT INTO `t_dictval` VALUES ('86', '彩票', '2', '31');
INSERT INTO `t_dictval` VALUES ('87', '金币', '1', '31');
INSERT INTO `t_dictval` VALUES ('88', '微信', '6', '31');
INSERT INTO `t_dictval` VALUES ('89', '支付宝', '7', '31');
INSERT INTO `t_dictval` VALUES ('91', '进货', '1', '38');
INSERT INTO `t_dictval` VALUES ('92', '出货', '2', '38');
INSERT INTO `t_dictval` VALUES ('93', '金币', '1', '39');
INSERT INTO `t_dictval` VALUES ('94', '金币彩票', '2', '39');
INSERT INTO `t_dictval` VALUES ('95', '金币积分', '3', '39');
INSERT INTO `t_dictval` VALUES ('97', '积分', '5', '39');
INSERT INTO `t_dictval` VALUES ('99', '门票', '7', '39');
INSERT INTO `t_dictval` VALUES ('100', '底电平', '1', '29');
INSERT INTO `t_dictval` VALUES ('101', '高电平', '0', '29');
INSERT INTO `t_dictval` VALUES ('102', '待审核', '0', '40');
INSERT INTO `t_dictval` VALUES ('103', '不通过', '1', '40');
INSERT INTO `t_dictval` VALUES ('104', '已完成', '2', '40');
INSERT INTO `t_dictval` VALUES ('105', '退款', '4', '37');
INSERT INTO `t_dictval` VALUES ('106', '退货', '3', '37');
INSERT INTO `t_dictval` VALUES ('107', '消费', '2', '37');
INSERT INTO `t_dictval` VALUES ('108', '充值', '1', '37');
INSERT INTO `t_dictval` VALUES ('109', '已完成', '1', '41');
INSERT INTO `t_dictval` VALUES ('110', '进行中', '2', '41');
INSERT INTO `t_dictval` VALUES ('111', '未进行', '3', '41');
INSERT INTO `t_dictval` VALUES ('112', '失败', '4', '41');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `URL` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `leaf` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `accessToken` varchar(255) NOT NULL COMMENT '通行证(二级菜单开始下才会有)',
  PRIMARY KEY (`id`),
  KEY `FK_36p7vtyw1vxng18fhtbd6m505` (`parentId`) USING BTREE,
  KEY `accessToken` (`accessToken`) USING BTREE,
  CONSTRAINT `t_menu_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `t_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '', 'fa fa-adjust', '\0', '系统管理', null, '');
INSERT INTO `t_menu` VALUES ('2', '/users', 'fa fa-adjust', '\0', '商户管理', '1', 'users');
INSERT INTO `t_menu` VALUES ('3', '/users/add', null, '', '添加', '2', '');
INSERT INTO `t_menu` VALUES ('4', '/menus', 'fa fa-camera-retro', '\0', '菜单管理', '1', 'menus');
INSERT INTO `t_menu` VALUES ('5', '/roles', 'fa fa-adjust', '\0', '角色管理', '1', 'roles');
INSERT INTO `t_menu` VALUES ('6', '/users/edit', 'fa fa-adjust', '', '编辑', '2', '');
INSERT INTO `t_menu` VALUES ('7', '/users/delete', 'fa fa-adjust', '', '删除', '2', '');
INSERT INTO `t_menu` VALUES ('14', null, 'fa fa-camera-retro', '\0', '参数中心', null, '');
INSERT INTO `t_menu` VALUES ('15', '/dicts', 'fa fa-adjust', '\0', '字典管理', '14', 'xxx');
INSERT INTO `t_menu` VALUES ('16', '', 'fa fa-adjust', '\0', '会员中心', null, 'xxxxxx');
INSERT INTO `t_menu` VALUES ('18', null, 'fa fa-adjust', '\0', '订单中心', null, '');
INSERT INTO `t_menu` VALUES ('19', '/comsumeLogs', 'fa fa-adjust', '', '订单管理', '18', 'test');
INSERT INTO `t_menu` VALUES ('23', '', 'fa fa-adjust', '\0', '设备中心', null, 'center');
INSERT INTO `t_menu` VALUES ('24', '/devs', 'fa fa-adjust', '\0', '设备管理', '23', 'mgr');
INSERT INTO `t_menu` VALUES ('25', '/members', 'fa fa-adjust', '\0', '会员管理', '16', 'xxxxxx');
INSERT INTO `t_menu` VALUES ('26', null, 'fa fa-camera-retro', '\0', '商户中心', null, 'xxx');
INSERT INTO `t_menu` VALUES ('27', '/agents', 'fa fa-camera-retro', '\0', '代理商管理', '26', 'xxx');
INSERT INTO `t_menu` VALUES ('28', '/devAuths', 'fa fa-adjust', '\0', '设备授权管理', '23', '');
INSERT INTO `t_menu` VALUES ('29', '/agents/add', null, '', '添加', '27', '12');
INSERT INTO `t_menu` VALUES ('31', '/agentRoles', 'fa fa-camera-retro', '\0', '代理角色', '26', 'test');
INSERT INTO `t_menu` VALUES ('32', '/agents/delete', 'fa fa-etsy', '', '删除', '27', 'test');
INSERT INTO `t_menu` VALUES ('33', '/agents/edit', 'fa fa-etsy', '', '编辑', '27', 'test');
INSERT INTO `t_menu` VALUES ('34', '/agentRoles/add', 'fa fa-etsy', '', '添加', '31', 'test');
INSERT INTO `t_menu` VALUES ('35', '/agentRoles/edit', 'fa fa-etsy', '', '更新', '31', 'test');
INSERT INTO `t_menu` VALUES ('36', '/agentRoles/delete', 'fa fa-etsy', '', '删除', '31', 'test');
INSERT INTO `t_menu` VALUES ('37', '/roles/add', 'fa fa-etsy', '', '添加', '5', 'test');
INSERT INTO `t_menu` VALUES ('38', '/roles/edit', 'fa fa-etsy', '', '更新', '5', 'test');
INSERT INTO `t_menu` VALUES ('39', '/roles/delete', 'fa fa-etsy', '', '删除', '5', 'test');
INSERT INTO `t_menu` VALUES ('40', '/roles/add', 'fa fa-etsy', '', '添加', '4', 'test');
INSERT INTO `t_menu` VALUES ('41', '/roles/edit', 'fa fa-etsy', '', '更新', '4', 'test');
INSERT INTO `t_menu` VALUES ('42', '/roles/delete', 'fa fa-etsy', '', '删除', '4', 'test');
INSERT INTO `t_menu` VALUES ('49', '/dicts/add', 'fa fa-etsy', '', '添加', '15', 'test');
INSERT INTO `t_menu` VALUES ('50', '/dicts/edit', 'fa fa-etsy', '', '更新', '15', 'test');
INSERT INTO `t_menu` VALUES ('51', '/dicts/delete', 'fa fa-etsy', '', '删除', '15', 'test');
INSERT INTO `t_menu` VALUES ('55', '/members/add', 'fa fa-etsy', '', '添加', '25', 'test');
INSERT INTO `t_menu` VALUES ('56', '/members/edit', 'fa fa-etsy', '', '更新', '25', 'test');
INSERT INTO `t_menu` VALUES ('57', '/members/delete', 'fa fa-etsy', '', '删除', '25', 'test');
INSERT INTO `t_menu` VALUES ('58', '/devs/add', 'fa fa-etsy', '', '添加', '24', 'test');
INSERT INTO `t_menu` VALUES ('59', '/devs/edit', 'fa fa-etsy', '', '更新', '24', 'test');
INSERT INTO `t_menu` VALUES ('60', '/devs/delete', 'fa fa-etsy', '', '删除', '24', 'test');
INSERT INTO `t_menu` VALUES ('61', '/devAuths/add', 'fa fa-etsy', '', '添加', '28', 'test');
INSERT INTO `t_menu` VALUES ('62', '/devAuths/edit', 'fa fa-etsy', '', '更新', '28', 'test');
INSERT INTO `t_menu` VALUES ('63', '/devAuths/delete', 'fa fa-etsy', '', '删除', '28', 'test');
INSERT INTO `t_menu` VALUES ('67', '/works', 'fa fa-adjust', null, '工单管理', '26', 'sdf');
INSERT INTO `t_menu` VALUES ('68', '/works/appropriation', null, '', '员工充值', '67', 'sdfsdf');
INSERT INTO `t_menu` VALUES ('69', '/appropriationLogs', 'fa fa-etsy', null, '划账记录', '26', 'sdf');
INSERT INTO `t_menu` VALUES ('70', '/countingLogs', 'fa fa-etsy', null, '计数日志', '23', 'test');
INSERT INTO `t_menu` VALUES ('71', '/cashApplys', 'fa fa-etsy', null, '代理商提现申请', '26', 'tet');
INSERT INTO `t_menu` VALUES ('72', '/cashApplys/add', 'fa fa-etsy', '', '申请', '71', 'test');
INSERT INTO `t_menu` VALUES ('75', '/members/credit', 'fa fa-adjust', '', '充值', '25', 'sss');
INSERT INTO `t_menu` VALUES ('76', '/meals', 'fa fa-adjust', '', '套餐管理', '16', 'test');
INSERT INTO `t_menu` VALUES ('77', '/meals/add', 'fa fa-adjust', '', '添加', '76', 'test');
INSERT INTO `t_menu` VALUES ('78', '/meals/edit', 'fa fa-adjust', '', '编辑', '76', 'test');
INSERT INTO `t_menu` VALUES ('79', '/meals/delete', 'fa fa-adjust', '', '删除', '76', 'test');
INSERT INTO `t_menu` VALUES ('80', '/userCashApplys', 'fa fa-etsy', null, '商户提现申请', '26', 'test');
INSERT INTO `t_menu` VALUES ('81', '/userCashApplys/add', 'fa fa-etsy', '', '申请', '80', 'test');
INSERT INTO `t_menu` VALUES ('82', '/users/updateRate', 'fa fa-etsy', '', '批量修改费率', '2', 'test');
INSERT INTO `t_menu` VALUES ('83', '/rechargeLogs', 'fa fa-etsy', '', '充值记录', '16', 'test');
INSERT INTO `t_menu` VALUES ('84', '/counts', 'fa fa-etsy', '', '统计管理', '1', 'test');
INSERT INTO `t_menu` VALUES ('85', '/productGroups', 'fa fa-etsy', null, '商品组管理', '89', 'test');
INSERT INTO `t_menu` VALUES ('86', '/productGroups/add', 'fa fa-etsy', '', '添加', '85', 'test');
INSERT INTO `t_menu` VALUES ('87', '/productGroups/edit', 'fa fa-etsy', '', '编辑', '85', 'test');
INSERT INTO `t_menu` VALUES ('88', '/productGroups/delete', 'fa fa-etsy', '', '删除', '85', 'test');
INSERT INTO `t_menu` VALUES ('89', null, 'fa fa-etsy', null, '商品中心', null, 'test');
INSERT INTO `t_menu` VALUES ('90', '/products', 'fa fa-etsy', '\0', '商品管理', '89', 'test');
INSERT INTO `t_menu` VALUES ('91', '/products/add', 'fa fa-etsy', '', '添加', '90', 'test');
INSERT INTO `t_menu` VALUES ('92', '/products/edit', 'fa fa-etsy', '', '编辑', '90', 'test');
INSERT INTO `t_menu` VALUES ('93', '/products/delete', 'fa fa-etsy', '', '删除', '90', 'test');
INSERT INTO `t_menu` VALUES ('94', '/comsumeLogs', 'fa fa-etsy', null, '消费记录', '89', 'test');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `agentId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_role_agentId` (`agentId`) USING BTREE,
  CONSTRAINT `t_role_ibfk_1` FOREIGN KEY (`agentId`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('10', '老鼠', '18');
INSERT INTO `t_role` VALUES ('11', '一级商户', '19');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `menuId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`,`menuId`),
  KEY `FK_f7xuyb3bvlxjss5yg0uv4ppvx` (`menuId`) USING BTREE,
  CONSTRAINT `t_role_menu_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`),
  CONSTRAINT `t_role_menu_ibfk_3` FOREIGN KEY (`menuId`) REFERENCES `t_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES ('1', '10');
INSERT INTO `t_role_menu` VALUES ('1', '11');
INSERT INTO `t_role_menu` VALUES ('23', '10');
INSERT INTO `t_role_menu` VALUES ('23', '11');
INSERT INTO `t_role_menu` VALUES ('24', '10');
INSERT INTO `t_role_menu` VALUES ('26', '10');
INSERT INTO `t_role_menu` VALUES ('26', '11');
INSERT INTO `t_role_menu` VALUES ('58', '10');
INSERT INTO `t_role_menu` VALUES ('59', '10');
INSERT INTO `t_role_menu` VALUES ('60', '10');
INSERT INTO `t_role_menu` VALUES ('80', '10');
INSERT INTO `t_role_menu` VALUES ('81', '10');
INSERT INTO `t_role_menu` VALUES ('84', '10');
INSERT INTO `t_role_menu` VALUES ('85', '10');
INSERT INTO `t_role_menu` VALUES ('86', '10');
INSERT INTO `t_role_menu` VALUES ('87', '10');
INSERT INTO `t_role_menu` VALUES ('88', '10');
INSERT INTO `t_role_menu` VALUES ('89', '10');
INSERT INTO `t_role_menu` VALUES ('90', '10');
INSERT INTO `t_role_menu` VALUES ('91', '10');
INSERT INTO `t_role_menu` VALUES ('92', '10');
INSERT INTO `t_role_menu` VALUES ('93', '10');
INSERT INTO `t_role_menu` VALUES ('94', '10');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `loginName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` bit(1) DEFAULT b'0',
  `leftMoney` decimal(9,2) NOT NULL DEFAULT '0.00',
  `lockMoney` decimal(9,2) DEFAULT NULL COMMENT '冻结资金',
  `rate` decimal(9,4) DEFAULT NULL COMMENT '商户费率',
  `score` int(11) NOT NULL DEFAULT '0',
  `status` int(255) NOT NULL DEFAULT '0' COMMENT '0:冻结1:激活',
  `agentId` int(11) DEFAULT NULL,
  `agentName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bmngciqbcrw3yqdcfdwh8ysu7` (`loginName`) USING BTREE,
  KEY `FK_t_user_agentId` (`agentId`) USING BTREE,
  CONSTRAINT `t_user_ibfk_1` FOREIGN KEY (`agentId`) REFERENCES `agent` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('55', '赵六', '45', '1164228070@qq.com', '000000', '670b14728ad9902aecba32e22fa4f6bd', '', '55.24', null, '0.0300', '0', '1', '18', '李四');
INSERT INTO `t_user` VALUES ('56', '新水坑', '18', '36703231@qq.com', 'hywlw2019', 'e10adc3949ba59abbe56e057f20f883e', '\0', '11.73', null, '0.0100', '0', '1', '19', '华亦');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `roleId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FK_kjp9c6hki8a1p70x44bwqex2v` (`roleId`) USING BTREE,
  CONSTRAINT `t_user_role_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`),
  CONSTRAINT `t_user_role_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('10', '55');
INSERT INTO `t_user_role` VALUES ('10', '56');

-- ----------------------------
-- Table structure for user_cash_apply
-- ----------------------------
DROP TABLE IF EXISTS `user_cash_apply`;
CREATE TABLE `user_cash_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` decimal(9,2) NOT NULL COMMENT '提现金额',
  `income` decimal(9,2) DEFAULT NULL COMMENT '到账金额',
  `agentMoney` decimal(9,2) DEFAULT NULL COMMENT '代理商到账',
  `adminMoney` decimal(9,2) DEFAULT NULL COMMENT '运营商到账',
  `userId` int(11) NOT NULL COMMENT '商户id',
  `userName` varchar(255) NOT NULL COMMENT '商户名',
  `agentId` int(11) DEFAULT NULL COMMENT '代理商id',
  `agentName` varchar(255) DEFAULT NULL COMMENT '代理商名',
  `typeName` varchar(255) NOT NULL COMMENT '卡类型',
  `accNo` varchar(255) NOT NULL COMMENT '卡号',
  `accUser` varchar(255) NOT NULL COMMENT '收款人姓名',
  `area` varchar(255) DEFAULT NULL COMMENT '卡所属区域（银行）',
  `createDate` datetime NOT NULL,
  `handlerDate` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` tinyint(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `user_cash_apply_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_cash_apply
-- ----------------------------

-- ----------------------------
-- Table structure for user_cash_config
-- ----------------------------
DROP TABLE IF EXISTS `user_cash_config`;
CREATE TABLE `user_cash_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL COMMENT '商户id',
  `agentId` int(11) DEFAULT NULL COMMENT '代理商id',
  `type` varchar(255) NOT NULL COMMENT '卡类型',
  `accNo` varchar(255) NOT NULL COMMENT '卡号',
  `accUser` varchar(255) NOT NULL COMMENT '商户姓名',
  `area` varchar(255) DEFAULT NULL COMMENT '卡所属地区（银行）',
  `createDate` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `user_cash_config_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_cash_config
-- ----------------------------
