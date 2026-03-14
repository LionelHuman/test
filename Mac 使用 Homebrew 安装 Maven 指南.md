# Mac 使用 Homebrew 安装 Maven 并编译项目

## 📦 当前状态

**Maven 安装中**...
```bash
brew install maven
```

---

## 🔧 安装步骤

### 1. 安装 Maven（自动完成）

```bash
brew install maven
```

**安装过程**：
- ✅ Homebrew 正在下载 Maven
- ⏳ 等待安装完成
- ⏳ 自动配置环境变量

### 2. 验证安装

```bash
mvn -version
```

**预期输出**：
```
Apache Maven 3.9.x
Maven home: /opt/homebrew/Cellar/maven/3.9.x
Java version: 1.8.x
```

### 3. 编译项目

```bash
cd "/Users/lionelhuman/develop/graduation project/Lingma/EduSwap"
mvn clean install -DskipTests
```

---

## ⏰ 当前命令（后台运行中）

```bash
cd "/Users/lionelhuman/develop/graduation project/Lingma/EduSwap" && \
brew install maven && \
mvn clean install -DskipTests
```

**说明**：
- 第 1 步：安装 Maven（约 1-2 分钟）
- 第 2 步：自动编译项目（约 2-5 分钟）
- 总计：约 5-10 分钟

---

## ✅ 编译成功标志

### Maven 输出

```
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3-5 minutes
[INFO] Finished at: 2026-03-12T...
[INFO] ------------------------------------------------------------------------
```

### 生成的文件

```
eduswap-admin/target/eduswap-admin.jar
```

---

## 🚀 下一步操作

### 1. 运行后端服务

```bash
cd eduswap-admin/target
java -jar eduswap-admin.jar
```

或使用 IDEA：
- 运行 `RuoYiApplication.java`

### 2. 测试首页

```
1. 清除缓存：Ctrl + Shift + Delete
2. 强制刷新：Ctrl + F5
3. 访问：http://localhost:1024/index
```

### 3. 验证数据

**预期效果**：
- ✅ 用户总数显示真实数字
- ✅ 今日访问显示真实数字
- ✅ 待处理任务显示真实数字
- ✅ 本月收入显示真实金额
- ✅ 图表正常显示

---

## 📝 常见问题

### Q1: Maven 安装失败？

**A**: 检查 Homebrew 是否正常
```bash
brew doctor
brew update
```

### Q2: 编译超时？

**A**: 增加 Maven 超时时间
```bash
mvn clean install -DskipTests -T 1C
```

### Q3: 依赖下载慢？

**A**: 使用阿里云镜像
```xml
<!-- 在 pom.xml 中添加 -->
<mirror>
  <id>aliyun</id>
  <mirrorOf>central</mirrorOf>
  <name>Aliyun Maven</name>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

### Q4: 端口被占用？

**A**: 修改端口或关闭占用进程
```bash
# 查看端口占用
lsof -i :1024

# 杀死进程
kill -9 <PID>
```

---

## 🎯 快速检查清单

### 安装检查
- [ ] Homebrew 已安装
- [ ] Maven 正在安装
- [ ] 环境变量已配置

### 编译检查
- [ ] 项目目录正确
- [ ] pom.xml 存在
- [ ] 编译命令执行中
- [ ] 无编译错误

### 运行检查
- [ ] jar 包已生成
- [ ] 数据库连接正常
- [ ] Redis 连接正常
- [ ] 端口未被占用

---

## 📞 需要帮助？

如果编译失败，请提供：

1. **完整错误日志**
   ```bash
   # 查看最后 100 行
   tail -n 100 build.log
   ```

2. **Maven 版本**
   ```bash
   mvn -version
   ```

3. **Java 版本**
   ```bash
   java -version
   ```

---

## ✅ 预计时间

| 步骤 | 预计时间 |
|------|---------|
| Maven 安装 | 1-2 分钟 |
| 依赖下载 | 2-5 分钟 |
| 项目编译 | 2-3 分钟 |
| **总计** | **5-10 分钟** |

---

## 🎉 完成后的效果

**编译成功**：
```
[INFO] BUILD SUCCESS
```

**启动成功**：
```
EduSwap 启动成功！
端口：1024
```

**页面显示**：
```
用户总数：1,258
今日访问：326
待处理任务：18
本月收入：¥28,650
[图表正常显示]
```

---

**状态**：⏳ Maven 安装中...  
**下一步**：等待安装完成，自动编译项目

**最后更新**：2026-03-12
