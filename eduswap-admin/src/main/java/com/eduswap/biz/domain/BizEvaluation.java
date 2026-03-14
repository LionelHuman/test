package com.eduswap.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 评价对象 biz_evaluation
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizEvaluation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评价ID */
    private Long evalId;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long orderId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long productId;

    /** 评价人ID */
    @Excel(name = "评价人ID")
    private Long userId;

    /** 被评价人ID */
    @Excel(name = "被评价人ID")
    private Long sellerId;

    /** 评分 */
    @Excel(name = "评分")
    private Long evalScore;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String evalContent;

    /** 评价图片 */
    @Excel(name = "评价图片")
    private String evalPics;

    /** 卖家回复 */
    @Excel(name = "卖家回复")
    private String replyContent;

    /** 回复时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date replyTime;

    /** 删除标记 */
    private String delFlag;

    public void setEvalId(Long evalId) 
    {
        this.evalId = evalId;
    }

    public Long getEvalId() 
    {
        return evalId;
    }

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setSellerId(Long sellerId) 
    {
        this.sellerId = sellerId;
    }

    public Long getSellerId() 
    {
        return sellerId;
    }

    public void setEvalScore(Long evalScore) 
    {
        this.evalScore = evalScore;
    }

    public Long getEvalScore() 
    {
        return evalScore;
    }

    public void setEvalContent(String evalContent) 
    {
        this.evalContent = evalContent;
    }

    public String getEvalContent() 
    {
        return evalContent;
    }

    public void setEvalPics(String evalPics) 
    {
        this.evalPics = evalPics;
    }

    public String getEvalPics() 
    {
        return evalPics;
    }

    public void setReplyContent(String replyContent) 
    {
        this.replyContent = replyContent;
    }

    public String getReplyContent() 
    {
        return replyContent;
    }

    public void setReplyTime(Date replyTime) 
    {
        this.replyTime = replyTime;
    }

    public Date getReplyTime() 
    {
        return replyTime;
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
            .append("evalId", getEvalId())
            .append("orderId", getOrderId())
            .append("productId", getProductId())
            .append("userId", getUserId())
            .append("sellerId", getSellerId())
            .append("evalScore", getEvalScore())
            .append("evalContent", getEvalContent())
            .append("evalPics", getEvalPics())
            .append("replyContent", getReplyContent())
            .append("replyTime", getReplyTime())
            .append("createTime", getCreateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
