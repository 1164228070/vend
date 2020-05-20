/*
Navicat MySQL Data Transfer

Source Server         : 47.105.79.63
Source Server Version : 50728
Source Host           : 47.105.79.63:3306
Source Database       : myvend

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2019-12-26 15:33:52
*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

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
INSERT INTO `t_menu` VALUES ('82', '/users/updateRate', 'fa fa-etsy', '', '批量修改余额费率', '2', 'test');
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
INSERT INTO `t_menu` VALUES ('95', '/users/updateScoreRate', 'fa fa-etsy', '', '批量修改积分费率', '2', 'test');
