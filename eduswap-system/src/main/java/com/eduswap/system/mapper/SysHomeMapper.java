package com.eduswap.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 后台首页数据 Mapper 接口
 * 
 * @author eduswap
 */
@Mapper
public interface SysHomeMapper
{
    /**
     * 查询今日访问次数（从 sys_oper_log 表查询今日登录次数）
     */
    @Select("SELECT COUNT(*) FROM sys_oper_log WHERE DATE(operate_time) = CURDATE()")
    int selectTodayVisitCount();

    /**
     * 查询近 7 日访问数据（按日期统计）
     */
    @Select("SELECT DATE(operate_time) as visit_date, COUNT(*) as visit_count " +
            "FROM sys_oper_log " +
            "WHERE operate_time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
            "GROUP BY DATE(operate_time) ORDER BY visit_date")
    List<Map<String, Object>> selectLast7DaysVisit();

    /**
     * 查询最近操作记录
     */
    @Select("SELECT operate_time, oper_name as userName, title as operateContent " +
            "FROM sys_oper_log " +
            "ORDER BY operate_time DESC LIMIT #{limit}")
    List<Map<String, Object>> selectRecentOperations(int limit);

    /**
     * 查询待处理订单数量（从 biz_order 表查询）
     */
    @Select("SELECT COUNT(*) FROM biz_order WHERE order_status IN ('0', '1') AND del_flag = '0'")
    int selectPendingTaskCount();

    /**
     * 查询本月收入（从 biz_order 表统计本月已支付订单金额）
     */
    @Select("SELECT IFNULL(SUM(order_amount), 0) FROM biz_order " +
            "WHERE order_status = '2' AND del_flag = '0' " +
            "AND DATE_FORMAT(create_time, '%Y%m') = DATE_FORMAT(CURDATE(), '%Y%m')")
    BigDecimal selectMonthIncome();
}
