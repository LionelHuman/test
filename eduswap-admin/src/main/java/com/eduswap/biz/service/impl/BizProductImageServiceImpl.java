package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizProductImageMapper;
import com.eduswap.biz.domain.BizProductImage;
import com.eduswap.biz.service.IBizProductImageService;

/**
 * 商品图片Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizProductImageServiceImpl implements IBizProductImageService 
{
    @Autowired
    private BizProductImageMapper bizProductImageMapper;

    /**
     * 查询商品图片
     * 
     * @param imageId 商品图片主键
     * @return 商品图片
     */
    @Override
    public BizProductImage selectBizProductImageByImageId(Long imageId)
    {
        return bizProductImageMapper.selectBizProductImageByImageId(imageId);
    }

    /**
     * 查询商品图片列表
     * 
     * @param bizProductImage 商品图片
     * @return 商品图片
     */
    @Override
    public List<BizProductImage> selectBizProductImageList(BizProductImage bizProductImage)
    {
        return bizProductImageMapper.selectBizProductImageList(bizProductImage);
    }

    /**
     * 新增商品图片
     * 
     * @param bizProductImage 商品图片
     * @return 结果
     */
    @Override
    public int insertBizProductImage(BizProductImage bizProductImage)
    {
        bizProductImage.setCreateTime(DateUtils.getNowDate());
        return bizProductImageMapper.insertBizProductImage(bizProductImage);
    }

    /**
     * 修改商品图片
     * 
     * @param bizProductImage 商品图片
     * @return 结果
     */
    @Override
    public int updateBizProductImage(BizProductImage bizProductImage)
    {
        return bizProductImageMapper.updateBizProductImage(bizProductImage);
    }

    /**
     * 批量删除商品图片
     * 
     * @param imageIds 需要删除的商品图片主键
     * @return 结果
     */
    @Override
    public int deleteBizProductImageByImageIds(Long[] imageIds)
    {
        return bizProductImageMapper.deleteBizProductImageByImageIds(imageIds);
    }

    /**
     * 删除商品图片信息
     * 
     * @param imageId 商品图片主键
     * @return 结果
     */
    @Override
    public int deleteBizProductImageByImageId(Long imageId)
    {
        return bizProductImageMapper.deleteBizProductImageByImageId(imageId);
    }
}
