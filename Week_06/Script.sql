-- 基于电商交易场景（用户、商品、订单），设计一套简单的表结构

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user(
	id bigint NOT NULL auto_increment COMMENT'主键',
	name varchar(100) NOT NULL COMMENT '姓名',
	nick_name varchar(100) DEFAULT '' COMMENT '昵称',
	phone varchar(11)  NOT NULL COMMENT '手机号码',
	status char(1) NOT NULL  DEFAULT '0' COMMENT '0正常,否则不正常',
	reg_time timestamp NOT NULL COMMENT '注册时间',
	update_time timestamp   COMMENT '修改时间',
	PRIMARY KEY  (`id`)
)engine=innodb DEFAULT charset=utf8mb4 COMMENT '用户表';

DROP TABLE IF EXISTS t_goods(
	id bigint NOT NULL auto_increment COMMENT '主键',
	goods_name varchar(200) NOT NULL COMMENT '商品名称',
	goods_image varchar(200) NOT NULL COMMENT '商品图片',
	goods_price decimal(10,2) NOT NULL COMMENT '商品价格',
	goods_desc varchar(200) NOT NULL COMMENT '商品备注',
	goods_status char(2) NOT NULL COMMENT '商品状态',
	create_time timestamp NOT NULL COMMENT '商品创建时间',
	is_del char(1) NOT NULL  DEFAULT '0' COMMENT '是否删除',
	PRIMARY KEY  (`id`)
)engine=innodb DEFAULT charset=utf8mb4 COMMENT '商品表';

DROP TABLE IF EXISTS t_order;
CREATE TABLE t_order(
	id bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
	good_id bigint NOT NULL COMMENT '商品id',
	user_id bigint NOT NULL COMMENT '用户id',
	order_price decimal(10,2) NOT NULL COMMENT '订单金额',
	order_address NOT NULL COMMENT '订单地址',
	order_time timestamp NOT NULL COMMENT '下单时间',
	pay_way char(2) NOT NULL COMMENT '支付方式',
	pay_time timestamp NOT NULL COMMENT '支付时间',
	order_status  char(2) NOT NULL COMMENT '订单状态',
	PRIMARY KEY (`id`)
)ENGINE=Innodb DEFAULT CHARSET=utf8mb4  COMMENT '订单表';