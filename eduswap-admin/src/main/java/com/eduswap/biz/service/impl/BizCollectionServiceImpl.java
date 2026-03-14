package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizCollectionMapper;
import com.eduswap.biz.domain.BizCollection;
import com.eduswap.biz.service.IBizCollectionService;

/**
 * 收藏Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizCollectionServiceImpl implements IBizCollectionService 
{
    @Autowired
    private BizCollectionMapper bizCollectionMapper;

    /**
     * 查询收藏
     * 
     * @param collectId 收藏主键
     * @return 收藏
     */
    @Override
    public BizCollection selectBizCollectionByCollectId(Long collectId)
    {
        return bizCollectionMapper.selectBizCollectionByCollectId(collectId);
    }

    /**
     * 查询收藏列表
     * 
     * @param bizCollection 收藏
     * @return 收藏
     */
    @Override
    public List<BizCollection> selectBizCollectionList(BizCollection bizCollection)
    {
        return bizCollectionMapper.selectBizCollectionList(bizCollection);
    }

    /**
     * 新增收藏
     * 
     * @param bizCollection 收藏
     * @return 结果
     */
    @Override
    public int insertBizCollection(BizCollection bizCollection)
    {
        bizCollection.setCreateTime(DateUtils.getNowDate());
        return bizCollectionMapper.insertBizCollection(bizCollection);
    }

    /**
     * 修改收藏
     * 
     * @param bizCollection 收藏
     * @return 结果
     */
    @Override
    public int updateBizCollection(BizCollection bizCollection)
    {
        return bizCollectionMapper.updateBizCollection(bizCollection);
    }

    /**
     * 批量删除收藏
     * 
     * @param collectIds 需要删除的收藏主键
     * @return 结果
     */
    @Override
    public int deleteBizCollectionByCollectIds(Long[] collectIds)
    {
        return bizCollectionMapper.deleteBizCollectionByCollectIds(collectIds);
    }

    /**
     * 删除收藏信息
     * 
     * @param collectId 收藏主键
     * @return 结果
     */
    @Override
    public int deleteBizCollectionByCollectId(Long collectId)
    {
        return bizCollectionMapper.deleteBizCollectionByCollectId(collectId);
    }
}
