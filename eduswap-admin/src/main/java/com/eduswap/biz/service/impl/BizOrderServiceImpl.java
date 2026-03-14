package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizOrderMapper;
import com.eduswap.biz.domain.BizOrder;
import com.eduswap.biz.service.IBizOrderService;

/**
 * 订单Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizOrderServiceImpl implements IBizOrderService 
{
    @Autowired
    private BizOrderMapper bizOrderMapper;

    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public BizOrder selectBizOrderByOrderId(Long orderId)
    {
        return bizOrderMapper.selectBizOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     * 
     * @param bizOrder 订单
     * @return 订单
     */
    @Override
    public List<BizOrder> selectBizOrderList(BizOrder bizOrder)
    {
        return bizOrderMapper.selectBizOrderList(bizOrder);
    }

    /**
     * 新增订单
     * 
     * @param bizOrder 订单
     * @return 结果
     */
    @Override
    public int insertBizOrder(BizOrder bizOrder)
    {
        bizOrder.setCreateTime(DateUtils.getNowDate());
        return bizOrderMapper.insertBizOrder(bizOrder);
    }

    /**
     * 修改订单
     * 
     * @param bizOrder 订单
     * @return 结果
     */
    @Override
    public int updateBizOrder(BizOrder bizOrder)
    {
        bizOrder.setUpdateTime(DateUtils.getNowDate());
        return bizOrderMapper.updateBizOrder(bizOrder);
    }

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteBizOrderByOrderIds(Long[] orderIds)
    {
        return bizOrderMapper.deleteBizOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteBizOrderByOrderId(Long orderId)
    {
        return bizOrderMapper.deleteBizOrderByOrderId(orderId);
    }
}
