package com.eduswap.biz.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eduswap.common.annotation.Log;
import com.eduswap.common.core.controller.BaseController;
import com.eduswap.common.core.domain.AjaxResult;
import com.eduswap.common.enums.BusinessType;
import com.eduswap.biz.domain.BizAddress;
import com.eduswap.biz.service.IBizAddressService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 收货地址Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/address")
public class BizAddressController extends BaseController
{
    @Autowired
    private IBizAddressService bizAddressService;

    /**
     * 查询收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('biz:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizAddress bizAddress)
    {
        startPage();
        List<BizAddress> list = bizAddressService.selectBizAddressList(bizAddress);
        return getDataTable(list);
    }

    /**
     * 导出收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('biz:address:export')")
    @Log(title = "收货地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizAddress bizAddress)
    {
        List<BizAddress> list = bizAddressService.selectBizAddressList(bizAddress);
        ExcelUtil<BizAddress> util = new ExcelUtil<BizAddress>(BizAddress.class);
        util.exportExcel(response, list, "收货地址数据");
    }

    /**
     * 获取收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:address:query')")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId)
    {
        return success(bizAddressService.selectBizAddressByAddressId(addressId));
    }

    /**
     * 新增收货地址
     */
    @PreAuthorize("@ss.hasPermi('biz:address:add')")
    @Log(title = "收货地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizAddress bizAddress)
    {
        return toAjax(bizAddressService.insertBizAddress(bizAddress));
    }

    /**
     * 修改收货地址
     */
    @PreAuthorize("@ss.hasPermi('biz:address:edit')")
    @Log(title = "收货地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizAddress bizAddress)
    {
        return toAjax(bizAddressService.updateBizAddress(bizAddress));
    }

    /**
     * 删除收货地址
     */
    @PreAuthorize("@ss.hasPermi('biz:address:remove')")
    @Log(title = "收货地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds)
    {
        return toAjax(bizAddressService.deleteBizAddressByAddressIds(addressIds));
    }
}
