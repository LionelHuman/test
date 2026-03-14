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
import com.eduswap.biz.domain.BizCollection;
import com.eduswap.biz.service.IBizCollectionService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 收藏Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/collection")
public class BizCollectionController extends BaseController
{
    @Autowired
    private IBizCollectionService bizCollectionService;

    /**
     * 查询收藏列表
     */
    @PreAuthorize("@ss.hasPermi('biz:collection:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizCollection bizCollection)
    {
        startPage();
        List<BizCollection> list = bizCollectionService.selectBizCollectionList(bizCollection);
        return getDataTable(list);
    }

    /**
     * 导出收藏列表
     */
    @PreAuthorize("@ss.hasPermi('biz:collection:export')")
    @Log(title = "收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizCollection bizCollection)
    {
        List<BizCollection> list = bizCollectionService.selectBizCollectionList(bizCollection);
        ExcelUtil<BizCollection> util = new ExcelUtil<BizCollection>(BizCollection.class);
        util.exportExcel(response, list, "收藏数据");
    }

    /**
     * 获取收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:collection:query')")
    @GetMapping(value = "/{collectId}")
    public AjaxResult getInfo(@PathVariable("collectId") Long collectId)
    {
        return success(bizCollectionService.selectBizCollectionByCollectId(collectId));
    }

    /**
     * 新增收藏
     */
    @PreAuthorize("@ss.hasPermi('biz:collection:add')")
    @Log(title = "收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizCollection bizCollection)
    {
        return toAjax(bizCollectionService.insertBizCollection(bizCollection));
    }

    /**
     * 修改收藏
     */
    @PreAuthorize("@ss.hasPermi('biz:collection:edit')")
    @Log(title = "收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizCollection bizCollection)
    {
        return toAjax(bizCollectionService.updateBizCollection(bizCollection));
    }

    /**
     * 删除收藏
     */
    @PreAuthorize("@ss.hasPermi('biz:collection:remove')")
    @Log(title = "收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{collectIds}")
    public AjaxResult remove(@PathVariable Long[] collectIds)
    {
        return toAjax(bizCollectionService.deleteBizCollectionByCollectIds(collectIds));
    }
}
