-- ============================================
-- 创建顾客角色 SQL
-- ============================================

-- 1. 插入顾客角色到 sys_role 表
INSERT INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, update_by, update_time, remark) VALUES
('3', '顾客角色', 'customer', 3, '1', 1, 1, '0', '0', 'admin', NOW(), '', NULL, '普通用户，可以浏览商品、发布商品、购买商品');

-- 2. 查询插入的角色
SELECT * FROM sys_role WHERE role_key = 'customer';

-- ============================================
-- 创建顾客用户 SQL（示例）
-- ============================================

-- 3. 插入一个测试顾客用户到 sys_user 表
-- 用户名：customer1，密码：123456
INSERT INTO sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_by, create_time, update_by, update_time, remark) VALUES
('101', '103', 'customer1', '顾客测试', '00', 'customer@test.com', '13800138001', '1', '', '$2a$10$7JBdZ0S1bYQsU1z1x1x1x1x1x1x1x1x1x1x1x1x1x1x1x1x1x1x', '0', '0', '', NULL, 'admin', NOW(), '', NULL, '顾客测试用户');

-- 注意：上面的密码是加密的，实际应该使用 BCrypt 加密
-- 简单方法：在若依后台管理系统中创建用户，系统会自动加密密码

-- 4. 查询插入的用户
SELECT user_id, user_name, nick_name, phonenumber, status, create_time FROM sys_user WHERE user_name = 'customer1';

-- ============================================
-- 使用说明
-- ============================================
-- 1. 执行第 1 条 SQL 创建顾客角色
-- 2. 在若依后台管理系统中创建用户（推荐）
--    - 路径：系统管理 -> 用户管理 -> 新增
--    - 用户名：customer1
--    - 昵称：顾客测试
--    - 手机号：13800138001
--    - 角色：选择"顾客角色"
--    - 密码：123456（系统会自动加密）
-- ============================================
