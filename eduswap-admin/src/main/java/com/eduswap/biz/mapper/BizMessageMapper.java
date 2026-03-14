package com.eduswap.biz.mapper;

import java.util.List;
import com.eduswap.biz.domain.BizMessage;

/**
 * 消息Mapper接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface BizMessageMapper 
{
    /**
     * 查询消息
     * 
     * @param msgId 消息主键
     * @return 消息
     */
    public BizMessage selectBizMessageByMsgId(Long msgId);

    /**
     * 查询消息列表
     * 
     * @param bizMessage 消息
     * @return 消息集合
     */
    public List<BizMessage> selectBizMessageList(BizMessage bizMessage);

    /**
     * 新增消息
     * 
     * @param bizMessage 消息
     * @return 结果
     */
    public int insertBizMessage(BizMessage bizMessage);

    /**
     * 修改消息
     * 
     * @param bizMessage 消息
     * @return 结果
     */
    public int updateBizMessage(BizMessage bizMessage);

    /**
     * 删除消息
     * 
     * @param msgId 消息主键
     * @return 结果
     */
    public int deleteBizMessageByMsgId(Long msgId);

    /**
     * 批量删除消息
     * 
     * @param msgIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizMessageByMsgIds(Long[] msgIds);
}
