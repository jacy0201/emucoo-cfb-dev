/*
Navicat MySQL Data Transfer

Source Server         : 192.168.16.173
Source Server Version : 50718
Source Host           : 192.168.16.173:3306
Source Database       : emucoo-cfb

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-05-08 17:40:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_name` varchar(50) DEFAULT NULL COMMENT '分区名称',
  `area_desc` varchar(255) DEFAULT NULL COMMENT '分区描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `area_name` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='分区信息表';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父ID ，层级关系(一级机构的 parent_id 为 0)',
  `dpt_name` varchar(255) DEFAULT NULL COMMENT '机构名称(公司或部门)',
  `short_name` varchar(255) DEFAULT NULL COMMENT '机构简称',
  `dpt_no` varchar(255) DEFAULT NULL COMMENT '机构编码',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `district` varchar(255) DEFAULT NULL COMMENT '区 ',
  `leader` varchar(255) DEFAULT NULL COMMENT '负责人',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '机构描述',
  `tel` varchar(128) DEFAULT NULL COMMENT '联系电话',
  `order_num` int(5) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `is_use` bit(1) DEFAULT NULL COMMENT '是启用 0：启用 1：不启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='机构信息表';

-- ----------------------------
-- Table structure for sys_district
-- ----------------------------
DROP TABLE IF EXISTS `sys_district`;
CREATE TABLE `sys_district` (
  `id` bigint(200) NOT NULL AUTO_INCREMENT,
  `area_code` varchar(100) NOT NULL COMMENT '区域编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1200) NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(2000) NOT NULL COMMENT '全节点名',
  `area_name` varchar(100) NOT NULL COMMENT '区域名称',
  `area_type` char(1) DEFAULT NULL COMMENT '区域类型',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_user_id` bigint(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_user_id` bigint(64) DEFAULT NULL COMMENT '更新者',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `area_code` (`area_code`) USING BTREE,
  KEY `idx_sys_area_pc` (`parent_code`),
  KEY `idx_sys_area_ts` (`tree_sort`),
  KEY `idx_sys_area_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=3257 DEFAULT CHARSET=utf8 COMMENT='行政区划';

-- ----------------------------
-- Table structure for sys_dpt_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_dpt_area`;
CREATE TABLE `sys_dpt_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `dpt_id` bigint(20) NOT NULL COMMENT '机构表ID',
  `area_id` bigint(20) NOT NULL COMMENT 'sys_area表 主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COMMENT='机构分区关系表';

-- ----------------------------
-- Table structure for sys_dpt_brand
-- ----------------------------
DROP TABLE IF EXISTS `sys_dpt_brand`;
CREATE TABLE `sys_dpt_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `dpt_id` bigint(20) NOT NULL COMMENT '机构表ID',
  `brand_id` bigint(20) NOT NULL COMMENT 'brand_info 表主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8 COMMENT='机构品牌关系表';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `app_name` varchar(255) DEFAULT NULL COMMENT '所属应用',
  `log_type` tinyint(5) DEFAULT NULL COMMENT '日志类型，0为操作日志，1为异常日志',
  `log_source` tinyint(5) DEFAULT NULL COMMENT '日志来源：0 controller层；1service 层',
  `username` varchar(255) DEFAULT NULL COMMENT '访问者/请求者',
  `operation` varchar(255) DEFAULT NULL COMMENT '方法描述',
  `method_name` varchar(255) DEFAULT NULL COMMENT '请求方法名称(全路径)',
  `request_method` varchar(255) DEFAULT NULL COMMENT '请求方式(GET,POST,DELETE,PUT)',
  `request_params` varchar(2048) DEFAULT NULL COMMENT '请求参数',
  `request_ip` varchar(255) DEFAULT NULL COMMENT '访问者IP',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `exception_code` varchar(255) DEFAULT NULL COMMENT '异常码',
  `exception_detail` varchar(1024) DEFAULT NULL COMMENT '异常描述',
  `time_consuming` bigint(20) DEFAULT NULL COMMENT '请求耗时',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '客户端信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='log日志表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` tinyint(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='菜单资源信息表';

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `post_code` varchar(64) DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(100) NOT NULL COMMENT '岗位名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `type` tinyint(1) DEFAULT '1' COMMENT '1：部门岗位，2：店铺岗位  (该字段现阶段不使用)',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `post_name` (`post_name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='岗位表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '资源表ID, sys_menu 表主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(100) DEFAULT NULL,
  `remark` varchar(600) DEFAULT NULL,
  `dpt_id` bigint(11) DEFAULT NULL COMMENT '部门id ,关联sys_dept表主键',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐值',
  `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `sex` tinyint(255) DEFAULT NULL COMMENT '性别： 1-男  ； 2-女 ；3-其他',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `is_admin` bit(1) DEFAULT NULL COMMENT '是否是管理员1：是管理员 其它不是管理员',
  `head_img_url` varchar(255) DEFAULT NULL,
  `push_token` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) DEFAULT NULL,
  `status` int(8) DEFAULT '0' COMMENT '用户状态：0-启用；1-停用；2-锁定；',
  `is_shop_manager` bit(1) DEFAULT NULL COMMENT '是否是店长：0-否；1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for sys_user_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_area`;
CREATE TABLE `sys_user_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `user_id` bigint(20) NOT NULL,
  `area_id` bigint(20) NOT NULL COMMENT '分区id ,sys_area 表主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_areaId` (`user_id`,`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='用户分区关系表';

-- ----------------------------
-- Table structure for sys_user_brand
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_brand`;
CREATE TABLE `sys_user_brand` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL,
  `brand_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId_brandId` (`user_id`,`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户品牌关系表';

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id,sys_user表主键',
  `post_id` bigint(20) NOT NULL COMMENT '岗位id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`),
  KEY `post_user` (`user_id`,`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='用户机构关系表';

-- ----------------------------
-- Table structure for sys_user_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_relation`;
CREATE TABLE `sys_user_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `post_id` bigint(20) DEFAULT NULL COMMENT 'user_id 的岗位id',
  `child_user_id` bigint(20) DEFAULT NULL COMMENT 'child_user_id下级id',
  `child_post_id` bigint(20) DEFAULT NULL COMMENT '下级用户岗位id',
  `dpt_id` bigint(11) DEFAULT NULL COMMENT 'user_id 的 dpt_id ',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_relation` (`user_id`,`post_id`,`child_user_id`,`child_post_id`,`dpt_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关系表';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Table structure for sys_user_shop
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_shop`;
CREATE TABLE `sys_user_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `user_id` bigint(20) NOT NULL,
  `shop_id` bigint(20) NOT NULL COMMENT '店铺id ,shop_info 表主键',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_shop` (`user_id`,`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户店铺关系表';

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `id` bigint(20) NOT NULL COMMENT 'id 关联 sys_user 表 主键 id',
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户Token';

-- ----------------------------
-- Table structure for t_brand_info
-- ----------------------------
DROP TABLE IF EXISTS `t_brand_info`;
CREATE TABLE `t_brand_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `brand_code` varchar(255) DEFAULT NULL COMMENT '品牌编码',
  `brand_name` varchar(255) DEFAULT NULL COMMENT '品牌名称',
  `brand_desc` varchar(500) DEFAULT NULL COMMENT '品牌描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `brand_name` (`brand_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='品牌信息表';

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `union_id` bigint(20) DEFAULT NULL COMMENT '关联的评论主体ID',
  `union_type` int(11) DEFAULT NULL COMMENT '*评论类型,默认按照评论的对象分类',
  `user_id` bigint(20) DEFAULT NULL COMMENT '评论用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '评论用户名',
  `content` text COMMENT '评论的文本内容',
  `is_show` bit(1) DEFAULT NULL COMMENT '是否显示 0 ：显示  1：不显示',
  `is_del` bit(1) DEFAULT NULL,
  `img_ids` varchar(255) DEFAULT NULL COMMENT '图片id，多个图片间用【,】分割',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` bigint(5) DEFAULT NULL,
  `modify_user_id` bigint(11) DEFAULT NULL,
  `org_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键PK',
  `source` tinyint(1) DEFAULT NULL COMMENT '图片来源 1：后台上传，2：前台上传',
  `orginal_name` varchar(255) DEFAULT NULL COMMENT '图片原名称',
  `saved_name` varchar(255) DEFAULT NULL COMMENT '图片保存名',
  `img_url` text COMMENT '图片路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_user_id` bigint(20) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `location` varchar(255) DEFAULT NULL COMMENT '位置信息',
  `file_date` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='图片附录表';

-- ----------------------------
-- Table structure for t_form_add_item
-- ----------------------------
DROP TABLE IF EXISTS `t_form_add_item`;
CREATE TABLE `t_form_add_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `form_main_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='表单补充项';

-- ----------------------------
-- Table structure for t_form_add_item_value
-- ----------------------------
DROP TABLE IF EXISTS `t_form_add_item_value`;
CREATE TABLE `t_form_add_item_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL COMMENT '补充项纪录值',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `form_main_id` bigint(20) DEFAULT NULL,
  `form_addition_item_id` bigint(20) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL COMMENT '报告id',
  `form_result_id` bigint(20) DEFAULT NULL,
  `form_addition_item_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='表单补充项纪录表';

-- ----------------------------
-- Table structure for t_form_check_result
-- ----------------------------
DROP TABLE IF EXISTS `t_form_check_result`;
CREATE TABLE `t_form_check_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_main_id` bigint(20) DEFAULT NULL COMMENT '关联表单主表',
  `form_main_name` varchar(100) DEFAULT NULL COMMENT '关联的表单的名称',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '商铺的id',
  `shop_name` varchar(100) DEFAULT NULL COMMENT '商铺名称',
  `front_plan_id` bigint(20) DEFAULT NULL COMMENT '巡店安排的id',
  `score_rate` float(11,0) DEFAULT NULL COMMENT 'l类型得分率',
  `score` int(11) DEFAULT NULL COMMENT '得分',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '提交结果的用户id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `summary` text,
  `org_id` bigint(20) DEFAULT NULL,
  `actual_total` varchar(255) DEFAULT NULL,
  `impt_item_deny_num` int(11) DEFAULT NULL COMMENT '失分重点项的数量',
  `na_num` int(11) DEFAULT NULL COMMENT 'N/A项数量',
  `summary_img` varchar(255) DEFAULT NULL COMMENT '总结图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 COMMENT='打表结果表';

-- ----------------------------
-- Table structure for t_form_impt_rules
-- ----------------------------
DROP TABLE IF EXISTS `t_form_impt_rules`;
CREATE TABLE `t_form_impt_rules` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count_num` int(10) DEFAULT NULL COMMENT '统计出的重点项数量',
  `discount_num` int(10) DEFAULT NULL COMMENT '分数折扣率',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `form_main_id` bigint(20) DEFAULT NULL COMMENT '表单主id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=215 DEFAULT CHARSET=utf8 COMMENT='表单重点项规则表';

-- ----------------------------
-- Table structure for t_form_main
-- ----------------------------
DROP TABLE IF EXISTS `t_form_main`;
CREATE TABLE `t_form_main` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '表单名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `total_score` int(5) DEFAULT NULL COMMENT '预设总分',
  `can_add_oppt` bit(1) DEFAULT NULL COMMENT '表单是否允许添加机会点',
  `cc_dpt_ids` varchar(255) DEFAULT NULL COMMENT '抄送范围',
  `create_user_id` bigint(5) DEFAULT NULL,
  `modify_user_id` bigint(11) DEFAULT NULL,
  `org_id` bigint(11) DEFAULT NULL,
  `is_del` bit(1) DEFAULT NULL,
  `is_use` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='表单主体表';

-- ----------------------------
-- Table structure for t_form_oppt
-- ----------------------------
DROP TABLE IF EXISTS `t_form_oppt`;
CREATE TABLE `t_form_oppt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `problem_id` bigint(20) DEFAULT NULL COMMENT '题项表id',
  `oppt_id` bigint(20) DEFAULT NULL COMMENT '机会点id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `problem_type` tinyint(1) DEFAULT NULL COMMENT '题项方案类型（1：不带抽样，2：带抽样）',
  `sub_problem_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8 COMMENT='表单机会点关联表';

-- ----------------------------
-- Table structure for t_form_oppt_value
-- ----------------------------
DROP TABLE IF EXISTS `t_form_oppt_value`;
CREATE TABLE `t_form_oppt_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `is_pick` bit(1) DEFAULT NULL COMMENT '该机会点是否已经点选',
  `sub_problem_unit_score` int(11) DEFAULT NULL COMMENT '子题项单元分数',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `oppt_id` bigint(20) DEFAULT NULL,
  `problem_id` bigint(20) DEFAULT NULL,
  `sub_problem_id` bigint(20) DEFAULT NULL,
  `oppt_name` varchar(100) DEFAULT NULL,
  `problem_value_id` bigint(20) DEFAULT NULL,
  `sub_problem_value_id` bigint(20) DEFAULT NULL,
  `problem_type` tinyint(1) DEFAULT NULL COMMENT '题项方案类型（1：不带抽样，2：带抽样）',
  `sub_header_id` bigint(20) DEFAULT NULL,
  `form_result_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2539 DEFAULT CHARSET=utf8 COMMENT='表单机会点纪录表';

-- ----------------------------
-- Table structure for t_form_pbm
-- ----------------------------
DROP TABLE IF EXISTS `t_form_pbm`;
CREATE TABLE `t_form_pbm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '题项名',
  `score` int(5) DEFAULT NULL COMMENT '分值',
  `create_user_id` bigint(5) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user_id` bigint(5) DEFAULT NULL,
  `sub_problem_num` int(5) DEFAULT NULL COMMENT '子题项抽样目标数量',
  `sub_problem_unit` varchar(10) DEFAULT NULL COMMENT '子题抽样目标单位',
  `problem_schema_type` tinyint(1) DEFAULT NULL COMMENT '题项方案类型（1：不带抽样，2：带抽样）',
  `description_hit` text COMMENT '描述提示',
  `is_important` bit(1) DEFAULT NULL COMMENT '是否重要（0：不重要，1：重要）',
  `form_type_id` bigint(20) DEFAULT NULL COMMENT '表单分类id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=utf8 COMMENT='表单题项表';

-- ----------------------------
-- Table structure for t_form_pbm_val
-- ----------------------------
DROP TABLE IF EXISTS `t_form_pbm_val`;
CREATE TABLE `t_form_pbm_val` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_value_id` bigint(20) DEFAULT NULL COMMENT '关联表单结果表id',
  `form_problem_id` bigint(20) DEFAULT NULL COMMENT '表单题项表id',
  `score` int(10) DEFAULT NULL COMMENT '实际分数',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `problem_description` text COMMENT '题项描述',
  `is_na` bit(1) DEFAULT NULL COMMENT '是否是NA',
  `is_score` bit(1) DEFAULT NULL COMMENT '该题项是否经过实际打分动作',
  `org_id` bigint(20) DEFAULT NULL,
  `problem_schema_type` tinyint(1) DEFAULT NULL COMMENT '题项方案类型（1：不带抽样，2：带抽样）',
  `form_result_id` bigint(20) DEFAULT NULL,
  `problem_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=597 DEFAULT CHARSET=utf8 COMMENT='表单题项值表';

-- ----------------------------
-- Table structure for t_form_score_item
-- ----------------------------
DROP TABLE IF EXISTS `t_form_score_item`;
CREATE TABLE `t_form_score_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `score_begin` int(11) DEFAULT NULL COMMENT '分数起始范围',
  `score_end` int(11) DEFAULT NULL COMMENT '分数结束范围',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `form_main_id` bigint(20) DEFAULT NULL COMMENT '表单主id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8 COMMENT='表单计分项表';

-- ----------------------------
-- Table structure for t_form_sub_pbm
-- ----------------------------
DROP TABLE IF EXISTS `t_form_sub_pbm`;
CREATE TABLE `t_form_sub_pbm` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sub_problem_name` varchar(255) DEFAULT NULL COMMENT '子题项名',
  `sub_problem_score` int(5) DEFAULT NULL COMMENT '子题项分数',
  `form_problem_id` bigint(20) DEFAULT NULL COMMENT '题项id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` bigint(5) DEFAULT NULL,
  `modify_user_id` bigint(11) DEFAULT NULL,
  `org_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8 COMMENT='表单题项的子题表';

-- ----------------------------
-- Table structure for t_form_sub_pbm_header
-- ----------------------------
DROP TABLE IF EXISTS `t_form_sub_pbm_header`;
CREATE TABLE `t_form_sub_pbm_header` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `form_problem_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_form_sub_pbm_val
-- ----------------------------
DROP TABLE IF EXISTS `t_form_sub_pbm_val`;
CREATE TABLE `t_form_sub_pbm_val` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `sub_problem_id` bigint(10) DEFAULT NULL,
  `sub_problem_score` int(5) DEFAULT NULL COMMENT '子题项实际得分',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `sub_problem_name` varchar(255) DEFAULT NULL,
  `problem_value_id` bigint(20) DEFAULT NULL,
  `form_result_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=495 DEFAULT CHARSET=utf8 COMMENT='表单子题项值 表';

-- ----------------------------
-- Table structure for t_form_type
-- ----------------------------
DROP TABLE IF EXISTS `t_form_type`;
CREATE TABLE `t_form_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) DEFAULT NULL COMMENT '类型名',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `form_main_id` bigint(20) DEFAULT NULL,
  `score` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8 COMMENT='表单题项类别表';

-- ----------------------------
-- Table structure for t_form_value
-- ----------------------------
DROP TABLE IF EXISTS `t_form_value`;
CREATE TABLE `t_form_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_main_id` bigint(20) DEFAULT NULL COMMENT '关联表单主表',
  `from_type_id` bigint(20) DEFAULT NULL COMMENT '表单分类id',
  `is_done` bit(1) DEFAULT NULL COMMENT '是否完成',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `front_plan_id` bigint(1) DEFAULT NULL,
  `form_main_name` varchar(100) DEFAULT NULL,
  `form_type_name` varchar(100) DEFAULT NULL,
  `score_rate` float(11,4) DEFAULT NULL COMMENT 'l类型得分率',
  `score` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `form_result_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=utf8 COMMENT='打表结果表';

-- ----------------------------
-- Table structure for t_front_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_front_plan`;
CREATE TABLE `t_front_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loop_plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `shop_id` bigint(20) DEFAULT NULL COMMENT '针对的店铺ID',
  `plan_date` date DEFAULT NULL COMMENT '巡店日期',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态0：未计划 1：未安排，2：未巡店，3：巡店中，4：已完成 5：已过期',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `plan_precise_time` datetime DEFAULT NULL COMMENT '预计到店时间',
  `remind_type` tinyint(1) DEFAULT NULL COMMENT '提醒时间类型(0：无，1：日程开始时，2：提前15分钟，3：提前30分钟，4：提前1小时，5：提前2小时，6：提前一天)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `actual_execute_time` datetime DEFAULT NULL COMMENT '实际巡店时间',
  `actual_execute_address` varchar(255) DEFAULT NULL COMMENT '实际巡店位置',
  `actual_remind_time` datetime DEFAULT NULL COMMENT '实际提醒时间',
  `arrange_year` varchar(10) DEFAULT NULL COMMENT '当前安排所在年份',
  `arrange_month` varchar(10) DEFAULT NULL COMMENT '当前安排所在年份',
  `arranger_id` bigint(20) DEFAULT NULL COMMENT '巡店安排人id',
  `arrangee_id` bigint(20) DEFAULT NULL COMMENT '被安排人id',
  `longitude` varchar(30) DEFAULT NULL COMMENT '巡店经度',
  `latitude` varchar(30) DEFAULT NULL COMMENT '巡店维度',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) NOT NULL,
  `sub_plan_id` bigint(20) DEFAULT NULL COMMENT '子计划id',
  `create_user_id` bigint(20) DEFAULT NULL,
  `modify_user_id` bigint(20) DEFAULT NULL,
  `notice_user_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='巡店安排表';

-- ----------------------------
-- Table structure for t_front_plan_form
-- ----------------------------
DROP TABLE IF EXISTS `t_front_plan_form`;
CREATE TABLE `t_front_plan_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `front_plan_id` bigint(20) DEFAULT NULL COMMENT '巡店安排id',
  `form_main_id` bigint(20) DEFAULT NULL COMMENT '表单表id',
  `is_del` bit(1) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  `report_status` tinyint(1) DEFAULT '2' COMMENT '已完成：1，未完成：2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_label
-- ----------------------------
DROP TABLE IF EXISTS `t_label`;
CREATE TABLE `t_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `label_group_id` int(11) DEFAULT NULL COMMENT '标签组id',
  `name` varchar(100) DEFAULT NULL COMMENT '标签名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '标签备注',
  `sort` int(11) DEFAULT NULL COMMENT '标签排序',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否删除(0：正常，1：删除）',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Table structure for t_label_group
-- ----------------------------
DROP TABLE IF EXISTS `t_label_group`;
CREATE TABLE `t_label_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '标签组名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签组表';

-- ----------------------------
-- Table structure for t_loop_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_loop_plan`;
CREATE TABLE `t_loop_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '任务计划名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述说明',
  `dpt_id` bigint(20) DEFAULT NULL COMMENT '所选择的部门ID',
  `is_use` bit(1) DEFAULT NULL COMMENT '状态 0：停用 1：启用',
  `plan_start_date` varchar(10) DEFAULT NULL COMMENT '计划开始月份\n            1：月循环\n            2：季度循环\n            3：年循环',
  `plan_end_date` varchar(10) DEFAULT NULL COMMENT '计划结束月份',
  `plan_cycle` int(11) DEFAULT NULL COMMENT '巡店周期(月)',
  `plan_cycle_count` int(11) DEFAULT '0' COMMENT '计划当前已循环周期次数',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='巡店计划表';

-- ----------------------------
-- Table structure for t_loop_sub_plan
-- ----------------------------
DROP TABLE IF EXISTS `t_loop_sub_plan`;
CREATE TABLE `t_loop_sub_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cycle_begin` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计划周期开始年月份',
  `cycle_end` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计划周期结束年月份',
  `plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `dpt_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `is_del` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_loop_work
-- ----------------------------
DROP TABLE IF EXISTS `t_loop_work`;
CREATE TABLE `t_loop_work` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `work_id` varchar(50) DEFAULT NULL COMMENT '事务ID',
  `sub_work_id` varchar(50) DEFAULT NULL COMMENT '子事务ID',
  `type` int(11) DEFAULT NULL COMMENT '1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID（可为空）',
  `create_user_name` varchar(255) DEFAULT NULL COMMENT '创建人的用户名',
  `excute_user_id` bigint(20) DEFAULT NULL COMMENT '执行人用户ID',
  `excute_user_name` varchar(255) DEFAULT NULL COMMENT '执行人用户名',
  `audit_user_id` bigint(20) DEFAULT NULL COMMENT '审核人用户ID',
  `audit_user_name` varchar(255) DEFAULT NULL COMMENT '审核人名称',
  `send_user_ids` varchar(100) DEFAULT NULL COMMENT '抄送人用户ID,多个抄送人用【，】分割',
  `work_status` int(11) DEFAULT NULL COMMENT '1：未提交 2：已提交 3：已过期',
  `work_result` int(11) DEFAULT NULL COMMENT '1：合格 2：不合格\n            ',
  `execute_begin_date` date DEFAULT NULL COMMENT '执行开始日期',
  `execute_end_date` date DEFAULT NULL COMMENT '执行结束日期',
  `execute_remind_time` datetime DEFAULT NULL COMMENT '执行提醒时间',
  `execute_deadline` datetime DEFAULT NULL COMMENT '执行截止时间',
  `audit_deadline` datetime DEFAULT NULL COMMENT '审核截止时间',
  `task_id` bigint(20) DEFAULT NULL COMMENT '任务表id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `score` varchar(20) DEFAULT NULL COMMENT '实际得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='循环计划表产生的事务，对应??台的常规任务,前台创建的指派任务，和改善任务';

-- ----------------------------
-- Table structure for t_operate_data_for_work
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_data_for_work`;
CREATE TABLE `t_operate_data_for_work` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_item_id` bigint(20) DEFAULT NULL COMMENT '操作项ID',
  `work_txt` varchar(255) DEFAULT NULL COMMENT '工作文档',
  `num_option_name` varchar(255) DEFAULT NULL COMMENT '数字项名称',
  `num_option_value` varchar(255) DEFAULT NULL COMMENT '数字项目值',
  `num_option_type` int(11) DEFAULT NULL COMMENT '1数值 2百分比 3货币',
  `loop_work_id` bigint(20) DEFAULT NULL COMMENT '任务事务id',
  `img_option_name` varchar(255) DEFAULT NULL COMMENT '图片操作项名称',
  `img_ids` varchar(255) DEFAULT NULL COMMENT '图片id，多个路径则对应多条记录【，】分割',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `audit_user_id` bigint(20) DEFAULT NULL COMMENT '审核用户ID',
  `audit_result` tinyint(1) DEFAULT NULL COMMENT '1合格 2不合格',
  `audit_content` text COMMENT '审核内容',
  `audit_img_ids` varchar(255) DEFAULT NULL COMMENT '审核图片id，多个图片间用【,】分割',
  `score` varchar(20) DEFAULT NULL COMMENT '实际分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='任务操作项纪录表';

-- ----------------------------
-- Table structure for t_operate_option
-- ----------------------------
DROP TABLE IF EXISTS `t_operate_option`;
CREATE TABLE `t_operate_option` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '操作项名称',
  `feedback_text_name` varchar(50) DEFAULT NULL COMMENT '反馈文本名称',
  `feedback_text_description` varchar(255) DEFAULT NULL COMMENT '反馈文本描述',
  `feedback_num_name` varchar(50) DEFAULT NULL COMMENT '反馈数字名称',
  `feedback_num_type` tinyint(1) DEFAULT NULL COMMENT '反馈数字类型（1数值 2百分比 3货币）',
  `feedback_img_name` varchar(50) DEFAULT NULL COMMENT '反馈图片名称',
  `feedback_img_max_count` int(11) DEFAULT NULL COMMENT '反馈图片最大数量',
  `feedback_img_from_album` bit(1) DEFAULT NULL COMMENT '是否允许从相册上传（0：不允许 1允许）',
  `feedback_img_example_id` bigint(20) DEFAULT NULL COMMENT '反馈图片示例id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `preinstall_score` varchar(10) DEFAULT NULL COMMENT '预设的满分',
  `preinstall_weight` varchar(10) DEFAULT NULL COMMENT '预设的权重',
  `task_id` bigint(20) DEFAULT NULL,
  `feedback_need_text` bit(1) DEFAULT NULL,
  `feedback_img_type` tinyint(1) DEFAULT NULL,
  `feedback_need_num` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='操作项配置表';

-- ----------------------------
-- Table structure for t_opportunity
-- ----------------------------
DROP TABLE IF EXISTS `t_opportunity`;
CREATE TABLE `t_opportunity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(10) DEFAULT NULL COMMENT '类型（0：正常，1：其他）',
  `name` varchar(255) DEFAULT NULL COMMENT '机会点名称',
  `create_type` int(10) DEFAULT '1' COMMENT '类型（1:后台创建，2:前台创建, 3:由表单自动创建）',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否删除(0：正常，1：删除）',
  `is_use` bit(1) DEFAULT b'0' COMMENT '是否启用(0：停用，1 启用）',
  `description` varchar(255) DEFAULT NULL COMMENT '机会点描述',
  `front_can_create` bit(1) DEFAULT NULL COMMENT '前端能否创建(0：不能，1：能)',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8 COMMENT='机会点表';

-- ----------------------------
-- Table structure for t_plan_form_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_plan_form_relation`;
CREATE TABLE `t_plan_form_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_id` bigint(20) DEFAULT NULL COMMENT '计划id',
  `form_main_id` bigint(20) DEFAULT NULL COMMENT '表单id',
  `form_use_count` int(20) DEFAULT NULL COMMENT '表单引用次数',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `is_use` bit(1) DEFAULT NULL,
  `is_del` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='计划和表单关联表';

-- ----------------------------
-- Table structure for t_remind
-- ----------------------------
DROP TABLE IF EXISTS `t_remind`;
CREATE TABLE `t_remind` (
  `remind_type` int(2) DEFAULT NULL,
  `remind_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id` int(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for t_report
-- ----------------------------
DROP TABLE IF EXISTS `t_report`;
CREATE TABLE `t_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `shop_name` varchar(50) DEFAULT NULL COMMENT '店铺名',
  `shopkeeper_name` varchar(50) DEFAULT NULL COMMENT '店长名',
  `reporter_id` bigint(11) DEFAULT NULL,
  `reporter_name` varchar(50) DEFAULT NULL COMMENT '打表人姓名',
  `reporter_dpt_name` varchar(50) DEFAULT NULL COMMENT '打表人部门名',
  `check_form_time` date DEFAULT NULL COMMENT '打表日期',
  `reporter_position` varchar(50) DEFAULT NULL COMMENT '打表人职位名',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL COMMENT '店铺id',
  `shopkeeper_id` bigint(20) DEFAULT NULL COMMENT '店长id',
  `reporter_dpt_id` bigint(20) DEFAULT NULL COMMENT '打表人部门id',
  `reporter_position_id` varchar(20) DEFAULT NULL COMMENT '打表人职位id',
  `create_user_id` bigint(5) DEFAULT NULL,
  `modify_user_id` bigint(11) DEFAULT NULL,
  `org_id` bigint(11) DEFAULT NULL,
  `form_result_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='报告表';

-- ----------------------------
-- Table structure for t_report_oppt
-- ----------------------------
DROP TABLE IF EXISTS `t_report_oppt`;
CREATE TABLE `t_report_oppt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` bigint(20) DEFAULT NULL,
  `oppt_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `oppt_name` varchar(100) DEFAULT NULL COMMENT '机会点名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='表单和机会点中间表';

-- ----------------------------
-- Table structure for t_report_user
-- ----------------------------
DROP TABLE IF EXISTS `t_report_user`;
CREATE TABLE `t_report_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` bigint(20) DEFAULT NULL COMMENT '报告id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `is_read` bit(1) DEFAULT NULL COMMENT '是否已读（0：未读，1：已读）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='表单用户关联表';

-- ----------------------------
-- Table structure for t_shop_info
-- ----------------------------
DROP TABLE IF EXISTS `t_shop_info`;
CREATE TABLE `t_shop_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键PK',
  `shop_name` varchar(100) DEFAULT NULL COMMENT '店铺名称',
  `short_name` varchar(50) DEFAULT NULL COMMENT '店铺简称',
  `type` int(255) DEFAULT NULL COMMENT '店铺类型：1-直营;2-加盟',
  `shop_code` varchar(50) DEFAULT NULL COMMENT '店铺编号',
  `shop_desc` varchar(255) DEFAULT NULL COMMENT '店铺描述',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `district` varchar(20) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '店铺地址',
  `shop_manager_id` bigint(255) DEFAULT NULL COMMENT '店长',
  `area_id` bigint(20) DEFAULT NULL COMMENT '分区id,关联sys_area 表id',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌id, 关联brand_info 表主键',
  `tel` varchar(50) DEFAULT NULL COMMENT '店铺电话',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `is_use` bit(1) DEFAULT NULL COMMENT '是否启用：1-启用；0-停用',
  `is_del` bit(1) DEFAULT NULL COMMENT '是否逻辑删除0：正常1：逻辑删除',
  `org_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shop_name` (`shop_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='店铺信息表';

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `illustration_img_ids` varchar(255) DEFAULT NULL COMMENT '任务说明图片id',
  `name` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `type` int(11) DEFAULT NULL COMMENT '1常规任务 2指派任务 3改善任务 4巡店安排 5工作备忘',
  `description` varchar(255) DEFAULT NULL COMMENT '描述说明',
  `execute_deadline` varchar(20) DEFAULT NULL COMMENT '执行截止时间',
  `execute_remind_time` varchar(20) DEFAULT NULL COMMENT '执行提醒时间',
  `audit_user_id` bigint(20) DEFAULT NULL COMMENT '审核人ids',
  `audit_deadline` varchar(20) DEFAULT NULL COMMENT '审核截止时间',
  `executor_dpt_id` bigint(20) DEFAULT NULL COMMENT '执行人部门id',
  `execute_user_ids` varchar(255) DEFAULT NULL COMMENT '执行人ids',
  `executor_position_ids` varchar(255) DEFAULT NULL COMMENT '执行人岗位ids',
  `executor_shop_ids` varchar(255) DEFAULT NULL COMMENT '执行人店铺ids',
  `cc_user_ids` varchar(255) DEFAULT NULL COMMENT '抄送人ids',
  `cc_position_ids` varchar(255) DEFAULT NULL COMMENT '抄送人岗位ids',
  `score_type` tinyint(4) DEFAULT NULL COMMENT '评分方式（1：任务评分，2：操作项评分）',
  `is_use` bit(1) DEFAULT NULL COMMENT '是否启用（0：停用，1：启用）',
  `duration_time_type` tinyint(1) DEFAULT NULL COMMENT '任务持续时间类型（0：不重复，1：每天，2：每周，3：每月，4：每年）',
  `task_start_date` date DEFAULT NULL COMMENT '重复任务开始日期',
  `task_end_date` date DEFAULT NULL COMMENT '重复任务结束日期',
  `loop_cycle_type` tinyint(1) DEFAULT NULL COMMENT '循环周期类型（1：隔X天一次，2：周循环，3：月循环）',
  `loop_cycle_value` varchar(255) DEFAULT NULL COMMENT '循环周期的值，和loop_cycle_type配合使用\n            1.如果是周循环，存储的是1,2,3,6,7，从1-7代表星期一到星期日\n            2.如果是月循环，存储的是详细日期的拼接串，以逗号相隔',
  `work_id` varchar(50) DEFAULT NULL COMMENT '主事务id',
  `audit_type` tinyint(1) DEFAULT NULL COMMENT '1：操作项审核 2：整体审核',
  `is_del` bit(1) DEFAULT b'0' COMMENT '是否删除',
  `oppt_id` bigint(20) DEFAULT NULL COMMENT '改善任务关联的机会点id',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` bigint(5) DEFAULT NULL COMMENT '数据创建人ID',
  `modify_user_id` bigint(11) DEFAULT NULL COMMENT '数据修改人ID',
  `audit_dpt_id` bigint(20) DEFAULT NULL COMMENT '审核部门id',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `preinstall_score` varchar(10) DEFAULT NULL COMMENT '预设的满分',
  `preinstall_weight` varchar(10) DEFAULT NULL COMMENT '预设的权重',
  `org_id` bigint(20) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL COMMENT '报告id',
  `front_plan_id` bigint(20) DEFAULT NULL COMMENT '巡店安排',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Table structure for t_task_label
-- ----------------------------
DROP TABLE IF EXISTS `t_task_label`;
CREATE TABLE `t_task_label` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) DEFAULT NULL,
  `label_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务标签表';
