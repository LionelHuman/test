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
import com.eduswap.biz.domain.BizProductImage;
import com.eduswap.biz.service.IBizProductImageService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 商品图片Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/image")
public class BizProductImageController extends BaseController
{
    @Autowired
    private IBizProductImageService bizProductImageService;

    /**
     * 查询商品图片列表
     */
    @PreAuthorize("@ss.hasPermi('biz:image:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProductImage bizProductImage)
    {
        startPage();
        List<BizProductImage> list = bizProductImageService.selectBizProductImageList(bizProductImage);
        return getDataTable(list);
    }

    /**
     * 导出商品图片列表
     */
    @PreAuthorize("@ss.hasPermi('biz:image:export')")
    @Log(title = "商品图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizProductImage bizProductImage)
    {
        List<BizProductImage> list = bizProductImageService.selectBizProductImageList(bizProductImage);
        ExcelUtil<BizProductImage> util = new ExcelUtil<BizProductImage>(BizProductImage.class);
        util.exportExcel(response, list, "商品图片数据");
    }

    /**
     * 获取商品图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:image:query')")
    @GetMapping(value = "/{imageId}")
    public AjaxResult getInfo(@PathVariable("imageId") Long imageId)
    {
        return success(bizProductImageService.selectBizProductImageByImageId(imageId));
    }

    /**
     * 新增商品图片
     */
    @PreAuthorize("@ss.hasPermi('biz:image:add')")
    @Log(title = "商品图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProductImage bizProductImage)
    {
        return toAjax(bizProductImageService.insertBizProductImage(bizProductImage));
    }

    /**
     * 修改商品图片
     */
    @PreAuthorize("@ss.hasPermi('biz:image:edit')")
    @Log(title = "商品图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProductImage bizProductImage)
    {
        return toAjax(bizProductImageService.updateBizProductImage(bizProductImage));
    }

    /**
     * 删除商品图片
     */
    @PreAuthorize("@ss.hasPermi('biz:image:remove')")
    @Log(title = "商品图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{imageIds}")
    public AjaxResult remove(@PathVariable Long[] imageIds)
    {
        return toAjax(bizProductImageService.deleteBizProductImageByImageIds(imageIds));
    }
}
