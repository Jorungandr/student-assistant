package com.example.studentassistant.service.impl;

import com.example.studentassistant.dto.budget.BudgetWarningResponse;
import com.example.studentassistant.dto.dashboard.DashboardSummaryResponse;
import com.example.studentassistant.entity.Goal;
import com.example.studentassistant.entity.HealthRecord;
import com.example.studentassistant.entity.Task;
import com.example.studentassistant.service.BudgetService;
import com.example.studentassistant.service.CourseService;
import com.example.studentassistant.service.DashboardService;
import com.example.studentassistant.service.GoalService;
import com.example.studentassistant.service.HabitService;
import com.example.studentassistant.service.HealthRecordService;
import com.example.studentassistant.service.TaskService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final CourseService courseService;
    private final TaskService taskService;
    private final HealthRecordService healthRecordService;
    private final BudgetService budgetService;
    private final GoalService goalService;
    private final HabitService habitService;

    public DashboardServiceImpl(
            CourseService courseService,
            TaskService taskService,
            HealthRecordService healthRecordService,
            BudgetService budgetService,
            GoalService goalService,
            HabitService habitService
    ) {
        this.courseService = courseService;
        this.taskService = taskService;
        this.healthRecordService = healthRecordService;
        this.budgetService = budgetService;
        this.goalService = goalService;
        this.habitService = habitService;
    }

    public DashboardSummaryResponse summary(Long userId) {
        DashboardSummaryResponse response = new DashboardSummaryResponse();
        List<Task> upcomingTasks = taskService.upcoming(userId);
        HealthRecord health = healthRecordService.today(userId);
        List<BudgetWarningResponse> budgetWarnings = budgetService.warnings(userId);
        List<Goal> goals = goalService.list(userId);

        response.setTodayCourses(courseService.today(userId));
        response.setTodoTasks(upcomingTasks);
        response.setHealthOverview(health);
        response.setBudgetOverview(budgetWarnings);
        response.setGoalProgress(goals);
        response.setTaskStatistics(taskService.statistics(userId));
        response.setHabitStatistics(habitService.statistics(userId));
        response.setStatusFlags(buildStatusFlags(upcomingTasks, health, budgetWarnings, goals));
        return response;
    }

    private List<String> buildStatusFlags(List<Task> tasks, HealthRecord health, List<BudgetWarningResponse> budgets, List<Goal> goals) {
        List<String> flags = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        if (tasks.stream().anyMatch(task -> task.getDeadline() != null && ChronoUnit.HOURS.between(now, task.getDeadline()) <= 24 && task.getDeadline().isAfter(now))) {
            flags.add("有任务即将截止");
        }
        if (tasks.stream().anyMatch(task -> task.getDeadline() != null && task.getDeadline().isBefore(now) && !"COMPLETED".equals(task.getStatus()))) {
            flags.add("存在已逾期任务");
        }
        if (health == null) {
            flags.add("今日尚未记录健康数据");
        }
        if (budgets.stream().anyMatch(budget -> "WARNING".equals(budget.getStatus()) || "EXCEEDED".equals(budget.getStatus()))) {
            flags.add("预算接近或超过上限");
        }
        if (goals.stream().anyMatch(this::goalBehindSchedule)) {
            flags.add("部分目标进度偏低");
        }
        return flags;
    }

    private boolean goalBehindSchedule(Goal goal) {
        if (!"ACTIVE".equals(goal.getStatus()) || goal.getTargetValue().compareTo(BigDecimal.ZERO) <= 0) return false;
        long totalDays = Math.max(1, ChronoUnit.DAYS.between(goal.getStartDate(), goal.getEndDate()));
        long elapsedDays = Math.max(0, ChronoUnit.DAYS.between(goal.getStartDate(), LocalDate.now()));
        BigDecimal expected = BigDecimal.valueOf(elapsedDays).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(totalDays), 2, java.math.RoundingMode.HALF_UP);
        BigDecimal actual = goal.getCurrentValue().multiply(BigDecimal.valueOf(100)).divide(goal.getTargetValue(), 2, java.math.RoundingMode.HALF_UP);
        return actual.compareTo(expected) < 0;
    }
}

