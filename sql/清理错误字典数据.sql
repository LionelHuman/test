-- ============================================
-- 清理错误的字典数据
-- ============================================

-- 删除商品分类中错误的测试数据（dict_label = '1'）
DELETE FROM sys_dict_data 
WHERE dict_type = 'product_class' 
  AND dict_label = '1';

-- 查询清理后的数据
SELECT dict_code, dict_sort, dict_label, dict_value, dict_type, list_class, status 
FROM sys_dict_data 
WHERE dict_type = 'product_class' 
ORDER BY dict_sort;
