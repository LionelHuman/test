-- ============================================
-- 商品成色字典数据 SQL
-- 字典类型：product_grade (商品成色)
-- 目标表：sys_dict_data
-- ============================================

-- 插入商品成色字典数据
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '99 新', '0', 'product_grade', '', 'primary', 'Y', '0', 'admin', NOW(), '几乎全新，仅拆封'),
(2, '95 新', '1', 'product_grade', '', 'success', 'N', '0', 'admin', NOW(), '轻微使用痕迹'),
(3, '9 新', '2', 'product_grade', '', 'info', 'N', '0', 'admin', NOW(), '有明显使用痕迹'),
(4, '8 新', '3', 'product_grade', '', 'warning', 'N', '0', 'admin', NOW(), '使用痕迹明显，功能正常'),
(5, '破损', '4', 'product_grade', '', 'danger', 'N', '0', 'admin', NOW(), '有破损或故障');

-- 查询插入的字典数据
SELECT * FROM sys_dict_data WHERE dict_type = 'product_grade' ORDER BY dict_sort;
