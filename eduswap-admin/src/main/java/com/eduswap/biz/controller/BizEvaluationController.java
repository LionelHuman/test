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
import com.eduswap.biz.domain.BizEvaluation;
import com.eduswap.biz.service.IBizEvaluationService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 评价Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/evaluation")
public class BizEvaluationController extends BaseController
{
    @Autowired
    private IBizEvaluationService bizEvaluationService;

    /**
     * 查询评价列表
     */
    @PreAuthorize("@ss.hasPermi('biz:evaluation:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizEvaluation bizEvaluation)
    {
        startPage();
        List<BizEvaluation> list = bizEvaluationService.selectBizEvaluationList(bizEvaluation);
        return getDataTable(list);
    }

    /**
     * 导出评价列表
     */
    @PreAuthorize("@ss.hasPermi('biz:evaluation:export')")
    @Log(title = "评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizEvaluation bizEvaluation)
    {
        List<BizEvaluation> list = bizEvaluationService.selectBizEvaluationList(bizEvaluation);
        ExcelUtil<BizEvaluation> util = new ExcelUtil<BizEvaluation>(BizEvaluation.class);
        util.exportExcel(response, list, "评价数据");
    }

    /**
     * 获取评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:evaluation:query')")
    @GetMapping(value = "/{evalId}")
    public AjaxResult getInfo(@PathVariable("evalId") Long evalId)
    {
        return success(bizEvaluationService.selectBizEvaluationByEvalId(evalId));
    }

    /**
     * 新增评价
     */
    @PreAuthorize("@ss.hasPermi('biz:evaluation:add')")
    @Log(title = "评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizEvaluation bizEvaluation)
    {
        return toAjax(bizEvaluationService.insertBizEvaluation(bizEvaluation));
    }

    /**
     * 修改评价
     */
    @PreAuthorize("@ss.hasPermi('biz:evaluation:edit')")
    @Log(title = "评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizEvaluation bizEvaluation)
    {
        return toAjax(bizEvaluationService.updateBizEvaluation(bizEvaluation));
    }

    /**
     * 删除评价
     */
    @PreAuthorize("@ss.hasPermi('biz:evaluation:remove')")
    @Log(title = "评价", businessType = BusinessType.DELETE)
	@DeleteMapping("/{evalIds}")
    public AjaxResult remove(@PathVariable Long[] evalIds)
    {
        return toAjax(bizEvaluationService.deleteBizEvaluationByEvalIds(evalIds));
    }
}
