package com.eduswap.biz.mapper;

import java.util.List;
import com.eduswap.biz.domain.BizProductCategory;

/**
 * 商品分类Mapper接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface BizProductCategoryMapper 
{
    /**
     * 查询商品分类
     * 
     * @param categoryId 商品分类主键
     * @return 商品分类
     */
    public BizProductCategory selectBizProductCategoryByCategoryId(Long categoryId);

    /**
     * 查询商品分类列表
     * 
     * @param bizProductCategory 商品分类
     * @return 商品分类集合
     */
    public List<BizProductCategory> selectBizProductCategoryList(BizProductCategory bizProductCategory);

    /**
     * 新增商品分类
     * 
     * @param bizProductCategory 商品分类
     * @return 结果
     */
    public int insertBizProductCategory(BizProductCategory bizProductCategory);

    /**
     * 修改商品分类
     * 
     * @param bizProductCategory 商品分类
     * @return 结果
     */
    public int updateBizProductCategory(BizProductCategory bizProductCategory);

    /**
     * 删除商品分类
     * 
     * @param categoryId 商品分类主键
     * @return 结果
     */
    public int deleteBizProductCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除商品分类
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizProductCategoryByCategoryIds(Long[] categoryIds);
}
