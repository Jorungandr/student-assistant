CREATE DATABASE IF NOT EXISTS student_assistant
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE student_assistant;

DROP TABLE IF EXISTS habit_checkin;
DROP TABLE IF EXISTS habit;
DROP TABLE IF EXISTS goal;
DROP TABLE IF EXISTS budget;
DROP TABLE IF EXISTS finance_record;
DROP TABLE IF EXISTS health_record;
DROP TABLE IF EXISTS study_record;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    email VARCHAR(100) NULL,
    avatar VARCHAR(255) NULL,
    gender VARCHAR(20) NULL,
    status TINYINT NOT NULL DEFAULT 1,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_sys_user_username (username),
    KEY idx_sys_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    teacher VARCHAR(50) NULL,
    classroom VARCHAR(100) NULL,
    weekday TINYINT NOT NULL,
    start_section TINYINT NOT NULL,
    end_section TINYINT NOT NULL,
    start_week TINYINT NOT NULL DEFAULT 1,
    end_week TINYINT NOT NULL DEFAULT 18,
    color VARCHAR(20) NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_course_user_id (user_id),
    KEY idx_course_user_weekday (user_id, weekday),
    CONSTRAINT fk_course_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_id BIGINT NULL,
    title VARCHAR(120) NOT NULL,
    description TEXT NULL,
    task_type VARCHAR(30) NOT NULL DEFAULT 'TODO',
    priority VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    status VARCHAR(20) NOT NULL DEFAULT 'TODO',
    deadline DATETIME NULL,
    completed_at DATETIME NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_task_user_id (user_id),
    KEY idx_task_course_id (course_id),
    KEY idx_task_user_deadline (user_id, deadline),
    KEY idx_task_user_status (user_id, status),
    CONSTRAINT fk_task_user FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_task_course FOREIGN KEY (course_id) REFERENCES course (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(120) NOT NULL,
    description TEXT NULL,
    location VARCHAR(120) NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    schedule_type VARCHAR(30) NOT NULL DEFAULT 'NORMAL',
    remind_time DATETIME NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_schedule_user_id (user_id),
    KEY idx_schedule_user_start_time (user_id, start_time),
    CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE study_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    course_id BIGINT NULL,
    study_date DATE NOT NULL,
    duration_minutes INT NOT NULL,
    content VARCHAR(500) NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_study_record_user_id (user_id),
    KEY idx_study_record_course_id (course_id),
    KEY idx_study_record_user_date (user_id, study_date),
    CONSTRAINT fk_study_record_user FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_study_record_course FOREIGN KEY (course_id) REFERENCES course (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    record_date DATE NOT NULL,
    weather VARCHAR(50) NULL,
    sleep_hours DECIMAL(4,1) NULL,
    water_ml INT NULL,
    exercise_minutes INT NULL,
    mood_score TINYINT NULL,
    stress_score TINYINT NULL,
    note VARCHAR(500) NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_health_record_user_date (user_id, record_date, deleted),
    KEY idx_health_record_user_id (user_id),
    CONSTRAINT fk_health_record_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE finance_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    record_type VARCHAR(20) NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255) NULL,
    record_date DATE NOT NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_finance_record_user_id (user_id),
    KEY idx_finance_record_user_date (user_id, record_date),
    KEY idx_finance_record_user_category (user_id, category),
    CONSTRAINT fk_finance_record_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE budget (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    month CHAR(7) NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    warning_threshold DECIMAL(5,2) NOT NULL DEFAULT 80.00,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_budget_user_month_category (user_id, month, category, deleted),
    KEY idx_budget_user_id (user_id),
    KEY idx_budget_user_month (user_id, month),
    CONSTRAINT fk_budget_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE goal (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(120) NOT NULL,
    description TEXT NULL,
    goal_type VARCHAR(30) NOT NULL DEFAULT 'CUSTOM',
    target_value DECIMAL(10,2) NOT NULL DEFAULT 100.00,
    current_value DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    unit VARCHAR(20) NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_goal_user_id (user_id),
    KEY idx_goal_user_status (user_id, status),
    KEY idx_goal_user_end_date (user_id, end_date),
    CONSTRAINT fk_goal_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE habit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    habit_name VARCHAR(100) NOT NULL,
    description VARCHAR(500) NULL,
    frequency VARCHAR(20) NOT NULL DEFAULT 'DAILY',
    target_count INT NOT NULL DEFAULT 1,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_habit_user_id (user_id),
    KEY idx_habit_user_status (user_id, status),
    CONSTRAINT fk_habit_user FOREIGN KEY (user_id) REFERENCES sys_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE habit_checkin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    habit_id BIGINT NOT NULL,
    checkin_date DATE NOT NULL,
    checkin_count INT NOT NULL DEFAULT 1,
    note VARCHAR(500) NULL,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_habit_checkin_habit_date (habit_id, checkin_date, deleted),
    KEY idx_habit_checkin_user_id (user_id),
    KEY idx_habit_checkin_habit_id (habit_id),
    KEY idx_habit_checkin_user_date (user_id, checkin_date),
    CONSTRAINT fk_habit_checkin_user FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_habit_checkin_habit FOREIGN KEY (habit_id) REFERENCES habit (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
