CREATE TABLE sps_sys_config(
  sysKey VARCHAR(200) NOT NULL COMMENT '参数名',
  sysValue TEXT DEFAULT NULL COMMENT '参数值',
  modifyTime BIGINT(20) DEFAULT 0 COMMENT '修改日期',
  accountId BIGINT(20) DEFAULT 0 COMMENT '修改人',
  isCache TINYINT(1) DEFAULT 0 COMMENT '是否缓存，true为是，false不是，默认false',
  description VARCHAR(200) DEFAULT '' COMMENT '参数描述',
  operator CHAR(4) DEFAULT '1111' COMMENT '字典对应的操作',
  PRIMARY KEY (sysKey)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '系统参数表';