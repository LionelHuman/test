package com.eduswap.biz.mapper;

import java.util.List;
import com.eduswap.biz.domain.BizProductImage;

/**
 * 商品图片Mapper接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface BizProductImageMapper 
{
    /**
     * 查询商品图片
     * 
     * @param imageId 商品图片主键
     * @return 商品图片
     */
    public BizProductImage selectBizProductImageByImageId(Long imageId);

    /**
     * 查询商品图片列表
     * 
     * @param bizProductImage 商品图片
     * @return 商品图片集合
     */
    public List<BizProductImage> selectBizProductImageList(BizProductImage bizProductImage);

    /**
     * 新增商品图片
     * 
     * @param bizProductImage 商品图片
     * @return 结果
     */
    public int insertBizProductImage(BizProductImage bizProductImage);

    /**
     * 修改商品图片
     * 
     * @param bizProductImage 商品图片
     * @return 结果
     */
    public int updateBizProductImage(BizProductImage bizProductImage);

    /**
     * 删除商品图片
     * 
     * @param imageId 商品图片主键
     * @return 结果
     */
    public int deleteBizProductImageByImageId(Long imageId);

    /**
     * 批量删除商品图片
     * 
     * @param imageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizProductImageByImageIds(Long[] imageIds);
}
