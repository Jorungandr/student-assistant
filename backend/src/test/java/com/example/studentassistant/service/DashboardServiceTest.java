package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.budget.BudgetRequest;
import com.example.studentassistant.dto.course.CourseRequest;
import com.example.studentassistant.dto.dashboard.DashboardSummaryResponse;
import com.example.studentassistant.dto.finance.FinanceRecordRequest;
import com.example.studentassistant.dto.goal.GoalRequest;
import com.example.studentassistant.dto.health.HealthRecordRequest;
import com.example.studentassistant.dto.task.TaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DashboardServiceTest {
    @Autowired private AuthService authService;
    @Autowired private CourseService courseService;
    @Autowired private TaskService taskService;
    @Autowired private HealthRecordService healthRecordService;
    @Autowired private BudgetService budgetService;
    @Autowired private FinanceRecordService financeRecordService;
    @Autowired private GoalService goalService;
    @Autowired private DashboardService dashboardService;

    @Test
    void summaryIncludesExpectedSections() {
        Long userId = createUser("dashboard");
        courseService.create(userId, course());
        taskService.create(userId, task());
        healthRecordService.create(userId, health());
        budgetService.create(userId, budget());
        financeRecordService.create(userId, expense());
        goalService.create(userId, goal());

        DashboardSummaryResponse summary = dashboardService.summary(userId);

        assertThat(summary.getTodayCourses()).isNotEmpty();
        assertThat(summary.getTodoTasks()).isNotEmpty();
        assertThat(summary.getHealthOverview()).isNotNull();
        assertThat(summary.getBudgetOverview()).isNotEmpty();
        assertThat(summary.getGoalProgress()).isNotEmpty();
        assertThat(summary.getTaskStatistics().getTotal()).isGreaterThan(0);
        assertThat(summary.getStatusFlags()).isNotNull();
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private CourseRequest course() {
        CourseRequest request = new CourseRequest();
        request.setCourseName("软件质量保证");
        request.setWeekday(LocalDate.now().getDayOfWeek().getValue());
        request.setStartSection(1);
        request.setEndSection(2);
        request.setStartWeek(1);
        request.setEndWeek(18);
        return request;
    }

    private TaskRequest task() {
        TaskRequest request = new TaskRequest();
        request.setTitle("提交测试报告");
        request.setStatus("TODO");
        request.setPriority("HIGH");
        request.setDeadline(LocalDateTime.now().plusHours(3));
        return request;
    }

    private HealthRecordRequest health() {
        HealthRecordRequest request = new HealthRecordRequest();
        request.setRecordDate(LocalDate.now());
        request.setWeather("晴");
        request.setSleepHours(new BigDecimal("7.5"));
        request.setWaterMl(1500);
        request.setExerciseMinutes(30);
        request.setMoodScore(8);
        request.setStressScore(4);
        return request;
    }

    private BudgetRequest budget() {
        BudgetRequest request = new BudgetRequest();
        request.setMonth(YearMonth.now().toString());
        request.setCategory("餐饮");
        request.setAmount(new BigDecimal("100.00"));
        request.setWarningThreshold(new BigDecimal("80.00"));
        return request;
    }

    private FinanceRecordRequest expense() {
        FinanceRecordRequest request = new FinanceRecordRequest();
        request.setRecordType("EXPENSE");
        request.setCategory("餐饮");
        request.setAmount(new BigDecimal("90.00"));
        request.setRecordDate(LocalDate.now());
        return request;
    }

    private GoalRequest goal() {
        GoalRequest request = new GoalRequest();
        request.setTitle("完成课程项目");
        request.setTargetValue(new BigDecimal("100"));
        request.setCurrentValue(new BigDecimal("20"));
        request.setStartDate(LocalDate.now().minusDays(10));
        request.setEndDate(LocalDate.now().plusDays(10));
        request.setStatus("ACTIVE");
        return request;
    }
}

