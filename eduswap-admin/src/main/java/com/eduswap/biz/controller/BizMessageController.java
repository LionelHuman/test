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
import com.eduswap.biz.domain.BizMessage;
import com.eduswap.biz.service.IBizMessageService;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.common.core.page.TableDataInfo;

/**
 * 消息Controller
 * 
 * @author LionelHuman
 * @date 2026-03-10
 */
@RestController
@RequestMapping("/biz/message")
public class BizMessageController extends BaseController
{
    @Autowired
    private IBizMessageService bizMessageService;

    /**
     * 查询消息列表
     */
    @PreAuthorize("@ss.hasPermi('biz:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizMessage bizMessage)
    {
        startPage();
        List<BizMessage> list = bizMessageService.selectBizMessageList(bizMessage);
        return getDataTable(list);
    }

    /**
     * 导出消息列表
     */
    @PreAuthorize("@ss.hasPermi('biz:message:export')")
    @Log(title = "消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizMessage bizMessage)
    {
        List<BizMessage> list = bizMessageService.selectBizMessageList(bizMessage);
        ExcelUtil<BizMessage> util = new ExcelUtil<BizMessage>(BizMessage.class);
        util.exportExcel(response, list, "消息数据");
    }

    /**
     * 获取消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('biz:message:query')")
    @GetMapping(value = "/{msgId}")
    public AjaxResult getInfo(@PathVariable("msgId") Long msgId)
    {
        return success(bizMessageService.selectBizMessageByMsgId(msgId));
    }

    /**
     * 新增消息
     */
    @PreAuthorize("@ss.hasPermi('biz:message:add')")
    @Log(title = "消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizMessage bizMessage)
    {
        return toAjax(bizMessageService.insertBizMessage(bizMessage));
    }

    /**
     * 修改消息
     */
    @PreAuthorize("@ss.hasPermi('biz:message:edit')")
    @Log(title = "消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizMessage bizMessage)
    {
        return toAjax(bizMessageService.updateBizMessage(bizMessage));
    }

    /**
     * 删除消息
     */
    @PreAuthorize("@ss.hasPermi('biz:message:remove')")
    @Log(title = "消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{msgIds}")
    public AjaxResult remove(@PathVariable Long[] msgIds)
    {
        return toAjax(bizMessageService.deleteBizMessageByMsgIds(msgIds));
    }
}
