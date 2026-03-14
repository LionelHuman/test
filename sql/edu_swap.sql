-- ----------------------------
-- 1. 商品分类表 (biz_product_category)
-- ----------------------------
DROP TABLE IF EXISTS `biz_product_category`;
CREATE TABLE `biz_product_category` (
                                        `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                                        `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父分类ID（顶级分类为0）',
                                        `category_name` varchar(50) NOT NULL COMMENT '分类名称',
                                        `category_code` varchar(30) DEFAULT NULL COMMENT '分类编码',
                                        `sort` int NOT NULL DEFAULT '0' COMMENT '排序号',
                                        `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态（0禁用/1启用）',
                                        `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
                                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                                        PRIMARY KEY (`category_id`),
                                        KEY `idx_category_parent_id` (`parent_id`),
                                        KEY `idx_category_status` (`status`),
                                        KEY `idx_category_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- ----------------------------
-- 2. 商品表 (biz_product) 【核心】
-- ----------------------------
DROP TABLE IF EXISTS `biz_product`;
CREATE TABLE `biz_product` (
                               `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
                               `category_id` bigint NOT NULL COMMENT '分类ID',
                               `user_id` bigint NOT NULL COMMENT '发布者ID（卖家）',
                               `product_name` varchar(100) NOT NULL COMMENT '商品名称',
                               `product_desc` text COMMENT '商品描述',
                               `original_price` decimal(10,2) DEFAULT '0.00' COMMENT '原价（元）',
                               `current_price` decimal(10,2) NOT NULL COMMENT '现价（元）',
                               `product_grade` char(1) DEFAULT NULL COMMENT '商品成色（关联字典：99新/95新/9新/8新/破损）',
                               `is_warranty` char(1) DEFAULT '0' COMMENT '是否保修（0否/1是）',
                               `warranty_period` varchar(50) DEFAULT '' COMMENT '保修期限',
                               `product_pics` varchar(1000) DEFAULT '' COMMENT '商品主图（多张逗号分隔）',
                               `status` char(1) NOT NULL DEFAULT '0' COMMENT '商品状态（0审核中/1在售/2已售出/3下架）',
                               `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览量',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                               PRIMARY KEY (`product_id`),
                               KEY `idx_product_user_id` (`user_id`),
                               KEY `idx_product_category_id` (`category_id`),
                               KEY `idx_product_status` (`status`),
                               KEY `idx_product_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- ----------------------------
-- 3. 商品图片表 (biz_product_image)
-- ----------------------------
DROP TABLE IF EXISTS `biz_product_image`;
CREATE TABLE `biz_product_image` (
                                     `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
                                     `product_id` bigint NOT NULL COMMENT '商品ID',
                                     `image_url` varchar(255) NOT NULL COMMENT '图片URL',
                                     `sort` int NOT NULL DEFAULT '0' COMMENT '排序号',
                                     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     PRIMARY KEY (`image_id`),
                                     KEY `idx_image_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片表';

-- ----------------------------
-- 4. 收货地址表 (biz_address)
-- ----------------------------
DROP TABLE IF EXISTS `biz_address`;
CREATE TABLE `biz_address` (
                               `address_id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
                               `user_id` bigint NOT NULL COMMENT '用户ID',
                               `receiver_name` varchar(20) NOT NULL COMMENT '收件人姓名',
                               `receiver_phone` varchar(11) NOT NULL COMMENT '收件人手机号',
                               `school_id` bigint DEFAULT '0' COMMENT '学校/学院ID（关联sys_dept）',
                               `address_detail` varchar(200) NOT NULL COMMENT '详细地址',
                               `is_default` char(1) NOT NULL DEFAULT '0' COMMENT '是否默认地址（0否/1是）',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                               PRIMARY KEY (`address_id`),
                               KEY `idx_address_user_id` (`user_id`),
                               KEY `idx_address_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收货地址表';

-- ----------------------------
-- 5. 订单表 (biz_order) 【核心】
-- ----------------------------
DROP TABLE IF EXISTS `biz_order`;
CREATE TABLE `biz_order` (
                             `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                             `order_no` varchar(30) NOT NULL COMMENT '订单编号（唯一）',
                             `user_id` bigint NOT NULL COMMENT '买家ID',
                             `seller_id` bigint NOT NULL COMMENT '卖家ID',
                             `product_id` bigint NOT NULL COMMENT '商品ID',
                             `order_amount` decimal(10,2) NOT NULL COMMENT '订单金额（元）',
                             `address_id` bigint NOT NULL COMMENT '收货地址ID',
                             `order_status` char(1) NOT NULL DEFAULT '0' COMMENT '订单状态（0待付款/1待发货/2待收货/3已完成/4已取消/5退款中）',
                             `pay_type` char(1) DEFAULT NULL COMMENT '支付方式（1微信/2支付宝/3线下转账）',
                             `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
                             `delivery_time` datetime DEFAULT NULL COMMENT '发货/交付时间',
                             `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
                             `cancel_reason` varchar(200) DEFAULT '' COMMENT '取消原因',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                             PRIMARY KEY (`order_id`),
                             UNIQUE KEY `uk_order_no` (`order_no`),
                             KEY `idx_order_user_id` (`user_id`),
                             KEY `idx_order_seller_id` (`seller_id`),
                             KEY `idx_order_product_id` (`product_id`),
                             KEY `idx_order_status` (`order_status`),
                             KEY `idx_order_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- ----------------------------
-- 6. 评价表 (biz_evaluation)
-- ----------------------------
DROP TABLE IF EXISTS `biz_evaluation`;
CREATE TABLE `biz_evaluation` (
                                  `eval_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
                                  `order_id` bigint NOT NULL COMMENT '订单ID',
                                  `product_id` bigint NOT NULL COMMENT '商品ID',
                                  `user_id` bigint NOT NULL COMMENT '评价人ID（买家）',
                                  `seller_id` bigint NOT NULL COMMENT '被评价人ID（卖家）',
                                  `eval_score` int NOT NULL COMMENT '评分（1-5星）',
                                  `eval_content` text COMMENT '评价内容',
                                  `eval_pics` varchar(1000) DEFAULT '' COMMENT '评价图片（多张逗号分隔）',
                                  `reply_content` text DEFAULT '' COMMENT '卖家回复',
                                  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                                  PRIMARY KEY (`eval_id`),
                                  UNIQUE KEY `uk_order_eval` (`order_id`),
                                  KEY `idx_eval_product_id` (`product_id`),
                                  KEY `idx_eval_seller_id` (`seller_id`),
                                  KEY `idx_eval_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评价表';

-- ----------------------------
-- 7. 收藏表 (biz_collection)
-- ----------------------------
DROP TABLE IF EXISTS `biz_collection`;
CREATE TABLE `biz_collection` (
                                  `collect_id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `product_id` bigint NOT NULL COMMENT '商品ID',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
                                  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                                  PRIMARY KEY (`collect_id`),
                                  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`),
                                  KEY `idx_collect_product_id` (`product_id`),
                                  KEY `idx_collect_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';

-- ----------------------------
-- 8. 消息表 (biz_message)
-- ----------------------------
DROP TABLE IF EXISTS `biz_message`;
CREATE TABLE `biz_message` (
                               `msg_id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
                               `send_user_id` bigint NOT NULL COMMENT '发送人ID',
                               `receive_user_id` bigint NOT NULL COMMENT '接收人ID',
                               `product_id` bigint DEFAULT '0' COMMENT '关联商品ID（0为无关联）',
                               `msg_content` varchar(500) NOT NULL COMMENT '消息内容',
                               `is_read` char(1) NOT NULL DEFAULT '0' COMMENT '是否已读（0未读/1已读）',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
                               `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0正常/1删除）',
                               PRIMARY KEY (`msg_id`),
                               KEY `idx_msg_send_user` (`send_user_id`),
                               KEY `idx_msg_receive_user` (`receive_user_id`),
                               KEY `idx_msg_product_id` (`product_id`),
                               KEY `idx_msg_is_read` (`is_read`),
                               KEY `idx_msg_del_flag` (`del_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';