# 大学生个人助手系统设计

## 目标

构建一个正式产品版的大学生个人助手 Web 应用，覆盖学习、课程、健康、消费、目标和习惯管理。系统采用前后端分离架构，前端负责交互和图表展示，后端提供认证、业务接口、统计分析和数据持久化。

## 技术栈

- 前端：Vue 3、Vite、Vue Router、Pinia、Element Plus、ECharts
- 后端：Spring Boot、Spring Security、JWT、MyBatis Plus
- 数据库：MySQL
- 接口风格：RESTful API
- 数据格式：JSON

## 系统范围

系统包含以下模块：

1. 登录注册与个人中心
2. 首页数据看板
3. 课程与日程管理
4. 学习任务分析
5. 生活健康记录
6. 消费与预算管理
7. 个人目标与习惯打卡

系统不包含独立的智能提醒中心，也不维护提醒表。首页和业务页面只展示轻量状态提示，例如作业即将截止、任务已逾期、预算接近上限、目标进度偏低等。

## 页面设计

前端采用后台管理式布局：

- 左侧导航栏：展示主要功能模块。
- 顶部栏：展示当前用户信息、退出登录入口。
- 主体区域：根据路由切换功能页面。

页面划分：

- 登录页：用户登录、跳转注册。
- 注册页：创建账号。
- 首页看板：展示今日课程、待办任务、天气、睡眠、饮水、运动、目标进度、预算摘要。
- 课程与日程页：管理课程表、作业 DDL、普通日程、任务状态。
- 学习分析页：展示学习时长、任务完成率、课程学习占比、优先级任务。
- 健康记录页：记录天气、睡眠、饮水、运动、情绪压力。
- 消费预算页：记录收入支出、查看分类统计、维护月度预算。
- 目标习惯页：管理阶段性目标、自定义计划、每日习惯打卡、连续打卡统计。
- 个人中心页：维护昵称、邮箱、头像、密码。

## 数据库设计

数据库建议命名为 `student_assistant`。

### 用户表 `sys_user`

- `id`
- `username`
- `password`
- `nickname`
- `email`
- `avatar`
- `gender`
- `status`
- `created_at`
- `updated_at`

### 课程表 `course`

- `id`
- `user_id`
- `course_name`
- `teacher`
- `classroom`
- `weekday`
- `start_section`
- `end_section`
- `start_week`
- `end_week`
- `color`
- `created_at`

### 任务表 `task`

- `id`
- `user_id`
- `course_id`
- `title`
- `description`
- `task_type`
- `priority`
- `status`
- `deadline`
- `completed_at`
- `created_at`

### 日程表 `schedule`

- `id`
- `user_id`
- `title`
- `description`
- `location`
- `start_time`
- `end_time`
- `schedule_type`
- `remind_time`
- `created_at`

### 学习记录表 `study_record`

- `id`
- `user_id`
- `course_id`
- `study_date`
- `duration_minutes`
- `content`
- `created_at`

### 健康记录表 `health_record`

- `id`
- `user_id`
- `record_date`
- `weather`
- `sleep_hours`
- `water_ml`
- `exercise_minutes`
- `mood_score`
- `stress_score`
- `note`

### 消费记录表 `finance_record`

- `id`
- `user_id`
- `record_type`
- `category`
- `amount`
- `description`
- `record_date`
- `created_at`

### 预算表 `budget`

- `id`
- `user_id`
- `month`
- `category`
- `amount`
- `warning_threshold`
- `created_at`

### 目标表 `goal`

- `id`
- `user_id`
- `title`
- `description`
- `goal_type`
- `target_value`
- `current_value`
- `unit`
- `start_date`
- `end_date`
- `status`
- `created_at`

### 习惯表 `habit`

- `id`
- `user_id`
- `habit_name`
- `description`
- `frequency`
- `target_count`
- `status`
- `created_at`

### 习惯打卡表 `habit_checkin`

- `id`
- `user_id`
- `habit_id`
- `checkin_date`
- `checkin_count`
- `note`
- `created_at`

## 接口设计

接口统一前缀为 `/api`。

### 认证与用户

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/profile`
- `PUT /api/auth/profile`
- `PUT /api/auth/password`

### 首页看板

- `GET /api/dashboard/summary`

### 课程与日程

- `GET /api/courses`
- `POST /api/courses`
- `PUT /api/courses/{id}`
- `DELETE /api/courses/{id}`
- `GET /api/courses/today`
- `GET /api/schedules`
- `POST /api/schedules`
- `PUT /api/schedules/{id}`
- `DELETE /api/schedules/{id}`

### 任务管理

- `GET /api/tasks`
- `POST /api/tasks`
- `PUT /api/tasks/{id}`
- `DELETE /api/tasks/{id}`
- `PUT /api/tasks/{id}/status`
- `GET /api/tasks/upcoming`
- `GET /api/tasks/statistics`

### 学习分析

- `GET /api/study-records`
- `POST /api/study-records`
- `PUT /api/study-records/{id}`
- `DELETE /api/study-records/{id}`
- `GET /api/analysis/study-duration`
- `GET /api/analysis/task-completion`
- `GET /api/analysis/course-ratio`
- `GET /api/analysis/priority-tasks`

### 生活健康

- `GET /api/health-records`
- `POST /api/health-records`
- `PUT /api/health-records/{id}`
- `DELETE /api/health-records/{id}`
- `GET /api/health-records/today`
- `GET /api/health-records/statistics`

### 消费与预算

- `GET /api/finance-records`
- `POST /api/finance-records`
- `PUT /api/finance-records/{id}`
- `DELETE /api/finance-records/{id}`
- `GET /api/finance-records/statistics`
- `GET /api/budgets`
- `POST /api/budgets`
- `PUT /api/budgets/{id}`
- `DELETE /api/budgets/{id}`
- `GET /api/budgets/warnings`

### 目标与习惯

- `GET /api/goals`
- `POST /api/goals`
- `PUT /api/goals/{id}`
- `DELETE /api/goals/{id}`
- `PUT /api/goals/{id}/progress`
- `GET /api/habits`
- `POST /api/habits`
- `PUT /api/habits/{id}`
- `DELETE /api/habits/{id}`
- `POST /api/habits/{id}/checkin`
- `GET /api/habits/{id}/checkins`
- `GET /api/habits/statistics`

## 业务流程

1. 用户注册并登录。
2. 后端通过 JWT 识别当前用户。
3. 用户维护课程、任务、日程、学习记录、健康记录、消费预算、目标和习惯。
4. 首页通过 `/api/dashboard/summary` 聚合展示今日关键数据。
5. 学习分析、消费统计、打卡统计通过后端聚合查询返回图表数据。
6. 前端通过 ECharts 展示趋势、占比和完成率。
7. 所有业务数据均按 `user_id` 隔离，用户不能访问其他用户的数据。

## 测试设计

### 后端单元测试

- 用户注册参数校验。
- 登录密码校验。
- 任务状态更新。
- 学习时长统计。
- 预算使用率计算。
- 连续打卡天数计算。

### 后端接口测试

- 登录注册接口。
- 课程 CRUD 接口。
- 任务 CRUD 接口。
- 健康记录 CRUD 接口。
- 消费预算 CRUD 接口。
- 目标习惯 CRUD 接口。
- 未登录访问接口应返回 401。
- 用户不能访问其他用户的数据。

### 前端功能测试

- 登录后进入首页。
- 新增课程后课程表展示正确。
- 新增任务后任务列表更新。
- 修改任务状态后统计变化。
- 新增消费后预算统计变化。
- 习惯打卡后连续天数变化。

### 系统测试场景

- 新用户完整使用流程。
- 课程、作业、学习记录分析流程。
- 月度消费预算管理流程。
- 目标创建与每日习惯打卡流程。

## 项目结构

```text
student-assistant/
├─ frontend/
│  ├─ src/
│  │  ├─ api/
│  │  ├─ assets/
│  │  ├─ components/
│  │  ├─ layout/
│  │  ├─ router/
│  │  ├─ stores/
│  │  ├─ views/
│  │  │  ├─ auth/
│  │  │  ├─ dashboard/
│  │  │  ├─ courses/
│  │  │  ├─ study/
│  │  │  ├─ health/
│  │  │  ├─ finance/
│  │  │  └─ goals/
│  │  ├─ App.vue
│  │  └─ main.js
│  ├─ package.json
│  └─ vite.config.js
│
├─ backend/
│  ├─ src/main/java/com/example/studentassistant/
│  │  ├─ config/
│  │  ├─ controller/
│  │  ├─ dto/
│  │  ├─ entity/
│  │  ├─ mapper/
│  │  ├─ security/
│  │  ├─ service/
│  │  └─ StudentAssistantApplication.java
│  ├─ src/main/resources/
│  │  ├─ mapper/
│  │  ├─ application.yml
│  │  └─ db/schema.sql
│  ├─ src/test/java/
│  └─ pom.xml
│
├─ docs/
│  ├─ database-design.md
│  ├─ api-design.md
│  ├─ test-plan.md
│  └─ user-guide.md
│
└─ README.md
```

## 交付物

- 可运行的 Vue 前端。
- 可运行的 Spring Boot 后端。
- MySQL 建表 SQL。
- 基础测试用例。
- README 启动说明。
- 数据库设计文档。
- 接口设计文档。
- 测试计划文档。
