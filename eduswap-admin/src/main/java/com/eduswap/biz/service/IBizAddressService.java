package com.eduswap.biz.service;

import java.util.List;
import com.eduswap.biz.domain.BizAddress;

/**
 * 收货地址Service接口
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
public interface IBizAddressService 
{
    /**
     * 查询收货地址
     * 
     * @param addressId 收货地址主键
     * @return 收货地址
     */
    public BizAddress selectBizAddressByAddressId(Long addressId);

    /**
     * 查询收货地址列表
     * 
     * @param bizAddress 收货地址
     * @return 收货地址集合
     */
    public List<BizAddress> selectBizAddressList(BizAddress bizAddress);

    /**
     * 新增收货地址
     * 
     * @param bizAddress 收货地址
     * @return 结果
     */
    public int insertBizAddress(BizAddress bizAddress);

    /**
     * 修改收货地址
     * 
     * @param bizAddress 收货地址
     * @return 结果
     */
    public int updateBizAddress(BizAddress bizAddress);

    /**
     * 批量删除收货地址
     * 
     * @param addressIds 需要删除的收货地址主键集合
     * @return 结果
     */
    public int deleteBizAddressByAddressIds(Long[] addressIds);

    /**
     * 删除收货地址信息
     * 
     * @param addressId 收货地址主键
     * @return 结果
     */
    public int deleteBizAddressByAddressId(Long addressId);
}
