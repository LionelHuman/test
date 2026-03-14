-- 商品成色字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`)
VALUES (NULL, '商品成色', 'product_grade', '1', 'admin', NOW());
INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `status`)
VALUES (NULL, 1, '99新', '1', 'product_grade', '1'),
       (NULL, 2, '95新', '2', 'product_grade', '1'),
       (NULL, 3, '9新', '3', 'product_grade', '1'),
       (NULL, 4, '8新', '4', 'product_grade', '1'),
       (NULL, 5, '破损', '5', 'product_grade', '1');