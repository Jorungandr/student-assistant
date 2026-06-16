# 接口设计

## 1. 通用说明

接口统一前缀为 `/api`，请求和响应均使用 JSON。除注册和登录接口外，其余接口均需要在请求头中携带 JWT。

认证请求头格式：

```text
Authorization: Bearer <token>
```

通用响应格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

## 2. 认证与用户

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/register` | 用户注册 |
| POST | `/api/auth/login` | 用户登录 |
| GET | `/api/auth/profile` | 获取当前用户资料 |
| PUT | `/api/auth/profile` | 修改用户资料 |
| PUT | `/api/auth/password` | 修改密码 |

## 3. 首页看板

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/dashboard/summary` | 获取首页聚合数据 |

首页聚合数据包括今日课程、待办任务、健康概览、预算状态、目标进度、任务统计、习惯统计和轻量状态提示。

## 4. 课程与日程

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/courses` | 查询课程列表 |
| POST | `/api/courses` | 新增课程 |
| PUT | `/api/courses/{id}` | 修改课程 |
| DELETE | `/api/courses/{id}` | 删除课程 |
| GET | `/api/courses/today` | 查询今日课程 |
| GET | `/api/schedules` | 查询日程列表 |
| POST | `/api/schedules` | 新增日程 |
| PUT | `/api/schedules/{id}` | 修改日程 |
| DELETE | `/api/schedules/{id}` | 删除日程 |

## 5. 任务管理

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/tasks` | 查询任务列表 |
| POST | `/api/tasks` | 新增任务 |
| PUT | `/api/tasks/{id}` | 修改任务 |
| DELETE | `/api/tasks/{id}` | 删除任务 |
| PUT | `/api/tasks/{id}/status` | 修改任务状态 |
| GET | `/api/tasks/upcoming` | 查询近期待办任务 |
| GET | `/api/tasks/statistics` | 查询任务统计 |

## 6. 学习分析

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/study-records` | 查询学习记录 |
| POST | `/api/study-records` | 新增学习记录 |
| PUT | `/api/study-records/{id}` | 修改学习记录 |
| DELETE | `/api/study-records/{id}` | 删除学习记录 |
| GET | `/api/analysis/study-duration` | 学习时长趋势 |
| GET | `/api/analysis/task-completion` | 任务完成率 |
| GET | `/api/analysis/course-ratio` | 课程学习占比 |
| GET | `/api/analysis/priority-tasks` | 优先级任务 |

## 7. 生活健康

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/health-records` | 查询健康记录 |
| POST | `/api/health-records` | 新增或更新健康记录 |
| PUT | `/api/health-records/{id}` | 修改健康记录 |
| DELETE | `/api/health-records/{id}` | 删除健康记录 |
| GET | `/api/health-records/today` | 查询今日健康记录 |
| GET | `/api/health-records/statistics` | 查询健康统计 |

## 8. 消费与预算

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/finance-records` | 查询收支记录 |
| POST | `/api/finance-records` | 新增收支记录 |
| PUT | `/api/finance-records/{id}` | 修改收支记录 |
| DELETE | `/api/finance-records/{id}` | 删除收支记录 |
| GET | `/api/finance-records/statistics` | 支出分类统计 |
| GET | `/api/budgets` | 查询预算 |
| POST | `/api/budgets` | 新增或更新预算 |
| PUT | `/api/budgets/{id}` | 修改预算 |
| DELETE | `/api/budgets/{id}` | 删除预算 |
| GET | `/api/budgets/warnings` | 查询预算预警 |

## 9. 目标与习惯

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/goals` | 查询目标 |
| POST | `/api/goals` | 新增目标 |
| PUT | `/api/goals/{id}` | 修改目标 |
| DELETE | `/api/goals/{id}` | 删除目标 |
| PUT | `/api/goals/{id}/progress` | 更新目标进度 |
| GET | `/api/habits` | 查询习惯 |
| POST | `/api/habits` | 新增习惯 |
| PUT | `/api/habits/{id}` | 修改习惯 |
| DELETE | `/api/habits/{id}` | 删除习惯 |
| POST | `/api/habits/{id}/checkin` | 习惯打卡 |
| GET | `/api/habits/{id}/checkins` | 查询习惯打卡记录 |
| GET | `/api/habits/statistics` | 查询习惯统计 |

