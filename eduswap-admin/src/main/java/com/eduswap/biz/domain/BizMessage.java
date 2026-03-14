package com.eduswap.biz.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.eduswap.common.annotation.Excel;
import com.eduswap.common.core.domain.BaseEntity;

/**
 * 消息对象 biz_message
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public class BizMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long msgId;

    /** 发送人ID */
    @Excel(name = "发送人ID")
    private Long sendUserId;

    /** 接收人ID */
    @Excel(name = "接收人ID")
    private Long receiveUserId;

    /** 关联商品ID */
    @Excel(name = "关联商品ID")
    private Long productId;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String msgContent;

    /** 是否已读 */
    @Excel(name = "是否已读")
    private String isRead;

    /** 删除标记 */
    private String delFlag;

    public void setMsgId(Long msgId) 
    {
        this.msgId = msgId;
    }

    public Long getMsgId() 
    {
        return msgId;
    }

    public void setSendUserId(Long sendUserId) 
    {
        this.sendUserId = sendUserId;
    }

    public Long getSendUserId() 
    {
        return sendUserId;
    }

    public void setReceiveUserId(Long receiveUserId) 
    {
        this.receiveUserId = receiveUserId;
    }

    public Long getReceiveUserId() 
    {
        return receiveUserId;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setMsgContent(String msgContent) 
    {
        this.msgContent = msgContent;
    }

    public String getMsgContent() 
    {
        return msgContent;
    }

    public void setIsRead(String isRead) 
    {
        this.isRead = isRead;
    }

    public String getIsRead() 
    {
        return isRead;
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
            .append("msgId", getMsgId())
            .append("sendUserId", getSendUserId())
            .append("receiveUserId", getReceiveUserId())
            .append("productId", getProductId())
            .append("msgContent", getMsgContent())
            .append("isRead", getIsRead())
            .append("createTime", getCreateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
