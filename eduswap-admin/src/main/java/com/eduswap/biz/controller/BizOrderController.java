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
import com.eduswap.biz.domain.BizOrder;
import com.eduswap.biz.service.IBizOrderService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 订单Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/order")
public class BizOrderController extends BaseController
{
    @Autowired
    private IBizOrderService bizOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('biz:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizOrder bizOrder)
    {
        startPage();
        List<BizOrder> list = bizOrderService.selectBizOrderList(bizOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('biz:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizOrder bizOrder)
    {
        List<BizOrder> list = bizOrderService.selectBizOrderList(bizOrder);
        ExcelUtil<BizOrder> util = new ExcelUtil<BizOrder>(BizOrder.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(bizOrderService.selectBizOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('biz:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizOrder bizOrder)
    {
        return toAjax(bizOrderService.insertBizOrder(bizOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('biz:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizOrder bizOrder)
    {
        return toAjax(bizOrderService.updateBizOrder(bizOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('biz:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(bizOrderService.deleteBizOrderByOrderIds(orderIds));
    }
}
