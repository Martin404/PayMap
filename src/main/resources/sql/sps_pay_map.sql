CREATE TABLE sps_pay_map(
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  orderId BIGINT(20) NOT NULL COMMENT '所属订单ID',
  orderCode VARCHAR(200) NOT NULL COMMENT '所属订单code',
  tempPayCode VARCHAR(200) NOT NULL COMMENT '临时支付号ID',
  platform VARCHAR(200) DEFAULT NULL COMMENT '所属平台',
  payParams VARCHAR(3500) DEFAULT NULL COMMENT '支付所生成的请求信息',
  retMsg VARCHAR(800) DEFAULT NULL COMMENT '支付后回调时的详细信息',
  retMsg2 VARCHAR(800) DEFAULT NULL COMMENT '备用消息',
  isPaid CHAR(1) DEFAULT NULL COMMENT '是否已支付0否；1是',
  remark VARCHAR(200) DEFAULT NULL,
  swiftNumber VARCHAR(60) DEFAULT NULL COMMENT '交易流水号',
  payPurpose VARCHAR(30) DEFAULT NULL COMMENT '交易意图：支付订单，补齐差价',
  idBelongsTo VARCHAR(60) DEFAULT NULL COMMENT 'orderId 所在的表 ',
  cashAmt DECIMAL(18, 2) DEFAULT NULL COMMENT '本次支付所使用的现金账户金额，sps_order表中有该字段，所以普通订单支付时此字段为空',
  remark2 VARCHAR(200) DEFAULT NULL,
  notify_time BIGINT(20) DEFAULT NULL COMMENT '通知回调时间',
  requestBiz VARCHAR(200) DEFAULT NULL COMMENT '支付请求业务来源',
  PRIMARY KEY (id),
  INDEX orderCode (orderCode),
  INDEX orderId (orderId)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '订单表';
