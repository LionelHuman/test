package com.eduswap.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 商品分类对象 biz_product_category
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizProductCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Long categoryId;

    /** 父分类ID */
    @Excel(name = "父分类ID")
    private Long parentId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 分类编码 */
    @Excel(name = "分类编码")
    private String categoryCode;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sort;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标记 */
    private String delFlag;

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }

    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }

    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("categoryId", getCategoryId())
            .append("parentId", getParentId())
            .append("categoryName", getCategoryName())
            .append("categoryCode", getCategoryCode())
            .append("sort", getSort())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
