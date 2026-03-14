package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizEvaluationMapper;
import com.eduswap.biz.domain.BizEvaluation;
import com.eduswap.biz.service.IBizEvaluationService;

/**
 * 评价Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizEvaluationServiceImpl implements IBizEvaluationService 
{
    @Autowired
    private BizEvaluationMapper bizEvaluationMapper;

    /**
     * 查询评价
     * 
     * @param evalId 评价主键
     * @return 评价
     */
    @Override
    public BizEvaluation selectBizEvaluationByEvalId(Long evalId)
    {
        return bizEvaluationMapper.selectBizEvaluationByEvalId(evalId);
    }

    /**
     * 查询评价列表
     * 
     * @param bizEvaluation 评价
     * @return 评价
     */
    @Override
    public List<BizEvaluation> selectBizEvaluationList(BizEvaluation bizEvaluation)
    {
        return bizEvaluationMapper.selectBizEvaluationList(bizEvaluation);
    }

    /**
     * 新增评价
     * 
     * @param bizEvaluation 评价
     * @return 结果
     */
    @Override
    public int insertBizEvaluation(BizEvaluation bizEvaluation)
    {
        bizEvaluation.setCreateTime(DateUtils.getNowDate());
        return bizEvaluationMapper.insertBizEvaluation(bizEvaluation);
    }

    /**
     * 修改评价
     * 
     * @param bizEvaluation 评价
     * @return 结果
     */
    @Override
    public int updateBizEvaluation(BizEvaluation bizEvaluation)
    {
        return bizEvaluationMapper.updateBizEvaluation(bizEvaluation);
    }

    /**
     * 批量删除评价
     * 
     * @param evalIds 需要删除的评价主键
     * @return 结果
     */
    @Override
    public int deleteBizEvaluationByEvalIds(Long[] evalIds)
    {
        return bizEvaluationMapper.deleteBizEvaluationByEvalIds(evalIds);
    }

    /**
     * 删除评价信息
     * 
     * @param evalId 评价主键
     * @return 结果
     */
    @Override
    public int deleteBizEvaluationByEvalId(Long evalId)
    {
        return bizEvaluationMapper.deleteBizEvaluationByEvalId(evalId);
    }
}
