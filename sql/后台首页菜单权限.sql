-- 后台首页菜单权限配置
-- 用于支持 /system/home/stat 等接口的权限控制

-- 1. 插入菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('后台首页', 0, 1, 'system/home', null, 1, 0, 'C', '0', '0', 'system:home:query', 'dashboard', 'admin', NOW(), '后台管理首页');

-- 获取刚插入的菜单 ID
SET @menuId = LAST_INSERT_ID();

-- 2. 插入按钮权限（查询）
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('首页统计查询', @menuId, 1, '', null, 1, 0, 'F', '0', '0', 'system:home:query', '#', 'admin', NOW(), '首页统计数据查询权限');

-- 获取访问趋势权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('访问趋势查询', @menuId, 2, '', null, 1, 0, 'F', '0', '0', 'system:home:visit:query', '#', 'admin', NOW(), '近 7 日访问趋势查询权限');

-- 获取用户分布权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('用户分布查询', @menuId, 3, '', null, 1, 0, 'F', '0', '0', 'system:home:user:query', '#', 'admin', NOW(), '用户类型分布查询权限');

-- 3. 给 admin 角色分配权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'admin' AND m.perms IN ('system:home:query', 'system:home:visit:query', 'system:home:user:query');

-- 4. 给 common 角色分配权限（如果需要）
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'common' AND m.perms IN ('system:home:query', 'system:home:visit:query', 'system:home:user:query');

-- 说明：
-- 1. 该脚本添加了后台首页菜单及权限
-- 2. 权限标识：system:home:query（统计查询）、system:home:visit:query（访问趋势）、system:home:user:query（用户分布）
-- 3. admin 和 common 角色自动获得这些权限
-- 4. 执行后需要重启后端服务才能生效
