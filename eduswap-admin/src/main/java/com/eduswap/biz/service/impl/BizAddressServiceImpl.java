package com.eduswap.biz.service.impl;

import java.util.List;
import com.eduswap.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eduswap.biz.mapper.BizAddressMapper;
import com.eduswap.biz.domain.BizAddress;
import com.eduswap.biz.service.IBizAddressService;

/**
 * 收货地址Service业务层处理
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@Service
public class BizAddressServiceImpl implements IBizAddressService 
{
    @Autowired
    private BizAddressMapper bizAddressMapper;

    /**
     * 查询收货地址
     * 
     * @param addressId 收货地址主键
     * @return 收货地址
     */
    @Override
    public BizAddress selectBizAddressByAddressId(Long addressId)
    {
        return bizAddressMapper.selectBizAddressByAddressId(addressId);
    }

    /**
     * 查询收货地址列表
     * 
     * @param bizAddress 收货地址
     * @return 收货地址
     */
    @Override
    public List<BizAddress> selectBizAddressList(BizAddress bizAddress)
    {
        return bizAddressMapper.selectBizAddressList(bizAddress);
    }

    /**
     * 新增收货地址
     * 
     * @param bizAddress 收货地址
     * @return 结果
     */
    @Override
    public int insertBizAddress(BizAddress bizAddress)
    {
        bizAddress.setCreateTime(DateUtils.getNowDate());
        return bizAddressMapper.insertBizAddress(bizAddress);
    }

    /**
     * 修改收货地址
     * 
     * @param bizAddress 收货地址
     * @return 结果
     */
    @Override
    public int updateBizAddress(BizAddress bizAddress)
    {
        bizAddress.setUpdateTime(DateUtils.getNowDate());
        return bizAddressMapper.updateBizAddress(bizAddress);
    }

    /**
     * 批量删除收货地址
     * 
     * @param addressIds 需要删除的收货地址主键
     * @return 结果
     */
    @Override
    public int deleteBizAddressByAddressIds(Long[] addressIds)
    {
        return bizAddressMapper.deleteBizAddressByAddressIds(addressIds);
    }

    /**
     * 删除收货地址信息
     * 
     * @param addressId 收货地址主键
     * @return 结果
     */
    @Override
    public int deleteBizAddressByAddressId(Long addressId)
    {
        return bizAddressMapper.deleteBizAddressByAddressId(addressId);
    }
}
