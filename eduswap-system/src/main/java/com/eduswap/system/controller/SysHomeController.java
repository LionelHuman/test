package com.eduswap.system.controller;

import com.eduswap.common.annotation.Log;
import com.eduswap.common.constant.UserConstants;
import com.eduswap.common.core.controller.BaseController;
import com.eduswap.common.core.domain.AjaxResult;
import com.eduswap.common.core.domain.entity.SysUser;
import com.eduswap.common.core.page.TableDataInfo;
import com.eduswap.common.enums.BusinessType;
import com.eduswap.common.utils.poi.ExcelUtil;
import com.eduswap.system.mapper.SysHomeMapper;
import com.eduswap.system.service.ISysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

/**
 * 后台首页控制器
 * 
 * @author eduswap
 */
@RestController
@RequestMapping("/system/home")
public class SysHomeController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysHomeMapper sysHomeMapper;

    /**
     * 获取后台首页统计数据
     */
    @PreAuthorize("@ss.hasPermi('system:home:query')")
    @GetMapping("/stat")
    public AjaxResult getHomeStat()
    {
        // 用户总数
        PageHelper.startPage(1, 1);
        List<SysUser> list = userService.selectUserList(new SysUser());
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        long userCount = pageInfo.getTotal();

        // 今日访问数（从系统日志表查询今日登录次数）
        int visitCount = queryTodayVisitCount();

        // 待处理任务（查询待处理的订单数量）
        int taskCount = queryPendingTaskCount();

        // 本月收入（从订单表统计本月已支付订单金额）
        BigDecimal monthIncome = queryMonthIncome();

        // 最近操作记录（从日志表查询最近 5 条）
        List<Map<String, Object>> operationList = queryRecentOperations(5);

        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userCount);
        data.put("visitCount", visitCount);
        data.put("taskCount", taskCount);
        data.put("monthIncome", monthIncome);
        data.put("operationList", operationList);

        return AjaxResult.success(data);
    }

    /**
     * 获取近 7 日访问趋势数据
     */
    @PreAuthorize("@ss.hasPermi('system:home:query')")
    @GetMapping("/visit/trend")
    public AjaxResult getVisitTrend()
    {
        // 查询近 7 日访问数据（从系统日志表按日期统计）
        List<Map<String, Object>> visitData = queryLast7DaysVisit();

        Map<String, Object> data = new HashMap<>();
        data.put("dates", Arrays.asList("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        data.put("visitData", visitData);

        return AjaxResult.success(data);
    }

    /**
     * 获取用户类型分布数据
     */
    @PreAuthorize("@ss.hasPermi('system:home:query')")
    @GetMapping("/user/type")
    public AjaxResult getUserTypeDistribution()
    {
        // 统计不同角色的用户数量
        SysUser user = new SysUser();
        PageHelper.startPage(1, 1000);
        List<SysUser> userList = userService.selectUserList(user);
        
        Map<String, Integer> typeCount = new HashMap<>();
        for (SysUser sysUser : userList) {
            String role = sysUser.getRoleId() != null ? sysUser.getRoleId().toString() : "0";
            typeCount.put(role, typeCount.getOrDefault(role, 0) + 1);
        }

        // 转换为 ECharts 需要的格式
        List<Map<String, Object>> pieData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            Map<String, Object> item = new HashMap<>();
            item.put("value", entry.getValue());
            item.put("name", "角色" + entry.getKey());
            pieData.add(item);
        }

        return AjaxResult.success(pieData);
    }

    // ==================== 以下方法用于查询真实数据 ====================

    /**
     * 查询今日访问次数（从 sys_oper_log 表查询今日登录次数）
     */
    private int queryTodayVisitCount() {
        // 从 sys_oper_log 表查询今日登录次数
        try {
            return sysHomeMapper.selectTodayVisitCount();
        } catch (Exception e) {
            // 如果表不存在或查询失败，返回 0
            return 0;
        }
    }

    /**
     * 查询待处理任务数量（从 biz_order 表查询待处理订单）
     */
    private int queryPendingTaskCount() {
        // 从 biz_order 表查询待处理订单数量
        try {
            return sysHomeMapper.selectPendingTaskCount();
        } catch (Exception e) {
            // 如果表不存在或查询失败，返回 0
            return 0;
        }
    }

    /**
     * 查询本月收入（从 biz_order 表统计本月已支付订单金额）
     */
    private BigDecimal queryMonthIncome() {
        // 从 biz_order 表统计本月已支付订单金额
        try {
            return sysHomeMapper.selectMonthIncome();
        } catch (Exception e) {
            // 如果表不存在或查询失败，返回 0
            return BigDecimal.ZERO;
        }
    }

    /**
     * 查询最近操作记录（从 sys_oper_log 表查询）
     */
    private List<Map<String, Object>> queryRecentOperations(int limit) {
        // 从 sys_oper_log 表查询最近操作记录
        try {
            return sysHomeMapper.selectRecentOperations(limit);
        } catch (Exception e) {
            // 如果表不存在或查询失败，返回空列表
            return new ArrayList<>();
        }
    }

    /**
     * 查询近 7 日访问数据（从 sys_oper_log 表按日期统计）
     */
    private List<Map<String, Object>> queryLast7DaysVisit() {
        // 从 sys_oper_log 表按日期统计近 7 日访问数据
        try {
            return sysHomeMapper.selectLast7DaysVisit();
        } catch (Exception e) {
            // 如果表不存在或查询失败，返回空列表
            return new ArrayList<>();
        }
    }
}
