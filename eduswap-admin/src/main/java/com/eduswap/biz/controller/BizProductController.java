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
import com.eduswap.common.utils.SecurityUtils;
import com.eduswap.biz.domain.BizProduct;
import com.eduswap.biz.service.IBizProductService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 商品Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/product")
public class BizProductController extends BaseController
{
    @Autowired
    private IBizProductService bizProductService;

    /**
     * 查询商品列表（前台用户可用）
     */
    @GetMapping("/list")
    public TableDataInfo list(BizProduct bizProduct)
    {
        startPage();
        List<BizProduct> list = bizProductService.selectBizProductList(bizProduct);
        return getDataTable(list);
    }

    /**
     * 查询商品列表（后台管理用，需要权限）
     */
    @PreAuthorize("@ss.hasPermi('biz:product:list')")
    @GetMapping("/admin/list")
    public TableDataInfo adminList(BizProduct bizProduct)
    {
        startPage();
        List<BizProduct> list = bizProductService.selectBizProductList(bizProduct);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermi('biz:product:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizProduct bizProduct)
    {
        List<BizProduct> list = bizProductService.selectBizProductList(bizProduct);
        ExcelUtil<BizProduct> util = new ExcelUtil<BizProduct>(BizProduct.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 获取商品详细信息（公开访问，无需权限）
     */
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(bizProductService.selectBizProductByProductId(productId));
    }

    /**
     * 新增商品
     */
    /**
     * 发布商品（前台用户可用）
     */
    @Log(title = "发布商品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody BizProduct bizProduct)
    {
        // 自动设置当前登录用户 ID
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return error("请先登录");
        }
        bizProduct.setUserId(userId);
        return toAjax(bizProductService.insertBizProduct(bizProduct));
    }

    /**
     * 后台管理添加商品（需要权限）
     */
    @PreAuthorize("@ss.hasPermi('biz:product:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping("/admin/add")
    public AjaxResult adminAdd(@RequestBody BizProduct bizProduct)
    {
        return toAjax(bizProductService.insertBizProduct(bizProduct));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermi('biz:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProduct bizProduct)
    {
        return toAjax(bizProductService.updateBizProduct(bizProduct));
    }

    /**
     * 删除商品（前台用户删除自己的商品）
     */
    @Log(title = "删除商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productId}")
    public AjaxResult deleteProduct(@PathVariable("productId") Long productId)
    {
        // 获取当前登录用户 ID
        Long currentUserId = SecurityUtils.getUserId();
        if (currentUserId == null) {
            return error("请先登录");
        }
        
        // 查询商品信息
        BizProduct product = bizProductService.selectBizProductByProductId(productId);
        if (product == null) {
            return error("商品不存在");
        }
        
        // 验证是否为当前用户发布的商品
        if (!product.getUserId().equals(currentUserId)) {
            return error("只能删除自己发布的商品");
        }
        
        // 删除商品
        return toAjax(bizProductService.deleteBizProductByProductId(productId));
    }

    /**
     * 删除商品（后台管理用，需要权限）
     */
    @PreAuthorize("@ss.hasPermi('biz:product:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/admin/{productIds}")
    public AjaxResult adminRemove(@PathVariable Long[] productIds)
    {
        return toAjax(bizProductService.deleteBizProductByProductIds(productIds));
    }
}
