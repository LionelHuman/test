-- 删除商品详情菜单
-- 用于移除后台管理系统左侧菜单中的"商品详情"项

-- 1. 查找商品详情菜单
-- SELECT * FROM sys_menu WHERE menu_name = '商品详情' OR path LIKE '%product%detail%';

-- 2. 删除商品详情菜单（根据路径匹配）
DELETE FROM sys_menu WHERE menu_name = '商品详情';

-- 3. 如果有子菜单或按钮权限，也一并删除
DELETE FROM sys_menu WHERE parent_id IN (
    SELECT menu_id FROM (
        SELECT menu_id FROM sys_menu WHERE menu_name = '商品详情'
    ) AS temp
);

-- 4. 删除角色菜单关联
DELETE FROM sys_role_menu WHERE menu_id IN (
    SELECT menu_id FROM (
        SELECT menu_id FROM sys_menu WHERE menu_name = '商品详情'
    ) AS temp
);

-- 说明：
-- 1. 执行此脚本后需要重启后端服务才能生效
-- 2. 如果菜单已经不存在，此脚本不会报错
-- 3. 建议先备份数据库再执行删除操作
