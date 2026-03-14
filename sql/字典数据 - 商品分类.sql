-- ============================================
-- 商品分类字典数据 SQL
-- 字典类型：product_class (商品分类)
-- 目标表：sys_dict_data
-- ============================================

-- 插入商品分类字典数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '数码产品', '100', 'product_class', '', 'primary', 'Y', '0', 'admin', NOW(), '手机、电脑、平板、数码配件等'),
(2, '图书文具', '101', 'product_class', '', 'success', 'N', '0', 'admin', NOW(), '教材、小说、文具、办公用品等'),
(3, '服装饰物', '102', 'product_class', '', 'warning', 'N', '0', 'admin', NOW(), '衣服、鞋子、包包、配饰等'),
(4, '生活用品', '103', 'product_class', '', 'info', 'N', '0', 'admin', NOW(), '日用品、小家电、家居用品等'),
(5, '运动户外', '104', 'product_class', '', 'danger', 'N', '0', 'admin', NOW(), '运动器材、户外用品、健身器材等'),
(6, '美妆护肤', '105', 'product_class', '', 'pink', 'N', '0', 'admin', NOW(), '化妆品、护肤品、香水等'),
(7, '母婴用品', '106', 'product_class', '', 'purple', 'N', '0', 'admin', NOW(), '婴儿用品、儿童玩具、母婴用品等'),
(8, '食品零食', '107', 'product_class', '', 'orange', 'N', '0', 'admin', NOW(), '零食、特产、保健食品等'),
(9, '虚拟物品', '108', 'product_class', '', 'cyan', 'N', '0', 'admin', NOW(), '游戏账号、充值卡、优惠券等'),
(10, '其他物品', '109', 'product_class', '', 'default', 'N', '0', 'admin', NOW(), '其他闲置物品');

-- 查询插入的字典数据
SELECT * FROM sys_dict_data WHERE dict_type = 'product_class' ORDER BY dict_sort;
