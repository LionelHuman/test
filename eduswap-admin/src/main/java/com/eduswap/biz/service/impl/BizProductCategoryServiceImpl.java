package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizProductCategoryMapper;
import com.eduswap.biz.domain.BizProductCategory;
import com.eduswap.biz.service.IBizProductCategoryService;

/**
 * 商品分类Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizProductCategoryServiceImpl implements IBizProductCategoryService 
{
    @Autowired
    private BizProductCategoryMapper bizProductCategoryMapper;

    /**
     * 查询商品分类
     * 
     * @param categoryId 商品分类主键
     * @return 商品分类
     */
    @Override
    public BizProductCategory selectBizProductCategoryByCategoryId(Long categoryId)
    {
        return bizProductCategoryMapper.selectBizProductCategoryByCategoryId(categoryId);
    }

    /**
     * 查询商品分类列表
     * 
     * @param bizProductCategory 商品分类
     * @return 商品分类
     */
    @Override
    public List<BizProductCategory> selectBizProductCategoryList(BizProductCategory bizProductCategory)
    {
        return bizProductCategoryMapper.selectBizProductCategoryList(bizProductCategory);
    }

    /**
     * 新增商品分类
     * 
     * @param bizProductCategory 商品分类
     * @return 结果
     */
    @Override
    public int insertBizProductCategory(BizProductCategory bizProductCategory)
    {
        bizProductCategory.setCreateTime(DateUtils.getNowDate());
        return bizProductCategoryMapper.insertBizProductCategory(bizProductCategory);
    }

    /**
     * 修改商品分类
     * 
     * @param bizProductCategory 商品分类
     * @return 结果
     */
    @Override
    public int updateBizProductCategory(BizProductCategory bizProductCategory)
    {
        bizProductCategory.setUpdateTime(DateUtils.getNowDate());
        return bizProductCategoryMapper.updateBizProductCategory(bizProductCategory);
    }

    /**
     * 批量删除商品分类
     * 
     * @param categoryIds 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteBizProductCategoryByCategoryIds(Long[] categoryIds)
    {
        return bizProductCategoryMapper.deleteBizProductCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除商品分类信息
     * 
     * @param categoryId 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteBizProductCategoryByCategoryId(Long categoryId)
    {
        return bizProductCategoryMapper.deleteBizProductCategoryByCategoryId(categoryId);
    }
}
