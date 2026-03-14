package com.eduswap.biz.mapper;

import java.util.List;
import com.eduswap.biz.domain.BizCollection;

/**
 * 收藏Mapper接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface BizCollectionMapper 
{
    /**
     * 查询收藏
     * 
     * @param collectId 收藏主键
     * @return 收藏
     */
    public BizCollection selectBizCollectionByCollectId(Long collectId);

    /**
     * 查询收藏列表
     * 
     * @param bizCollection 收藏
     * @return 收藏集合
     */
    public List<BizCollection> selectBizCollectionList(BizCollection bizCollection);

    /**
     * 新增收藏
     * 
     * @param bizCollection 收藏
     * @return 结果
     */
    public int insertBizCollection(BizCollection bizCollection);

    /**
     * 修改收藏
     * 
     * @param bizCollection 收藏
     * @return 结果
     */
    public int updateBizCollection(BizCollection bizCollection);

    /**
     * 删除收藏
     * 
     * @param collectId 收藏主键
     * @return 结果
     */
    public int deleteBizCollectionByCollectId(Long collectId);

    /**
     * 批量删除收藏
     * 
     * @param collectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizCollectionByCollectIds(Long[] collectIds);
}
