# EduSwap - 教育交换平台

> 🎓 基于若依 (RuoYi-Vue) 框架的校园闲置物品交易平台

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.5.15-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-2.x-green.svg)](https://cn.vuejs.org/)

---

## 📖 项目简介

EduSwap 是一个基于若依框架二次开发的校园闲置物品交易平台，实现学生之间的闲置物品流通。

**核心功能**:
- ✅ 商品发布与展示
- ✅ 商品搜索与分类
- ✅ 在线交易管理
- ✅ 用户评价系统
- ✅ 消息通知功能
- ✅ 后台管理功能

---

## 🏗️ 技术架构

### 后端技术栈
- **框架**: Spring Boot 2.5.15
- **安全**: Spring Security 5.7.14 + JWT
- **ORM**: MyBatis
- **数据库**: MySQL 5.7+ + Druid 1.2.27
- **缓存**: Redis 5.0+
- **定时任务**: Quartz
- **代码生成**: 若依代码生成器

### 前端技术栈
- **框架**: Vue 2.x + Vuex
- **UI 库**: Element UI 2.x
- **路由**: Vue Router 3.x
- **HTTP**: Axios
- **构建工具**: Webpack

---

## 📦 模块结构

```
eduswap/
├── eduswap-admin      # 后台管理模块 - Web 应用入口
├── eduswap-framework  # 核心框架模块 - 安全认证/权限控制
├── eduswap-system     # 系统管理模块 - 用户/角色/菜单管理
├── eduswap-quartz     # 定时任务模块 - 任务调度
├── eduswap-generator  # 代码生成模块 - 自动生成 CRUD
├── eduswap-common     # 通用工具模块 - 工具类/常量/注解
└── eduswap-ui         # 前端页面模块 - Vue 项目
```

---

## 🚀 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 5.0+
- Node.js 14+

### 1. 数据库初始化

```bash
# 1. 创建数据库
CREATE DATABASE IF NOT EXISTS eduswap DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

# 2. 导入数据
mysql -u root -p eduswap < sql/ry_20250522.sql
mysql -u root -p eduswap < sql/edu_swap.sql
```

### 2. 后端配置

```bash
# 1. 复制配置文件模板
cp eduswap-admin/src/main/resources/application-druid.yml.template \
   eduswap-admin/src/main/resources/application-druid.yml

# 2. 修改数据库配置
# 编辑 application-druid.yml，配置数据库连接
```

### 3. 后端启动

```bash
# 方式一：使用 Maven
cd eduswap
mvn clean install
java -jar eduswap-admin/target/eduswap-admin.jar

# 方式二：使用 IDEA
# 直接运行 RuoYiApplication.java
```

### 4. 前端启动

```bash
cd eduswap-ui

# 安装依赖
npm install

# 启动开发服务
npm run dev

# 访问地址：http://localhost:1024
```

---

## 📸 系统截图

### 前台商城
- 商品列表展示
- 商品分类筛选
- 商品详情页面
- 个人中心管理

### 后台管理
- 数据统计面板
- 商品管理
- 用户管理
- 系统配置

---

## 📚 文档导航

### 核心文档
- **[项目文档索引](项目文档索引.md)** - 完整文档导航
- **[项目架构说明](项目架构说明.md)** - 技术架构详解
- **[开发规范](开发规范.md)** - 开发规范与最佳实践
- **[CONFIG.md](CONFIG.md)** - 配置说明

### 快速指南
- [项目配置说明](项目配置说明.md) - 环境配置
- [创建顾客用户指南](创建顾客用户指南.md) - 测试用户
- [顾客角色权限配置指南](顾客角色权限配置指南.md) - 权限配置

### 技术文档
- [前后台布局分离](前后台布局分离.md) - 布局设计
- [路由跳转规则优化说明](路由跳转规则优化说明.md) - 路由配置
- [开发日志](doc/开发日志/) - 开发记录

---

## 🎯 核心功能

### 1. 商品管理
- 商品发布（支持图片上传）
- 商品编辑与删除
- 商品分类管理
- 商品状态管理（审核中/在售/已售出）

### 2. 交易流程
- 商品搜索与筛选
- 商品详情查看
- 在线沟通
- 订单管理

### 3. 用户功能
- 个人中心
- 我的发布
- 我的收藏
- 我的订单
- 消息通知

### 4. 后台管理
- 用户管理
- 角色权限
- 菜单管理
- 商品审核
- 数据统计

---

## 🗄️ 数据库设计

### 业务表 (8 张)
- `biz_product` - 商品表
- `biz_order` - 订单表
- `biz_category` - 分类表
- `biz_favorite` - 收藏表
- `biz_message` - 消息表
- `biz_comment` - 评价表
- `biz_report` - 举报表
- `biz_statistics` - 统计表

### 系统表 (若依框架)
- `sys_user` - 用户表
- `sys_role` - 角色表
- `sys_menu` - 菜单表
- `sys_dept` - 部门表
- `sys_dict_type` - 字典类型
- `sys_dict_data` - 字典数据

---

## 🔐 权限说明

### 角色分类
- **admin** - 管理员（访问后台管理系统）
- **customer** - 顾客（访问前台商城）
- **common** - 普通用户

### 登录跳转规则
```
管理员登录 → /dashboard (后台管理系统)
普通用户登录 → /index (前台商城)
```

### 商品展示规则
- 所有用户发布的商品都会在前台展示
- 包括：审核中、在售、已售出状态
- 未删除的商品（del_flag = '0'）

---

## 🛠️ 开发工具

### 推荐 IDE
- **后端**: IntelliJ IDEA
- **前端**: VS Code

### 数据库工具
- MySQL Workbench
- Navicat

### API 测试
- Postman
- Swagger (内置)

---

## 📝 Git 规范

### 提交类型
```
feat: 新功能
fix: 修复 bug
docs: 文档更新
style: 代码格式
refactor: 重构
test: 测试
chore: 构建/工具
```

### 示例
```bash
git commit -m "feat: 添加商品搜索功能"
git commit -m "fix: 修复登录跳转问题"
git commit -m "docs: 更新项目文档"
```

---

## 📊 项目统计

### 代码统计
- **后端模块**: 6 个
- **前端页面**: 20+ 个
- **业务表**: 8 张
- **系统表**: 20+ 张
- **API 接口**: 50+ 个

### 功能统计
- **生成页面**: 8 个业务页面
- **自定义页面**: 4 个前台页面
- **字典类型**: 2 个（商品分类、商品成色）
- **角色类型**: 3 个（admin、customer、common）

---

## 🔗 相关链接

- **若依框架**: http://www.ruoyi.vip/
- **Vue 文档**: https://cn.vuejs.org/
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Element UI**: https://element.eleme.cn/
- **GitHub 仓库**: https://github.com/LionelHuman/EduSwap

---

## 📞 技术支持

### 常见问题
- 查看 [项目文档索引](项目文档索引.md)
- 查看 [开发规范](开发规范.md)
- 查看 [开发日志](doc/开发日志/)

### 问题反馈
- GitHub Issues
- 开发日志记录

---

## 📄 开源协议

- **License**: MIT
- 详见 [LICENSE](LICENSE) 文件

---

## 👨‍ 作者

- **Developer**: LionelHuman
- **Email**: [你的邮箱]
- **GitHub**: https://github.com/LionelHuman

---

## 🎉 致谢

感谢以下开源项目：
- [若依框架](http://www.ruoyi.vip/) - 强大的企业级开发框架
- [Vue.js](https://vuejs.org/) - 渐进式 JavaScript 框架
- [Element UI](https://element.eleme.cn/) - 基于 Vue 的 UI 组件库
- [Spring Boot](https://spring.io/projects/spring-boot) - Java 开发框架

---

## 📈 项目状态

- ✅ 项目初始化完成
- ✅ 数据库设计完成
- ✅ 代码生成完成（8 个业务模块）
- ✅ 前后台路由分离
- ✅ 登录跳转优化
- ✅ 商品展示优化
- 🔄 持续开发中

---

*最后更新：2026-03-13*