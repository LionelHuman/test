<template>
  <div class="user-center-container">
    <div class="user-content">
      <el-row :gutter="20">
        <!-- 左侧菜单 -->
        <el-col :span="6">
          <el-card>
            <div class="user-profile">
              <div class="avatar">
                <i class="el-icon-user-solid"></i>
              </div>
              <h3>个人中心</h3>
            </div>
            <el-menu :default-active="activeMenu" @select="handleMenuSelect">
              <el-menu-item index="my-products">
                <i class="el-icon-shopping-bag-1"></i>
                <span>我的商品</span>
                <el-badge :value="productCount" :hidden="productCount === 0" slot="title" />
              </el-menu-item>
              <el-menu-item index="my-favorites">
                <i class="el-icon-star-on"></i>
                <span>我的收藏</span>
                <el-badge :value="favoritesCount" :hidden="favoritesCount === 0" slot="title" />
              </el-menu-item>
              <el-menu-item index="my-orders">
                <i class="el-icon-shopping-cart-2"></i>
                <span>我的订单</span>
                <el-badge :value="ordersCount" :hidden="ordersCount === 0" slot="title" />
              </el-menu-item>
              <el-menu-item index="my-messages">
                <i class="el-icon-chat-dot-round"></i>
                <span>我的消息</span>
                <el-badge :value="messagesCount" :hidden="messagesCount === 0" slot="title" />
              </el-menu-item>
            </el-menu>
          </el-card>
        </el-col>

        <!-- 右侧内容 -->
        <el-col :span="18">
          <el-card>
            <!-- 我的商品 -->
            <div v-if="activeMenu === 'my-products'">
              <div class="content-header" slot="header">
                <span>我的商品</span>
                <el-tag size="small" type="success">共 {{ productCount }} 件</el-tag>
              </div>
              <el-table :data="myProducts" v-loading="loading" stripe>
                <el-table-column prop="productName" label="商品名称" />
                <el-table-column prop="currentPrice" label="价格" width="100">
                  <template slot-scope="scope">¥{{ scope.row.currentPrice }}</template>
                </el-table-column>
                <el-table-column prop="status" label="状态" width="100">
                  <template slot-scope="scope">
                    <el-tag :type="getStatusType(scope.row.status)" size="small">
                      {{ getStatusText(scope.row.status) }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="viewCount" label="浏览量" width="100" />
                <el-table-column label="操作" width="150">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="editProduct(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="deleteProduct(scope.row)" style="color: #f56c6c;">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <!-- 分页 -->
              <el-pagination
                v-show="total > 0"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20, 50]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total"
                style="margin-top: 20px; text-align: right;"
              />
            </div>

            <!-- 我的收藏 -->
            <div v-if="activeMenu === 'my-favorites'">
              <div class="content-header" slot="header">
                <span>我的收藏</span>
                <el-tag size="small" type="success">共 {{ favoritesCount }} 件</el-tag>
              </div>
              <el-empty description="暂无收藏" v-if="favorites.length === 0" />
            </div>

            <!-- 我的订单 -->
            <div v-if="activeMenu === 'my-orders'">
              <div class="content-header" slot="header">
                <span>我的订单</span>
                <el-tag size="small" type="success">共 {{ ordersCount }} 件</el-tag>
              </div>
              <el-empty description="暂无订单" v-if="orders.length === 0" />
            </div>

            <!-- 我的消息 -->
            <div v-if="activeMenu === 'my-messages'">
              <div class="content-header" slot="header">
                <span>我的消息</span>
                <el-tag size="small" type="success">共 {{ messagesCount }} 条</el-tag>
              </div>
              <el-empty description="暂无消息" v-if="messages.length === 0" />
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { listProduct, delProduct } from '@/api/biz/product'

export default {
  name: 'UserCenter',
  data() {
    return {
      activeMenu: 'my-products',
      loading: true,
      myProducts: [],
      favorites: [],
      orders: [],
      messages: [],
      // 分页相关
      pageNum: 1,
      pageSize: 10,
      total: 0,
      // 统计数量
      productCount: 0,
      favoritesCount: 0,
      ordersCount: 0,
      messagesCount: 0
    }
  },
  created() {
    this.loadMyProducts()
  },
  methods: {
    // 加载我的商品
    async loadMyProducts() {
      this.loading = true
      try {
        // 获取当前登录用户 ID
        const userId = this.$store.state.user.id
        if (!userId) {
          this.$message.error('请先登录')
          this.$router.push('/login')
          return
        }
        // 查询当前用户的商品
        const res = await listProduct({ 
          pageNum: this.pageNum, 
          pageSize: this.pageSize,
          userId: userId  // 传递用户 ID 过滤
        })
        this.myProducts = res.rows || []
        this.total = res.total || 0
        this.productCount = this.total
      } catch (error) {
        this.$message.error('加载失败：' + (error.message || '未知错误'))
      } finally {
        this.loading = false
      }
    },
    // 分页处理
    handleSizeChange(val) {
      this.pageSize = val
      this.loadMyProducts()
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.loadMyProducts()
    },
    // 获取状态类型
    getStatusType(status) {
      const typeMap = {
        '0': 'warning', // 审核中
        '1': 'success', // 在售
        '2': 'info',    // 已售出
        '3': 'danger'   // 下架
      }
      return typeMap[status] || ''
    },
    // 获取状态文本
    getStatusText(status) {
      const textMap = {
        '0': '审核中',
        '1': '在售',
        '2': '已售出',
        '3': '下架'
      }
      return textMap[status] || '未知'
    },
    // 菜单选择
    handleMenuSelect(index) {
      this.activeMenu = index
    },
    // 编辑商品
    editProduct(product) {
      this.$message.info('编辑功能开发中')
    },
    // 删除商品
    deleteProduct(product) {
      this.$modal.confirm(`是否确认删除商品"${product.productName}"？`).then(() => {
        return delProduct(product.productId)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.loadMyProducts()
      }).catch(() => {})
    }
  }
}
</script>

<style scoped lang="scss">
.user-center-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.user-content {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}

.user-profile {
  text-align: center;
  padding: 20px 0;
  border-bottom: 1px solid #eee;
  margin-bottom: 15px;

  .avatar {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 15px;
    color: white;
    font-size: 36px;
  }

  h3 {
    margin: 0;
    font-size: 18px;
    color: #333;
  }
}

.el-menu {
  border-right: none;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  span {
    font-size: 16px;
    font-weight: 500;
  }
}
</style>
