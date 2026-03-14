<template>
  <div class="home-container">
    <!-- 分类导航 -->
    <div class="category-nav">
      <div class="category-content">
        <div 
          class="category-item" 
          :class="{ active: currentCategory === null }"
          @click="selectCategory(null)"
        >
          全部
        </div>
        <div 
          v-for="cat in productClassDict" 
          :key="cat.value"
          class="category-item"
          :class="{ active: currentCategory === cat.value }"
          @click="selectCategory(cat.value)"
        >
          {{ cat.label }}
        </div>
      </div>
    </div>

    <!-- 商品列表 -->
    <div class="product-list-container">
      <div class="product-list">
        <div 
          v-for="product in productList" 
          :key="product.productId"
          class="product-card"
          @click="goToDetail(product.productId)"
        >
          <div class="product-image">
            <img :src="getProductImage(product.productPics)" alt="商品图片" />
          </div>
          <div class="product-info">
            <h3 class="product-title">{{ product.productName }}</h3>
            <p class="product-desc">{{ product.productDesc }}</p>
            <div class="product-meta">
              <span class="price">¥{{ product.currentPrice }}</span>
              <span class="original-price" v-if="product.originalPrice">¥{{ product.originalPrice }}</span>
            </div>
            <div class="product-footer">
              <span class="grade">{{ getProductGrade(product.productGrade) }}</span>
              <span class="views"><i class="el-icon-view"></i> {{ product.viewCount }}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 分页 -->
      <div class="pagination" v-if="total > 0">
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          :page-size="pageSize"
          :current-page="pageNum"
          @current-change="handlePageChange"
        />
      </div>
    </div>

    <!-- 空状态 -->
    <el-empty 
      v-if="productList.length === 0 && !loading"
      description="暂无商品"
    />
  </div>
</template>

<script>
import { listProduct } from '@/api/biz/product'

export default {
  name: 'FrontHome',
  dicts: ['product_class'],
  data() {
    return {
      loading: true,
      searchKeyword: '',
      currentCategory: null,
      pageNum: 1,
      pageSize: 12,
      total: 0,
      productList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 12
        // 不过滤状态，显示所有商品（包括审核中、在售、已售出）
      }
    }
  },
  created() {
    this.loadProducts()
  },
  computed: {
    productClassDict() {
      return this.dict.type.product_class || []
    }
  },
  methods: {
    // 加载商品列表
    async loadProducts() {
      this.loading = true
      try {
        const params = {
          ...this.queryParams,
          pageNum: this.pageNum,
          pageSize: this.pageSize
        }
        
        if (this.currentCategory !== null) {
          params.categoryId = this.currentCategory
        }
        
        if (this.searchKeyword) {
          params.productName = this.searchKeyword
        }

        const res = await listProduct(params)
        this.productList = res.rows
        this.total = parseInt(res.total)
      } catch (error) {
        this.$message.error('加载商品失败')
      } finally {
        this.loading = false
      }
    },
    // 选择分类
    selectCategory(categoryId) {
      this.currentCategory = categoryId
      this.pageNum = 1
      this.loadProducts()
    },
    // 搜索
    handleSearch() {
      this.pageNum = 1
      this.loadProducts()
    },
    // 分页变化
    handlePageChange(page) {
      this.pageNum = page
      this.loadProducts()
    },
    // 获取商品成色
    getProductGrade(grade) {
      const gradeMap = {
        '0': '99新',
        '1': '95新',
        '2': '9新',
        '3': '8新',
        '4': '破损'
      }
      return gradeMap[grade] || '未知'
    },
    // 获取商品主图
    getProductImage(pics) {
      if (!pics) return 'https://via.placeholder.com/300x300?text=No+Image'
      const picArray = pics.split(',')
      return picArray[0] || 'https://via.placeholder.com/300x300?text=No+Image'
    },
    // 跳转商品详情
    goToDetail(productId) {
      this.$router.push(`/product/${productId}`)
    },
    // 跳转发布页
    goToPublish() {
      this.$router.push('/publish')
    },
    // 跳转个人中心
    goToUserCenter() {
      this.$router.push('/user/center')
    }
  }
}
</script>

<style scoped lang="scss">
.home-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

// 分类导航
.category-nav {
  background: white;
  border-bottom: 1px solid #eee;

  .category-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    gap: 30px;
    padding: 15px 20px;
    overflow-x: auto;
  }

  .category-item {
    padding: 8px 16px;
    cursor: pointer;
    color: #666;
    font-size: 14px;
    white-space: nowrap;
    transition: all 0.3s;
    border-radius: 4px;

    &:hover {
      background-color: #fff3e0;
      color: #ff5000;
    }

    &.active {
      background-color: #ff5000;
      color: white;
    }
  }
}

// 商品列表
.product-list-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}

.product-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.product-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
  }

  .product-image {
    width: 100%;
    height: 280px;
    overflow: hidden;
    background: #f8f8f8;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.3s;
    }

    &:hover img {
      transform: scale(1.05);
    }
  }

  .product-info {
    padding: 15px;

    .product-title {
      font-size: 15px;
      color: #333;
      margin: 0 0 8px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
      line-height: 1.4;
    }

    .product-desc {
      font-size: 13px;
      color: #999;
      margin: 0 0 10px 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .product-meta {
      display: flex;
      align-items: center;
      gap: 10px;
      margin-bottom: 10px;

      .price {
        font-size: 20px;
        color: #ff5000;
        font-weight: bold;
      }

      .original-price {
        font-size: 13px;
        color: #999;
        text-decoration: line-through;
      }
    }

    .product-footer {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 12px;

      .grade {
        background: #fff3e0;
        color: #ff5000;
        padding: 2px 8px;
        border-radius: 4px;
      }

      .views {
        color: #999;
        display: flex;
        align-items: center;
        gap: 4px;
      }
    }
  }
}

// 分页
.pagination {
  margin-top: 30px;
  text-align: center;
  padding: 20px 0;
}

// 响应式
@media (max-width: 768px) {
  .navbar-content {
    flex-direction: column;
    gap: 15px;
  }

  .search-box {
    max-width: 100%;
    width: 100%;
  }

  .product-list {
    grid-template-columns: repeat(2, 1fr);
    gap: 10px;
  }

  .product-image {
    height: 200px;
  }
}
</style>
