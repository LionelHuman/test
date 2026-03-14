package com.eduswap.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 商品图片对象 biz_product_image
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizProductImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    private Long imageId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long productId;

    /** 图片URL */
    @Excel(name = "图片URL")
    private String imageUrl;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sort;

    public void setImageId(Long imageId) 
    {
        this.imageId = imageId;
    }

    public Long getImageId() 
    {
        return imageId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imageId", getImageId())
            .append("productId", getProductId())
            .append("imageUrl", getImageUrl())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .toString();
    }
}
