<template>
  <div class="app-container home">
    <!-- 顶部统计卡片区域 -->
    <el-row :gutter="20" class="stat-card-row">
      <el-col :xs="6" :sm="6" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <i class="el-icon-user-solid stat-icon"></i>
            <div class="stat-content">
              <p class="stat-label">用户总数</p>
              <p class="stat-value">{{ userCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="6" :sm="6" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <i class="el-icon-s-data stat-icon"></i>
            <div class="stat-content">
              <p class="stat-label">今日访问</p>
              <p class="stat-value">{{ visitCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="6" :sm="6" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <i class="el-icon-s-order stat-icon"></i>
            <div class="stat-content">
              <p class="stat-label">待处理任务</p>
              <p class="stat-value">{{ taskCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="6" :sm="6" :md="6" :lg="6">
        <el-card class="stat-card">
          <div class="stat-item">
            <i class="el-icon-s-finance stat-icon"></i>
            <div class="stat-content">
              <p class="stat-label">本月收入</p>
              <p class="stat-value">{{ monthIncome }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row" style="margin-top: 20px">
      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-card>
          <div slot="header" class="chart-header">
            <span>近7日访问趋势</span>
          </div>
          <div id="visitChart" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12">
        <el-card>
          <div slot="header" class="chart-header">
            <span>用户类型分布</span>
          </div>
          <div id="userTypeChart" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 原有信息区域（简化版） -->
    <el-divider style="margin-top: 20px" />
    <el-row :gutter="20" style="margin-top: 10px">
      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="info-card">
          <div slot="header" class="clearfix">
            <span>系统信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-monitor"></i> 系统版本：<span>{{ systemInfo.osName }}</span>
            </p>
            <p>
              <i class="el-icon-time"></i> 当前时间：<span>{{ systemInfo.currentTime }}</span>
            </p>
            <p>
              <i class="el-icon-connection"></i> IP 地址：<span>{{ systemInfo.ipAddress }}</span>
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="info-card">
          <div slot="header" class="clearfix">
            <span>最近操作记录</span>
          </div>
          <el-table :data="operationList" border stripe size="mini">
            <el-table-column prop="time" label="操作时间" width="160" />
            <el-table-column prop="user" label="操作人" width="80" />
            <el-table-column prop="content" label="操作内容" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import request from '@/utils/request'

export default {
  name: "Index",
  data() {
    return {
      // 版本号（保持和原代码一致）
      version: "3.9.1",
      // 统计数据
      userCount: 0,
      visitCount: 0,
      taskCount: 0,
      monthIncome: '¥0',
      // 系统信息
      systemInfo: {
        osName: 'Mac OS',
        currentTime: this.formatDate(new Date()),
        ipAddress: '127.0.0.1'
      },
      // 最近操作记录
      operationList: [],
      // ECharts 实例
      visitChart: null,
      userTypeChart: null
    }
  },
  mounted() {
    // 加载首页数据
    this.loadHomeData()
    // 初始化图表
    this.initCharts()
    // 监听窗口大小，图表自适应
    window.addEventListener('resize', this.resizeCharts)
  },
  beforeDestroy() {
    // 销毁图表，防止内存泄漏
    window.removeEventListener('resize', this.resizeCharts)
    if (this.visitChart) this.visitChart.dispose()
    if (this.userTypeChart) this.userTypeChart.dispose()
  },
  methods: {
    // 加载首页数据
    async loadHomeData() {
      try {
        // 调用后端首页统计接口
        const response = await request({
          url: '/system/home/stat',
          method: 'get'
        })
        if (response.code === 200 && response.data) {
          const data = response.data
          // 赋值统计数据
          this.userCount = data.userCount || 0
          this.visitCount = data.visitCount || 0
          this.taskCount = data.taskCount || 0
          this.monthIncome = '¥' + (data.monthIncome || 0).toLocaleString()
          // 赋值操作列表
          this.operationList = data.operationList || []
        }
      } catch (error) {
        console.error('首页数据加载失败:', error)
        this.$message.warning('首页数据加载失败，请稍后重试')
      }
    },
    // 格式化日期时间
    formatDate(date) {
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      const seconds = String(date.getSeconds()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    },
    // 跳转外部链接（保留原方法）
    goTarget(href) {
      window.open(href, "_blank")
    },
    // 初始化图表
    async initCharts() {
      // 加载近 7 日访问趋势数据
      await this.loadVisitTrend()
      // 加载用户类型分布数据
      await this.loadUserTypeDistribution()
    },
    // 加载近 7 日访问趋势
    async loadVisitTrend() {
      try {
        const response = await request({
          url: '/system/home/visit/trend',
          method: 'get'
        })
        if (response.code === 200 && response.data) {
          const data = response.data
          this.visitChart = echarts.init(document.getElementById('visitChart'))
          this.visitChart.setOption({
            tooltip: { trigger: 'axis' },
            grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
            xAxis: {
              type: 'category',
              data: data.dates || ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            },
            yAxis: { type: 'value' },
            series: [{
              name: '访问量',
              type: 'line',
              data: data.visitData || [120, 200, 150, 80, 70, 110, 130],
              smooth: true,
              itemStyle: { color: '#409EFF' },
              areaStyle: { color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(64, 158, 255, 0.3)'
                }, {
                  offset: 1,
                  color: 'rgba(64, 158, 255, 0.0)'
                }]) }
            }]
          })
        }
      } catch (error) {
        console.error('访问趋势数据加载失败:', error)
      }
    },
    // 加载用户类型分布
    async loadUserTypeDistribution() {
      try {
        const response = await request({
          url: '/system/home/user/type',
          method: 'get'
        })
        if (response.code === 200 && response.data) {
          const data = response.data
          this.userTypeChart = echarts.init(document.getElementById('userTypeChart'))
          this.userTypeChart.setOption({
            tooltip: { trigger: 'item' },
            series: [{
              name: '用户类型',
              type: 'pie',
              radius: ['40%', '70%'],
              data: data || [
                { value: 335, name: '普通用户' },
                { value: 310, name: 'VIP 用户' },
                { value: 234, name: '管理员' },
                { value: 135, name: '游客' }
              ],
              label: {
                show: true,
                formatter: '{b}: {c} ({d}%)'
              }
            }]
          })
        }
      } catch (error) {
        console.error('用户类型分布数据加载失败:', error)
      }
    },
    // 图表自适应
    resizeCharts() {
      this.visitChart?.resize()
      this.userTypeChart?.resize()
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  padding: 20px;
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;

  // 统计卡片样式
  .stat-card-row {
    .stat-card {
      height: 120px;
      overflow: hidden;
      .stat-item {
        display: flex;
        align-items: center;
        height: 100%;
        padding: 0 20px;
        .stat-icon {
          font-size: 36px;
          color: #409EFF;
          margin-right: 20px;
        }
        .stat-content {
          .stat-label {
            font-size: 14px;
            color: #999;
            margin: 0;
          }
          .stat-value {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin: 5px 0 0 0;
          }
        }
      }
    }
  }

  // 图表样式
  .chart-row {
    .chart-header {
      font-weight: bold;
    }
    .chart-container {
      width: 100%;
      height: 280px;
    }
  }

  // 信息卡片样式
  .info-card {
    .body {
      padding: 10px 0;
      p {
        margin: 5px 0;
        line-height: 24px;
      }
    }
    .el-table {
      --el-table-header-text-color: #666;
      --el-table-row-hover-bg-color: #f5f7fa;
    }
  }
}
</style>
