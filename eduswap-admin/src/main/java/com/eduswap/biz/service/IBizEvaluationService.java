package com.eduswap.biz.service;

import java.util.List;
import com.eduswap.biz.domain.BizEvaluation;

/**
 * 评价Service接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface IBizEvaluationService 
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
     * 批量删除评价
     * 
     * @param evalIds 需要删除的评价主键集合
     * @return 结果
     */
    public int deleteBizEvaluationByEvalIds(Long[] evalIds);

    /**
     * 删除评价信息
     * 
     * @param evalId 评价主键
     * @return 结果
     */
    public int deleteBizEvaluationByEvalId(Long evalId);
}
