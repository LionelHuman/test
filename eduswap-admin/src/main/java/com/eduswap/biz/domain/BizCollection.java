package com.eduswap.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 收藏对象 biz_collection
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizCollection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收藏ID */
    private Long collectId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long productId;

    /** 删除标记 */
    private String delFlag;

    public void setCollectId(Long collectId) 
    {
        this.collectId = collectId;
    }

    public Long getCollectId() 
    {
        return collectId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
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
            .append("collectId", getCollectId())
            .append("userId", getUserId())
            .append("productId", getProductId())
            .append("createTime", getCreateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
