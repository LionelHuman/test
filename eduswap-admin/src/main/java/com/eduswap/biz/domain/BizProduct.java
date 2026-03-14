package com.eduswap.biz.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 商品对象 biz_product
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    private Long productId;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 发布者ID */
    @Excel(name = "发布者ID")
    private Long userId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String productName;

    /** 商品描述 */
    @Excel(name = "商品描述")
    private String productDesc;

    /** 原价 */
    @Excel(name = "原价")
    private BigDecimal originalPrice;

    /** 现价 */
    @Excel(name = "现价")
    private BigDecimal currentPrice;

    /** 商品成色 */
    @Excel(name = "商品成色")
    private String productGrade;

    /** 是否保修 */
    @Excel(name = "是否保修")
    private String isWarranty;

    /** 保修期限 */
    @Excel(name = "保修期限")
    private String warrantyPeriod;

    /** 商品主图 */
    @Excel(name = "商品主图")
    private String productPics;

    /** 商品状态 */
    @Excel(name = "商品状态")
    private String status;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long viewCount;

    /** 删除标记 */
    private String delFlag;

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setProductDesc(String productDesc) 
    {
        this.productDesc = productDesc;
    }

    public String getProductDesc() 
    {
        return productDesc;
    }

    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) 
    {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getCurrentPrice() 
    {
        return currentPrice;
    }

    public void setProductGrade(String productGrade) 
    {
        this.productGrade = productGrade;
    }

    public String getProductGrade() 
    {
        return productGrade;
    }

    public void setIsWarranty(String isWarranty) 
    {
        this.isWarranty = isWarranty;
    }

    public String getIsWarranty() 
    {
        return isWarranty;
    }

    public void setWarrantyPeriod(String warrantyPeriod) 
    {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyPeriod() 
    {
        return warrantyPeriod;
    }

    public void setProductPics(String productPics) 
    {
        this.productPics = productPics;
    }

    public String getProductPics() 
    {
        return productPics;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setViewCount(Long viewCount) 
    {
        this.viewCount = viewCount;
    }

    public Long getViewCount() 
    {
        return viewCount;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("productId", getProductId())
            .append("categoryId", getCategoryId())
            .append("userId", getUserId())
            .append("productName", getProductName())
            .append("productDesc", getProductDesc())
            .append("originalPrice", getOriginalPrice())
            .append("currentPrice", getCurrentPrice())
            .append("productGrade", getProductGrade())
            .append("isWarranty", getIsWarranty())
            .append("warrantyPeriod", getWarrantyPeriod())
            .append("productPics", getProductPics())
            .append("status", getStatus())
            .append("viewCount", getViewCount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
