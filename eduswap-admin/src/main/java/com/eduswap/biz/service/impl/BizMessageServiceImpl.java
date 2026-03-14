package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizMessageMapper;
import com.eduswap.biz.domain.BizMessage;
import com.eduswap.biz.service.IBizMessageService;

/**
 * 消息Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizMessageServiceImpl implements IBizMessageService 
{
    @Autowired
    private BizMessageMapper bizMessageMapper;

    /**
     * 查询消息
     * 
     * @param msgId 消息主键
     * @return 消息
     */
    @Override
    public BizMessage selectBizMessageByMsgId(Long msgId)
    {
        return bizMessageMapper.selectBizMessageByMsgId(msgId);
    }

    /**
     * 查询消息列表
     * 
     * @param bizMessage 消息
     * @return 消息
     */
    @Override
    public List<BizMessage> selectBizMessageList(BizMessage bizMessage)
    {
        return bizMessageMapper.selectBizMessageList(bizMessage);
    }

    /**
     * 新增消息
     * 
     * @param bizMessage 消息
     * @return 结果
     */
    @Override
    public int insertBizMessage(BizMessage bizMessage)
    {
        bizMessage.setCreateTime(DateUtils.getNowDate());
        return bizMessageMapper.insertBizMessage(bizMessage);
    }

    /**
     * 修改消息
     * 
     * @param bizMessage 消息
     * @return 结果
     */
    @Override
    public int updateBizMessage(BizMessage bizMessage)
    {
        return bizMessageMapper.updateBizMessage(bizMessage);
    }

    /**
     * 批量删除消息
     * 
     * @param msgIds 需要删除的消息主键
     * @return 结果
     */
    @Override
    public int deleteBizMessageByMsgIds(Long[] msgIds)
    {
        return bizMessageMapper.deleteBizMessageByMsgIds(msgIds);
    }

    /**
     * 删除消息信息
     * 
     * @param msgId 消息主键
     * @return 结果
     */
    @Override
    public int deleteBizMessageByMsgId(Long msgId)
    {
        return bizMessageMapper.deleteBizMessageByMsgId(msgId);
    }
}
