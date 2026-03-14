<template>
  <div class="detail-container">
    <!-- 商品详情 -->
    <div class="product-detail" v-if="product">
      <div class="detail-content">
        <!-- 商品图片 -->
        <div class="product-gallery">
          <div class="main-image">
            <img :src="currentImage" alt="商品主图" />
          </div>
          <div class="thumbnail-list" v-if="imageList.length > 1">
            <div 
              v-for="(img, index) in imageList" 
              :key="index"
              class="thumbnail"
              :class="{ active: currentImage === img }"
              @click="currentImage = img"
            >
              <img :src="img" alt="缩略图" />
            </div>
          </div>
        </div>

        <!-- 商品信息 -->
        <div class="product-info-panel">
          <h1 class="product-title">{{ product.productName }}</h1>
          <p class="product-desc">{{ product.productDesc }}</p>
          
          <div class="price-section">
            <div class="current-price">
              <span class="label">现价</span>
              <span class="price">¥{{ product.currentPrice }}</span>
            </div>
            <div class="original-price" v-if="product.originalPrice">
              <span class="label">原价</span>
              <span>¥{{ product.originalPrice }}</span>
            </div>
          </div>

          <div class="product-specs">
            <div class="spec-item">
              <span class="spec-label">成色</span>
              <span class="spec-value">{{ getProductGrade(product.productGrade) }}</span>
            </div>
            <div class="spec-item" v-if="product.isWarranty === '1'">
              <span class="spec-label">保修</span>
              <span class="spec-value">保修期内 ({{ product.warrantyPeriod }})</span>
            </div>
            <div class="spec-item">
              <span class="spec-label">浏览</span>
              <span class="spec-value">{{ product.viewCount }} 次</span>
            </div>
            <div class="spec-item">
              <span class="spec-label">发布</span>
              <span class="spec-value">{{ formatDate(product.createTime) }}</span>
            </div>
          </div>

          <div class="action-buttons">
            <el-button type="primary" size="large" @click="contactSeller">
              <i class="el-icon-chat-dot-round"></i> 联系卖家
            </el-button>
            <el-button size="large" @click="toggleFavorite">
              <i :class="isFavorite ? 'el-icon-star-on' : 'el-icon-star-off'"></i>
              {{ isFavorite ? '已收藏' : '收藏' }}
            </el-button>
          </div>
        </div>
      </div>

      <!-- 卖家信息 -->
      <div class="seller-info">
        <h3>卖家信息</h3>
        <div class="seller-content">
          <div class="seller-avatar">
            <i class="el-icon-user-solid"></i>
          </div>
          <div class="seller-detail">
            <p class="seller-name">用户 ID: {{ product.userId }}</p>
            <p class="seller-status">
              <el-tag size="mini" type="success">在售</el-tag>
            </p>
          </div>
        </div>
      </div>

      <!-- 商品详情 -->
      <div class="product-description">
        <h3>商品详情</h3>
        <div class="desc-content">
          <p>{{ product.productDesc }}</p>
        </div>
      </div>
    </div>

    <!-- 加载中 -->
    <div class="loading-container" v-if="loading">
      <el-spinner :size="42"></el-spinner>
      <p>加载中...</p>
    </div>
  </div>
</template>

<script>
import { getProduct } from '@/api/biz/product'

export default {
  name: 'ProductDetail',
  data() {
    return {
      productId: this.$route.params.id,
      loading: true,
      product: null,
      searchKeyword: '',
      currentImage: '',
      imageList: [],
      isFavorite: false
    }
  },
  created() {
    // 验证 productId 是否存在
    if (!this.productId) {
      this.$message.error('商品 ID 不能为空')
      this.$router.push('/index') // 返回前台首页
      return
    }
    this.loadProduct()
  },
  methods: {
    // 加载商品详情
    async loadProduct() {
      this.loading = true
      try {
        const res = await getProduct(this.productId)
        this.product = res.data
        
        // 处理图片列表
        if (this.product.productPics) {
          this.imageList = this.product.productPics.split(',')
          this.currentImage = this.imageList[0]
        } else {
          this.currentImage = 'https://via.placeholder.com/500x500?text=No+Image'
        }
      } catch (error) {
        this.$message.error('加载商品详情失败')
      } finally {
        this.loading = false
      }
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
    // 格式化日期
    formatDate(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    // 联系卖家
    contactSeller() {
      this.$message.info('联系卖家功能开发中')
    },
    // 收藏/取消收藏
    toggleFavorite() {
      this.isFavorite = !this.isFavorite
      this.$message.success(this.isFavorite ? '收藏成功' : '取消收藏')
    },
    // 搜索
    handleSearch() {
      if (this.searchKeyword) {
        this.$router.push({ path: '/front/index', query: { keyword: this.searchKeyword }})
      }
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
.detail-container {
  min-height: 100vh;
  background-color: #f5f5f5;
}

// 商品详情
.product-detail {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}

.detail-content {
  display: grid;
  grid-template-columns: 500px 1fr;
  gap: 30px;
  background: white;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

// 商品图片
.product-gallery {
  .main-image {
    width: 100%;
    height: 500px;
    background: #f8f8f8;
    border-radius: 8px;
    overflow: hidden;
    margin-bottom: 15px;

    img {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }
  }

  .thumbnail-list {
    display: flex;
    gap: 10px;
    overflow-x: auto;

    .thumbnail {
      width: 80px;
      height: 80px;
      border: 2px solid transparent;
      border-radius: 6px;
      cursor: pointer;
      overflow: hidden;
      flex-shrink: 0;

      &.active {
        border-color: #ff5000;
      }

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
    }
  }
}

// 商品信息面板
.product-info-panel {
  .product-title {
    font-size: 22px;
    color: #333;
    margin: 0 0 15px 0;
    line-height: 1.4;
  }

  .product-desc {
    font-size: 14px;
    color: #666;
    margin: 0 0 25px 0;
    line-height: 1.6;
  }

  .price-section {
    margin-bottom: 30px;

    .current-price {
      display: flex;
      align-items: baseline;
      gap: 10px;
      margin-bottom: 10px;

      .label {
        font-size: 14px;
        color: #999;
      }

      .price {
        font-size: 32px;
        color: #ff5000;
        font-weight: bold;
      }
    }

    .original-price {
      display: flex;
      align-items: baseline;
      gap: 10px;

      .label {
        font-size: 14px;
        color: #999;
      }

      span:last-child {
        font-size: 15px;
        color: #999;
        text-decoration: line-through;
      }
    }
  }

  .product-specs {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 15px;
    margin-bottom: 30px;

    .spec-item {
      display: flex;
      justify-content: space-between;
      padding: 12px 15px;
      background: #f8f8f8;
      border-radius: 6px;

      .spec-label {
        font-size: 14px;
        color: #999;
      }

      .spec-value {
        font-size: 14px;
        color: #333;
        font-weight: 500;
      }
    }
  }

  .action-buttons {
    display: flex;
    gap: 15px;

    .el-button {
      flex: 1;
    }
  }
}

// 卖家信息
.seller-info {
  background: white;
  margin-top: 20px;
  padding: 25px 30px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);

  h3 {
    font-size: 18px;
    color: #333;
    margin: 0 0 20px 0;
  }

  .seller-content {
    display: flex;
    align-items: center;
    gap: 20px;

    .seller-avatar {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-size: 28px;
    }

    .seller-detail {
      flex: 1;

      .seller-name {
        font-size: 16px;
        color: #333;
        margin: 0 0 8px 0;
      }

      .seller-status {
        font-size: 14px;
      }
    }
  }
}

// 商品详情
.product-description {
  background: white;
  margin-top: 20px;
  padding: 25px 30px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);

  h3 {
    font-size: 18px;
    color: #333;
    margin: 0 0 20px 0;
  }

  .desc-content {
    p {
      font-size: 15px;
      color: #666;
      line-height: 1.8;
      white-space: pre-wrap;
    }
  }
}

// 加载中
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 0;

  p {
    margin-top: 20px;
    color: #999;
  }
}

// 响应式
@media (max-width: 768px) {
  .detail-content {
    grid-template-columns: 1fr;
    padding: 20px;
  }

  .product-gallery .main-image {
    height: 300px;
  }

  .product-specs {
    grid-template-columns: 1fr;
  }
}
</style>
