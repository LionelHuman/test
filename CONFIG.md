# 项目配置说明

## 快速开始

### 1. 配置文件说明

项目包含以下配置文件模板：

- `application-druid.yml.template` - 数据库配置模板
- `application.yml` - 应用主配置（部分配置需要修改）

### 2. 本地开发环境配置步骤

#### 步骤 1：复制配置文件

```bash
# 进入配置目录
cd eduswap-admin/src/main/resources/

# 复制数据库配置模板
cp application-druid.yml.template application-druid.yml
```

#### 步骤 2：修改数据库配置

编辑 `application-druid.yml` 文件，修改以下内容：

```yaml
spring:
    datasource:
        druid:
            master:
                url: jdbc:mysql://localhost:3306/edu_swap?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8
                username: root          # 修改为您的数据库用户名
                password: your_password # 修改为您的数据库密码
```

#### 步骤 3：修改 Redis 配置

编辑 `application.yml` 文件，修改 Redis 配置：

```yaml
spring:
  redis:
    host: localhost      # 修改为本地 Redis 地址
    port: 6379
    password:            # 如果没有密码，留空即可
```

#### 步骤 4：修改 Token 密钥

编辑 `application.yml` 文件，修改 Token 密钥（生产环境建议使用随机字符串）：

```yaml
token:
  secret: your_random_secret_key_here  # 修改为随机字符串
```

### 3. 数据库初始化

```bash
# 1. 创建数据库
mysql -u root -p -e "CREATE DATABASE edu_swap CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 2. 导入表结构
mysql -u root -p edu_swap < sql/ry_20250522.sql

# 3. 导入业务表
mysql -u root -p edu_swap < sql/edu_swap.sql

# 4. 导入字典数据
mysql -u root -p edu_swap < sql/数据字典初始化.sql
```

### 4. 启动项目

#### 方式 1：使用 IDEA

1. 打开项目
2. 找到 `RuoYiApplication.java`
3. 右键 → Run 'RuoYiApplication'

#### 方式 2：使用 Maven 命令

```bash
# 编译项目
mvn clean install

# 启动项目
mvn spring-boot:run
```

### 5. 访问系统

- **后台管理地址**：http://localhost:8080
- **默认管理员账号**：
  - 用户名：admin
  - 密码：admin123

---

## 生产环境配置

### 1. 创建生产环境配置文件

```bash
# 复制模板
cp application-druid.yml.template application-prod.yml
```

### 2. 修改生产环境配置

编辑 `application-prod.yml`：

```yaml
spring:
    datasource:
        druid:
            master:
                # 生产环境数据库地址
                url: jdbc:mysql://your_server_ip:3306/edu_swap?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
                username: your_username    # 生产环境数据库用户名
                password: your_password    # 生产环境数据库密码
```

### 3. 修改生产环境 application.yml

```yaml
spring:
  profiles:
    active: prod  # 激活生产环境配置
  redis:
    host: your_redis_server_ip
    password: your_redis_password

token:
  secret: your_production_random_secret  # 生产环境随机密钥
```

---

## 配置项说明

### 数据库配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| url | 数据库连接地址 | jdbc:mysql://localhost:3306/edu_swap |
| username | 数据库用户名 | root |
| password | 数据库密码 | root |
| initialSize | 初始连接数 | 5 |
| minIdle | 最小空闲连接 | 10 |
| maxActive | 最大连接数 | 20 |

### Redis 配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| host | Redis 服务器地址 | localhost |
| port | Redis 端口 | 6379 |
| password | Redis 密码 | 无 |
| database | Redis 数据库索引 | 0 |

### Token 配置

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| secret | Token 加密密钥 | abcdefghijklmnopqrstuvwxyz |
| expireTime | Token 有效期（分钟） | 30 |

---

## 常见问题

### 1. 数据库连接失败

**错误信息**：`Communications link failure`

**解决方案**：
- 检查 MySQL 服务是否启动
- 检查数据库地址、端口是否正确
- 检查防火墙设置

### 2. Redis 连接失败

**错误信息**：`Cannot get Jedis connection`

**解决方案**：
- 检查 Redis 服务是否启动
- 检查 Redis 配置中的密码是否正确

### 3. 端口被占用

**错误信息**：`Port 8080 is already in use`

**解决方案**：
- 修改 `application.yml` 中的端口号
- 或者关闭占用 8080 端口的程序

---

## 安全建议

1. **不要使用默认密码**
   - 数据库密码修改为复杂密码
   - Redis 密码必须设置
   - Druid 控制台密码修改

2. **Token 密钥随机化**
   - 生产环境使用随机生成的密钥
   - 定期更换密钥

3. **配置文件不要提交到 Git**
   - 使用 `.gitignore` 忽略配置文件
   - 只提交 `.template` 模板文件

4. **启用 SSL**
   - 生产环境数据库连接启用 SSL
   - Redis 启用密码认证

---

## 联系支持

如有问题，请联系项目维护者或查看若依框架官方文档。
