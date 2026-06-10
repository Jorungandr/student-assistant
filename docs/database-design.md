# 数据库设计

本文档记录大学生个人助手系统的数据库表结构、字段含义和表关系。

## 数据库

数据库名：`student_assistant`

字符集：`utf8mb4`

所有业务表都包含 `user_id`，用于隔离不同用户的数据。后端查询、更新、删除数据时必须同时校验业务数据的 `user_id` 是否属于当前登录用户。

## 通用字段

- `id`：自增主键。
- `user_id`：数据所属用户。
- `deleted`：软删除标记，`0` 表示未删除，`1` 表示已删除。
- `created_at`：创建时间。
- `updated_at`：更新时间。

## 表结构

### `sys_user`

用户账号表。

| 字段 | 说明 |
| --- | --- |
| `username` | 登录用户名，唯一 |
| `password` | BCrypt 加密后的密码 |
| `nickname` | 昵称 |
| `email` | 邮箱 |
| `avatar` | 头像地址 |
| `gender` | 性别 |
| `status` | 账号状态 |

### `course`

课程表，用于维护周课表。

| 字段 | 说明 |
| --- | --- |
| `course_name` | 课程名称 |
| `teacher` | 任课教师 |
| `classroom` | 上课地点 |
| `weekday` | 星期，1-7 |
| `start_section` | 开始节次 |
| `end_section` | 结束节次 |
| `start_week` | 开始周 |
| `end_week` | 结束周 |
| `color` | 课程展示颜色 |

### `task`

学习任务和待办表。

| 字段 | 说明 |
| --- | --- |
| `course_id` | 关联课程，可为空 |
| `title` | 任务标题 |
| `description` | 任务说明 |
| `task_type` | 任务类型，如作业、复习、考试、普通待办 |
| `priority` | 优先级 |
| `status` | 状态 |
| `deadline` | 截止时间 |
| `completed_at` | 完成时间 |

### `schedule`

普通日程表。

| 字段 | 说明 |
| --- | --- |
| `title` | 日程标题 |
| `description` | 日程说明 |
| `location` | 地点 |
| `start_time` | 开始时间 |
| `end_time` | 结束时间 |
| `schedule_type` | 日程类型 |
| `remind_time` | 页面提示时间，不做独立提醒中心 |

### `study_record`

学习时长记录表。

| 字段 | 说明 |
| --- | --- |
| `course_id` | 关联课程，可为空 |
| `study_date` | 学习日期 |
| `duration_minutes` | 学习时长，单位分钟 |
| `content` | 学习内容 |

### `health_record`

生活健康记录表。

| 字段 | 说明 |
| --- | --- |
| `record_date` | 记录日期 |
| `weather` | 天气 |
| `sleep_hours` | 睡眠小时数 |
| `water_ml` | 饮水量，单位毫升 |
| `exercise_minutes` | 运动时长，单位分钟 |
| `mood_score` | 情绪评分 |
| `stress_score` | 压力评分 |
| `note` | 备注 |

### `finance_record`

收支记录表。

| 字段 | 说明 |
| --- | --- |
| `record_type` | `INCOME` 或 `EXPENSE` |
| `category` | 分类 |
| `amount` | 金额 |
| `description` | 说明 |
| `record_date` | 记录日期 |

### `budget`

月度预算表。

| 字段 | 说明 |
| --- | --- |
| `month` | 月份，格式 `YYYY-MM` |
| `category` | 分类 |
| `amount` | 预算金额 |
| `warning_threshold` | 页面预警阈值百分比 |

### `goal`

个人目标表。

| 字段 | 说明 |
| --- | --- |
| `title` | 目标标题 |
| `description` | 目标说明 |
| `goal_type` | 目标类型 |
| `target_value` | 目标值 |
| `current_value` | 当前进度值 |
| `unit` | 单位 |
| `start_date` | 开始日期 |
| `end_date` | 结束日期 |
| `status` | 状态 |

### `habit`

习惯表。

| 字段 | 说明 |
| --- | --- |
| `habit_name` | 习惯名称 |
| `description` | 习惯说明 |
| `frequency` | 频率 |
| `target_count` | 每日目标次数 |
| `status` | 状态 |

### `habit_checkin`

习惯打卡表。

| 字段 | 说明 |
| --- | --- |
| `habit_id` | 关联习惯 |
| `checkin_date` | 打卡日期 |
| `checkin_count` | 打卡次数 |
| `note` | 备注 |

## 索引设计

- 所有业务表均为 `user_id` 建立索引。
- 课程表建立 `(user_id, weekday)` 索引，用于查询今日课程。
- 任务表建立 `(user_id, deadline)` 和 `(user_id, status)` 索引，用于 DDL 和任务状态统计。
- 学习记录表建立 `(user_id, study_date)` 索引，用于学习时长趋势统计。
- 消费记录表建立 `(user_id, record_date)` 和 `(user_id, category)` 索引，用于月度统计和分类统计。
- 预算表建立 `(user_id, month)` 索引，用于月度预算查询。
- 目标表建立 `(user_id, status)` 和 `(user_id, end_date)` 索引，用于目标进度展示。
- 习惯打卡表建立 `(habit_id, checkin_date)` 和 `(user_id, checkin_date)` 索引，用于连续打卡统计。

## 表关系

- `course.user_id` 关联 `sys_user.id`。
- `task.user_id` 关联 `sys_user.id`，`task.course_id` 可关联 `course.id`。
- `schedule.user_id` 关联 `sys_user.id`。
- `study_record.user_id` 关联 `sys_user.id`，`study_record.course_id` 可关联 `course.id`。
- `health_record.user_id` 关联 `sys_user.id`。
- `finance_record.user_id` 关联 `sys_user.id`。
- `budget.user_id` 关联 `sys_user.id`。
- `goal.user_id` 关联 `sys_user.id`。
- `habit.user_id` 关联 `sys_user.id`。
- `habit_checkin.user_id` 关联 `sys_user.id`，`habit_checkin.habit_id` 关联 `habit.id`。
