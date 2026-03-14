<template>
  <div class="home-container">
    <!-- 页面标题 -->
    <el-card shadow="never" class="page-header">
      <div class="title">系统首页</div>
    </el-card>

    <!-- 统计卡片区域 -->
    <el-row :gutter="20" class="stat-card-row">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <span class="label">今日访问量</span>
            <span class="value">{{ visitCount }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <span class="label">用户总数</span>
            <span class="value">{{ userCount }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <span class="label">订单总数</span>
            <span class="value">{{ orderCount }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <span class="label">待处理任务</span>
            <span class="value">{{ taskCount }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- ECharts图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :span="12">
        <el-card class="chart-card">
          <div slot="header" class="chart-header">
            <span>近7日访问趋势</span>
          </div>
          <div id="visitChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <div slot="header" class="chart-header">
            <span>用户类型分布</span>
          </div>
          <div id="userTypeChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近操作列表 -->
    <el-card class="list-card">
      <div slot="header" class="list-header">
        <span>最近操作记录</span>
      </div>
      <el-table :data="operationList" border stripe>
        <el-table-column prop="operateTime" label="操作时间" width="180" />
        <el-table-column prop="userName" label="操作人" width="100" />
        <el-table-column prop="operateContent" label="操作内容" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
// 引入ECharts（若依已全局配置，也可按需引入）
import * as echarts from 'echarts'
// 引入若依封装的请求工具
import { request } from '@/utils/request'

export default {
  name: 'HomePage',
  data() {
    return {
      // 统计数据
      visitCount: 0,
      userCount: 0,
      orderCount: 0,
      taskCount: 0,
      // 最近操作列表
      operationList: [],
      // ECharts实例
      visitChart: null,
      userTypeChart: null
    }
  },
  mounted() {
    // 初始化数据
    this.loadHomeData()
    // 初始化图表
    this.initCharts()
    // 监听窗口大小，自适应图表
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    // 销毁图表，避免内存泄漏
    window.removeEventListener('resize', this.resizeCharts)
    if (this.visitChart) this.visitChart.dispose()
    if (this.userTypeChart) this.userTypeChart.dispose()
  },
  methods: {
    // 加载首页数据（对接后端接口）
    async loadHomeData() {
      try {
        // 调用后端首页统计接口（需后端提前开发）
        const res = await request({
          url: '/system/home/stat',
          method: 'get'
        })
        // 赋值统计数据
        this.visitCount = res.data.visitCount
        this.userCount = res.data.userCount
        this.orderCount = res.data.orderCount
        this.taskCount = res.data.taskCount
        // 赋值操作列表
        this.operationList = res.data.operationList
      } catch (error) {
        this.$message.error('首页数据加载失败：' + error.message)
      }
    },
    // 初始化ECharts图表
    initCharts() {
      // 近7日访问趋势图
      this.visitChart = echarts.init(document.getElementById('visitChart'))
      this.visitChart.setOption({
        tooltip: { trigger: 'axis' },
        xAxis: {
          type: 'category',
          data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        },
        yAxis: { type: 'value' },
        series: [{
          name: '访问量',
          type: 'line',
          data: [120, 200, 150, 80, 70, 110, 130], // 实际开发替换为接口数据
          smooth: true
        }]
      })

      // 用户类型分布图
      this.userTypeChart = echarts.init(document.getElementById('userTypeChart'))
      this.userTypeChart.setOption({
        tooltip: { trigger: 'item' },
        series: [{
          name: '用户类型',
          type: 'pie',
          radius: ['40%', '70%'],
          data: [
            { value: 335, name: '普通用户' },
            { value: 310, name: 'VIP用户' },
            { value: 234, name: '管理员' },
            { value: 135, name: '游客' }
          ], // 实际开发替换为接口数据
          label: {
            show: true,
            formatter: '{b}: {c} ({d}%)'
          }
        }]
      })
    },
    // 图表自适应
    resizeCharts() {
      this.visitChart?.resize()
      this.userTypeChart?.resize()
    }
  }
}
</script>

<style scoped>
.home-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 100px);
}
.page-header {
  margin-bottom: 20px;
}
.title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}
.stat-card-row {
  margin-bottom: 20px;
}
.stat-card {
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.stat-item .label {
  font-size: 14px;
  color: #666;
  margin-right: 10px;
}
.stat-item .value {
  font-size: 24px;
  font-weight: bold;
  color: #1989fa;
}
.chart-row {
  margin-bottom: 20px;
}
.chart-card {
  height: 300px;
}
.chart-container {
  width: 100%;
  height: calc(100% - 40px);
}
.list-card {
  margin-top: 20px;
}
</style>
