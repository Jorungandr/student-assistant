# 大学生个人助手

本项目是“软件质量保证与测试”课程实验的待测 JavaWeb 程序，面向大学生日常学习与生活管理场景。系统采用前后端分离架构，前端使用 Vue 3，后端使用 Spring Boot，数据库使用 MySQL。

## 功能模块

- 登录注册与个人中心：账号注册、登录、资料维护、密码修改。
- 首页数据看板：今日课程、待办任务、健康概览、预算状态、目标进度、习惯打卡统计。
- 课程与日程管理：课程表、作业 DDL、普通日程、任务状态维护。
- 学习任务分析：学习时长记录、任务完成率、课程学习占比、优先级任务。
- 生活健康记录：天气、睡眠、饮水、运动、情绪压力记录。
- 消费与预算管理：收入支出记录、分类统计、月度预算、预算预警。
- 个人目标与习惯打卡：阶段目标、目标进度、每日习惯打卡、连续打卡统计。

## 开发和运行环境

- JDK 17 或更高版本
- Maven 3.6 或更高版本
- Node.js 18 或更高版本
- MySQL 8.0 或兼容版本
- 浏览器：Chrome、Edge 或 Firefox

## 技术栈

- 前端：Vue 3、Vite、Vue Router、Pinia、Element Plus、ECharts、Axios
- 后端：Spring Boot、Spring Security、JWT、MyBatis Plus、JUnit 5
- 数据库：MySQL

## 数据库初始化

先创建数据库和表结构：

```powershell
Get-Content -Raw backend\src\main\resources\db\schema.sql | mysql -uroot -p你的数据库密码
```

默认配置使用：

- 数据库：`student_assistant`
- 用户名：`root`
- 密码：`jorungandr`

如果你的 MySQL 密码不同，可以通过环境变量或启动脚本参数覆盖。

## 启动后端

方式一：使用脚本。

```powershell
powershell -ExecutionPolicy Bypass -File scripts\start-backend.ps1 -DbUsername root -DbPassword 你的数据库密码
```

方式二：手动启动。

```powershell
cd backend
$env:DB_USERNAME="root"
$env:DB_PASSWORD="你的数据库密码"
mvn spring-boot:run
```

后端地址：

```text
http://localhost:8080
```

## 启动前端

方式一：使用脚本。

```powershell
powershell -ExecutionPolicy Bypass -File scripts\start-frontend.ps1
```

方式二：手动启动。

```powershell
cd frontend
npm install
npm run dev
```

前端地址：

```text
http://localhost:5173
```

## 测试

后端单元测试和服务层集成测试：

```powershell
cd backend
mvn test
```

前端构建测试：

```powershell
cd frontend
npm run build
```

## 实验说明

本项目可作为实验 1 和实验 2 的待测程序：

- 实验 1：使用 JUnit 对后端核心业务逻辑进行白盒测试。
- 实验 2：使用浏览器对系统功能进行黑盒手工测试。

后端测试用例位于：

```text
backend/src/test/java/com/example/studentassistant/service
```

