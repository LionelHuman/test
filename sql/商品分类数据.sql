-- ============================================
-- 商品分类数据 SQL
-- 表名：biz_product_category
-- ============================================

-- 插入商品分类数据
INSERT INTO biz_product_category (parent_id, category_name, category_code, sort, status, create_by, create_time, del_flag) VALUES
(0, '数码产品', 'digital', 1, '1', 'admin', NOW(), '0'),
(0, '图书文具', 'books', 2, '1', 'admin', NOW(), '0'),
(0, '服装饰物', 'clothing', 3, '1', 'admin', NOW(), '0'),
(0, '生活用品', 'daily', 4, '1', 'admin', NOW(), '0'),
(0, '运动户外', 'sports', 5, '1', 'admin', NOW(), '0'),
(0, '美妆护肤', 'beauty', 6, '1', 'admin', NOW(), '0'),
(0, '母婴用品', 'baby', 7, '1', 'admin', NOW(), '0'),
(0, '食品零食', 'food', 8, '1', 'admin', NOW(), '0'),
(0, '虚拟物品', 'virtual', 9, '1', 'admin', NOW(), '0'),
(0, '其他物品', 'other', 10, '1', 'admin', NOW(), '0');

-- 查询插入的分类数据
SELECT * FROM biz_product_category ORDER BY sort;
