-- 扩展sys_user表，添加大学生相关字段
ALTER TABLE `sys_user`
    ADD COLUMN `student_no` varchar(20) DEFAULT '' COMMENT '学号' AFTER `nick_name`,
ADD COLUMN `college` varchar(50) DEFAULT '' COMMENT '学院' AFTER `student_no`,
ADD COLUMN `grade` varchar(20) DEFAULT '' COMMENT '年级' AFTER `college`,
ADD COLUMN `real_name_auth` char(1) DEFAULT '0' COMMENT '实名认证状态（0未认证/1已认证）' AFTER `grade`;