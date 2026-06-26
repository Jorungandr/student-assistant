USE student_assistant;

SET @uid = (SELECT id FROM sys_user WHERE username = 'demo' LIMIT 1);

UPDATE sys_user
SET nickname = '刘皓天', email = 'demo@student.local', gender = '男'
WHERE id = @uid;

DELETE FROM habit_checkin WHERE user_id = @uid;
DELETE FROM habit WHERE user_id = @uid;
DELETE FROM goal WHERE user_id = @uid;
DELETE FROM budget WHERE user_id = @uid;
DELETE FROM finance_record WHERE user_id = @uid;
DELETE FROM health_record WHERE user_id = @uid;
DELETE FROM study_record WHERE user_id = @uid;
DELETE FROM schedule WHERE user_id = @uid;
DELETE FROM task WHERE user_id = @uid;
DELETE FROM course WHERE user_id = @uid;

INSERT INTO course (user_id, course_name, teacher, classroom, weekday, start_section, end_section, start_week, end_week, color)
VALUES
(@uid, '软件质量保证与测试', '王老师', '综合楼 B204', WEEKDAY(CURDATE()) + 1, 1, 2, 1, 18, '#2563eb'),
(@uid, 'Java Web 应用开发', '李老师', '实验楼 A301', WEEKDAY(CURDATE()) + 1, 3, 4, 1, 18, '#16a34a'),
(@uid, '数据库系统原理', '陈老师', '逸夫楼 502', 1, 5, 6, 1, 18, '#f59e0b'),
(@uid, '软件工程导论', '赵老师', '教学楼 C106', 2, 1, 2, 1, 18, '#7c3aed'),
(@uid, '计算机网络', '周老师', '教学楼 D308', 3, 7, 8, 1, 18, '#06b6d4'),
(@uid, '大学英语', '孙老师', '文科楼 201', 4, 3, 4, 1, 18, '#ef4444');

SET @course_test = (SELECT id FROM course WHERE user_id = @uid AND course_name = '软件质量保证与测试' LIMIT 1);
SET @course_java = (SELECT id FROM course WHERE user_id = @uid AND course_name = 'Java Web 应用开发' LIMIT 1);
SET @course_db = (SELECT id FROM course WHERE user_id = @uid AND course_name = '数据库系统原理' LIMIT 1);
SET @course_se = (SELECT id FROM course WHERE user_id = @uid AND course_name = '软件工程导论' LIMIT 1);
SET @course_net = (SELECT id FROM course WHERE user_id = @uid AND course_name = '计算机网络' LIMIT 1);

INSERT INTO task (user_id, course_id, title, description, task_type, priority, status, deadline, completed_at)
VALUES
(@uid, @course_test, '整理 JUnit 单元测试截图', '汇总 mvn test 运行结果，补充白盒测试分析。', 'HOMEWORK', 'URGENT', 'IN_PROGRESS', DATE_ADD(NOW(), INTERVAL 6 HOUR), NULL),
(@uid, @course_java, '完成个人助手前端联调', '检查课程、任务、健康、消费和目标模块页面。', 'TODO', 'HIGH', 'TODO', DATE_ADD(NOW(), INTERVAL 1 DAY), NULL),
(@uid, @course_db, '数据库实验报告补充 ER 图', '整理 11 张核心业务表和用户隔离关系。', 'HOMEWORK', 'MEDIUM', 'TODO', DATE_ADD(NOW(), INTERVAL 3 DAY), NULL),
(@uid, @course_se, '软件工程课堂展示准备', '准备 5 分钟项目功能汇报。', 'TODO', 'HIGH', 'TODO', DATE_ADD(NOW(), INTERVAL 2 DAY), NULL),
(@uid, @course_net, '复习 TCP 拥塞控制', '完成课后选择题和简答题。', 'TODO', 'MEDIUM', 'COMPLETED', DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 2 HOUR)),
(@uid, @course_java, '接口异常路径测试', '补充登录失败、预算阈值、任务状态变更等场景。', 'TEST', 'HIGH', 'COMPLETED', DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY));

INSERT INTO schedule (user_id, title, description, location, start_time, end_time, schedule_type, remind_time)
VALUES
(@uid, '小组测试材料整理', '分配黑盒测试、接口测试和截图任务。', '图书馆研讨室 3', DATE_ADD(CURDATE(), INTERVAL 18 HOUR), DATE_ADD(CURDATE(), INTERVAL 20 HOUR), 'GROUP', DATE_ADD(CURDATE(), INTERVAL 17 HOUR)),
(@uid, '实验报告检查', '检查测试用例编号、运行结果和截图引用是否一致。', '宿舍', DATE_ADD(CURDATE(), INTERVAL 21 HOUR), DATE_ADD(CURDATE(), INTERVAL 22 HOUR), 'STUDY', DATE_ADD(CURDATE(), INTERVAL 20 HOUR)),
(@uid, '体育锻炼', '慢跑和拉伸。', '操场', DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL 7 HOUR), DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL 8 HOUR), 'HEALTH', DATE_ADD(DATE_ADD(CURDATE(), INTERVAL 1 DAY), INTERVAL 6 HOUR));

INSERT INTO study_record (user_id, course_id, study_date, duration_minutes, content)
VALUES
(@uid, @course_test, CURDATE(), 95, '梳理白盒测试路径、完善测试报告。'),
(@uid, @course_java, CURDATE(), 80, '调试前后端接口联通和页面数据展示。'),
(@uid, @course_db, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 70, '复习索引、事务和外键约束。'),
(@uid, @course_test, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 110, '设计单元测试用例和边界值。'),
(@uid, @course_java, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 120, '完成 Controller 与 Service 联调。'),
(@uid, @course_se, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 60, '整理需求分析和模块划分。'),
(@uid, @course_net, DATE_SUB(CURDATE(), INTERVAL 4 DAY), 75, '复习网络分层和可靠传输。'),
(@uid, @course_db, DATE_SUB(CURDATE(), INTERVAL 5 DAY), 90, '完成 SQL 练习题。');

INSERT INTO health_record (user_id, record_date, weather, sleep_hours, water_ml, exercise_minutes, mood_score, stress_score, note)
VALUES
(@uid, CURDATE(), '多云 26℃', 7.2, 1800, 35, 8, 4, '状态不错，适合完成报告收尾。'),
(@uid, DATE_SUB(CURDATE(), INTERVAL 1 DAY), '晴 29℃', 6.8, 1600, 20, 7, 5, '联调时间较长，晚上需要早点休息。'),
(@uid, DATE_SUB(CURDATE(), INTERVAL 2 DAY), '小雨 24℃', 7.5, 2000, 45, 8, 3, '运动后学习效率较高。'),
(@uid, DATE_SUB(CURDATE(), INTERVAL 3 DAY), '阴 25℃', 6.5, 1500, 15, 6, 6, 'DDL 较多，压力略高。');

INSERT INTO finance_record (user_id, record_type, category, amount, description, record_date)
VALUES
(@uid, 'INCOME', '生活费', 1800.00, '月初生活费', DATE_SUB(CURDATE(), INTERVAL 5 DAY)),
(@uid, 'EXPENSE', '餐饮', 28.00, '午餐', CURDATE()),
(@uid, 'EXPENSE', '餐饮', 34.50, '晚餐', DATE_SUB(CURDATE(), INTERVAL 1 DAY)),
(@uid, 'EXPENSE', '学习', 56.00, '打印报告和资料', CURDATE()),
(@uid, 'EXPENSE', '交通', 12.00, '地铁', DATE_SUB(CURDATE(), INTERVAL 2 DAY)),
(@uid, 'EXPENSE', '日用', 89.00, '宿舍用品', DATE_SUB(CURDATE(), INTERVAL 3 DAY)),
(@uid, 'EXPENSE', '娱乐', 45.00, '周末电影', DATE_SUB(CURDATE(), INTERVAL 4 DAY));

INSERT INTO budget (user_id, month, category, amount, warning_threshold)
VALUES
(@uid, DATE_FORMAT(CURDATE(), '%Y-%m'), '餐饮', 600.00, 80.00),
(@uid, DATE_FORMAT(CURDATE(), '%Y-%m'), '学习', 120.00, 80.00),
(@uid, DATE_FORMAT(CURDATE(), '%Y-%m'), '交通', 150.00, 80.00),
(@uid, DATE_FORMAT(CURDATE(), '%Y-%m'), '娱乐', 200.00, 80.00),
(@uid, DATE_FORMAT(CURDATE(), '%Y-%m'), '日用', 180.00, 80.00);

INSERT INTO goal (user_id, title, description, goal_type, target_value, current_value, unit, start_date, end_date, status)
VALUES
(@uid, '完成软件测试实验', '完成白盒测试、黑盒测试、报告和成果物整理。', 'STUDY', 100.00, 82.00, '%', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_ADD(CURDATE(), INTERVAL 4 DAY), 'ACTIVE'),
(@uid, 'Java Web 项目完善', '补齐数据展示、接口联调和演示数据。', 'PROJECT', 100.00, 76.00, '%', DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'ACTIVE'),
(@uid, '本周学习时长', '保持每天有效学习 2 小时以上。', 'HABIT', 14.00, 8.50, '小时', DATE_SUB(CURDATE(), INTERVAL 4 DAY), DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'ACTIVE');

INSERT INTO habit (user_id, habit_name, description, frequency, target_count, status)
VALUES
(@uid, '每日复盘', '记录当天完成事项和第二天计划。', 'DAILY', 1, 'ACTIVE'),
(@uid, '喝水 1800ml', '保持基础饮水量。', 'DAILY', 1, 'ACTIVE'),
(@uid, '英语单词', '每天背诵 30 个四级词汇。', 'DAILY', 1, 'ACTIVE'),
(@uid, '运动打卡', '慢跑、跳绳或力量训练。', 'DAILY', 1, 'ACTIVE');

SET @habit_review = (SELECT id FROM habit WHERE user_id = @uid AND habit_name = '每日复盘' LIMIT 1);
SET @habit_water = (SELECT id FROM habit WHERE user_id = @uid AND habit_name = '喝水 1800ml' LIMIT 1);
SET @habit_words = (SELECT id FROM habit WHERE user_id = @uid AND habit_name = '英语单词' LIMIT 1);
SET @habit_sport = (SELECT id FROM habit WHERE user_id = @uid AND habit_name = '运动打卡' LIMIT 1);

INSERT INTO habit_checkin (user_id, habit_id, checkin_date, checkin_count, note)
VALUES
(@uid, @habit_review, CURDATE(), 1, '已完成今日复盘。'),
(@uid, @habit_water, CURDATE(), 1, '饮水目标完成。'),
(@uid, @habit_words, CURDATE(), 1, '完成 30 个单词。'),
(@uid, @habit_review, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 1, '整理实验报告修改点。'),
(@uid, @habit_water, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 1, '完成。'),
(@uid, @habit_sport, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 1, '慢跑 20 分钟。'),
(@uid, @habit_review, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 1, '复盘接口测试截图。'),
(@uid, @habit_water, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 1, '完成。'),
(@uid, @habit_words, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 1, '完成。'),
(@uid, @habit_review, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, '完成。'),
(@uid, @habit_water, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, '完成。'),
(@uid, @habit_sport, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, '操场慢跑。');
