<template>
  <div class="front-layout">
    <!-- 前台顶部导航 -->
    <div class="front-navbar">
      <div class="navbar-container">
        <div class="logo" @click="goToHome">
          <i class="el-icon-shop"></i>
          <span>EduSwap</span>
        </div>
        <div class="nav-actions">
          <el-button type="text" @click="goToPublish">
            <i class="el-icon-plus"></i> 发布商品
          </el-button>
          <el-button type="text" @click="goToUserCenter">
            <i class="el-icon-user"></i> 个人中心
          </el-button>
          <el-dropdown @command="handleCommand" v-if="isLogin">
            <span class="user-info">
              <el-avatar :size="30" :src="avatar" />
              <span class="username">{{ nickName }}</span>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button v-else type="primary" @click="goToLogin">登录</el-button>
        </div>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="front-main">
      <router-view />
    </div>

    <!-- 底部 -->
    <div class="front-footer">
      <div class="footer-content">
        <p>© 2026 EduSwap 二手交易平台 - All Rights Reserved</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'FrontLayout',
  data() {
    return {}
  },
  computed: {
    ...mapGetters(['nickName', 'avatar']),
    isLogin() {
      return this.$store.getters.roles && this.$store.getters.roles.length > 0
    }
  },
  methods: {
    goToHome() {
      this.$router.push('/front/index')
    },
    goToPublish() {
      this.$router.push('/publish')
    },
    goToUserCenter() {
      this.$router.push('/user/center')
    },
    goToLogin() {
      this.$router.push('/login')
    },
    handleSearch() {
      // 搜索功能已移除
    },
    handleCommand(command) {
      if (command === 'logout') {
        this.$store.dispatch('LogOut').then(() => {
          // 清除所有状态并刷新页面，确保下次登录时不会受之前路由影响
          this.$router.replace('/login')
          this.$message.success('退出成功')
        })
      } else if (command === 'profile') {
        this.goToUserCenter()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.front-layout {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.front-navbar {
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 1000;

  .navbar-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 15px 20px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .logo {
    display: flex;
    align-items: center;
    cursor: pointer;
    font-size: 24px;
    font-weight: bold;
    color: #409EFF;
    
    i {
      margin-right: 8px;
      font-size: 28px;
    }
  }

  .nav-actions {
    display: flex;
    align-items: center;
    gap: 15px;

    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;

      .username {
        font-size: 14px;
        color: #606266;
      }
    }
  }
}

.front-main {
  min-height: calc(100vh - 120px);
  padding: 20px 0;
}

.front-footer {
  background-color: #333;
  color: #fff;
  padding: 20px 0;
  text-align: center;

  .footer-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
  }
}
</style>
