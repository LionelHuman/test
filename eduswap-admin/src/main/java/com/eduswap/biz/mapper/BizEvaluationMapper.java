package com.eduswap.biz.mapper;

import java.util.List;
import com.eduswap.biz.domain.BizEvaluation;

/**
 * 评价Mapper接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface BizEvaluationMapper 
{
    /**
     * 查询评价
     * 
     * @param evalId 评价主键
     * @return 评价
     */
    public BizEvaluation selectBizEvaluationByEvalId(Long evalId);

    /**
     * 查询评价列表
     * 
     * @param bizEvaluation 评价
     * @return 评价集合
     */
    public List<BizEvaluation> selectBizEvaluationList(BizEvaluation bizEvaluation);

    /**
     * 新增评价
     * 
     * @param bizEvaluation 评价
     * @return 结果
     */
    public int insertBizEvaluation(BizEvaluation bizEvaluation);

    /**
     * 修改评价
     * 
     * @param bizEvaluation 评价
     * @return 结果
     */
    public int updateBizEvaluation(BizEvaluation bizEvaluation);

    /**
     * 删除评价
     * 
     * @param evalId 评价主键
     * @return 结果
     */
    public int deleteBizEvaluationByEvalId(Long evalId);

    /**
     * 批量删除评价
     * 
     * @param evalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizEvaluationByEvalIds(Long[] evalIds);
}
