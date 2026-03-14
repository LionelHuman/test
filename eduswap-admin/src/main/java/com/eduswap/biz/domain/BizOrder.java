package com.eduswap.biz.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 订单对象 biz_order
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;

    /** 买家ID */
    @Excel(name = "买家ID")
    private Long userId;

    /** 卖家ID */
    @Excel(name = "卖家ID")
    private Long sellerId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long productId;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal orderAmount;

    /** 收货地址ID */
    @Excel(name = "收货地址ID")
    private Long addressId;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String orderStatus;

    /** 支付方式 */
    @Excel(name = "支付方式")
    private String payType;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 发货/交付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发货/交付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deliveryTime;

    /** 确认收货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 取消原因 */
    @Excel(name = "取消原因")
    private String cancelReason;

    /** 删除标记 */
    private String delFlag;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }

    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
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

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setOrderAmount(BigDecimal orderAmount) 
    {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderAmount() 
    {
        return orderAmount;
    }

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }

    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }

    public void setPayType(String payType) 
    {
        this.payType = payType;
    }

    public String getPayType() 
    {
        return payType;
    }

    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }

    public void setDeliveryTime(Date deliveryTime) 
    {
        this.deliveryTime = deliveryTime;
    }

    public Date getDeliveryTime() 
    {
        return deliveryTime;
    }

    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime() 
    {
        return receiveTime;
    }

    public void setCancelReason(String cancelReason) 
    {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason() 
    {
        return cancelReason;
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
            .append("orderId", getOrderId())
            .append("orderNo", getOrderNo())
            .append("userId", getUserId())
            .append("sellerId", getSellerId())
            .append("productId", getProductId())
            .append("orderAmount", getOrderAmount())
            .append("addressId", getAddressId())
            .append("orderStatus", getOrderStatus())
            .append("payType", getPayType())
            .append("payTime", getPayTime())
            .append("deliveryTime", getDeliveryTime())
            .append("receiveTime", getReceiveTime())
            .append("cancelReason", getCancelReason())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
