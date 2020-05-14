-- ----------------------------
-- auth 客户端详情信息表
-- ----------------------------

DROP TABLE IF EXISTS `summer_db`.`oauth_client_details`;
CREATE TABLE `summer_db`.`oauth_client_details` (
  `client_id` 				VARCHAR(255) NOT NULL COMMENT     '客户端标 识',
  `resource_ids` 			VARCHAR(255) DEFAULT NULL COMMENT '接入资源列表',
  `client_secret` 			VARCHAR(255) DEFAULT NULL COMMENT '客户端秘钥',
  `scope` 					VARCHAR(255) DEFAULT NULL COMMENT '指定client的权限范围',
  `authorized_grant_types`  VARCHAR(255) DEFAULT NULL COMMENT '授权类型 client_credentials,password,authorization_code,implicit,refresh_token',
  `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '重定向地址',
  `authorities` 			VARCHAR(255) DEFAULT NULL COMMENT '指定用户的权限范围，如果授权的过程需要用户登陆，该字段不生效，implicit和client_credentials需要',
  `access_token_validity`   INT(11) DEFAULT NULL COMMENT 	  '设置access_token的有效时间(秒),默认(606012,12小时)',
  `refresh_token_validity`  INT(11) DEFAULT NULL COMMENT 	  '设置refresh_token有效期(秒)，默认(606024*30, 30填)',
  `additional_information`  LONGTEXT COMMENT                  'JSON 格式的token附加信息',
  `create_time` 			TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `archived` 				TINYINT(4) DEFAULT NULL COMMENT   '用于标识客户端是否已存档(即实现逻辑删除),默认值为’0’(即未存档). ',
  `trusted` 				TINYINT(4) DEFAULT NULL COMMENT   '客户端是否为受信任的,默认为0. 该字段只适用于grant_type="authorization_code"的情况',
  `autoapprove` 			VARCHAR(255) DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’. 该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为’true’或支持的scope值,则会跳过用户Approve的页面, 直接授权',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='客户端详情信息表';


-- ----------------------------
-- auth 客户端详情信息表
-- ----------------------------
DROP TABLE IF EXISTS `summer_db`.`oauth_code`;
CREATE TABLE `summer_db`.`oauth_code` (
  `create_time` 	TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `code`			VARCHAR(255) DEFAULT NULL COMMENT  					 '授权码',
  `authentication`  BLOB COMMENT 										 '存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.',
  KEY `code_index` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='客户端详情信息表';


-- ----------------------------
-- 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `summer_db`.`sys_userinfo`;
CREATE TABLE `summer_db`.`sys_userinfo` (
  `id` int(11) 		NOT NULL AUTO_INCREMENT COMMENT 	'主键',
  `loginname` 		VARCHAR(255) DEFAULT NULL COMMENT 	'登录账号',
  `password` 		VARCHAR(255) DEFAULT NULL COMMENT 	'登录密码',
  `headurl` 		VARCHAR(500) DEFAULT NULL COMMENT 	'头像地址',
  `username` 		VARCHAR(255) DEFAULT NULL COMMENT 	'人员名称',
  `telphone` 		VARCHAR(255) DEFAULT NULL COMMENT 	'固定电话',
  `mobile` 			VARCHAR(255) DEFAULT NULL COMMENT 	'移动电话',
  `enable` 			TINYINT(1) 	 DEFAULT NULL COMMENT 	'状态码1启用0停用',
  `role` 			VARCHAR(255) DEFAULT NULL COMMENT 	'人员角色ID 逗号拼接',
  `addtime` 		DATETIME 	 DEFAULT NULL COMMENT 	'添加时间',
  `updatetime` 		DATETIME 	 DEFAULT NULL COMMENT 	'修改时间',
  `lastlogintime` 	DATETIME 	 DEFAULT NULL COMMENT 	'最后登录时间',
  `areaid` 			INT(11) 	 DEFAULT NULL COMMENT 	'区域id',
  `deptcode` 		VARCHAR(32)  DEFAULT NULL COMMENT 	'部门id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='用户信息表';
CREATE INDEX LOGIN_NAME ON `summer_db`.`sys_userinfo`(loginname);


-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS `summer_db`.`sys_role`;
CREATE TABLE `summer_db`.`sys_role` (
  `id` 			INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rolename` 	VARCHAR(255) DEFAULT NULL COMMENT 		'角色名称',
  `addtime` 	DATETIME DEFAULT NULL COMMENT 			'添加时间',
  `updatetime` 	DATETIME DEFAULT NULL COMMENT 			'修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='角色表';


-- ----------------------------
-- 角色权限表
-- ----------------------------
DROP TABLE IF EXISTS `summer_db`.`sys_role_permission`;
CREATE TABLE `summer_db`.`sys_role_permission` (
  `id` 			INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id`		VARCHAR(255) DEFAULT NULL COMMENT 		'角色权限列表逗号分隔',
  `permissions` LONGTEXT DEFAULT NULL COMMENT 		'权限',
  `addtime` 	DATETIME DEFAULT NULL COMMENT 			'添加时间',
  `updatetime` 	DATETIME DEFAULT NULL COMMENT 			'修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='角色权限表';
CREATE INDEX RILE_ID ON `summer_db`.`sys_role_permission`(role_id);
























