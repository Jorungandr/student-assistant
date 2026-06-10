package com.example.studentassistant.dto.dashboard;

import com.example.studentassistant.dto.budget.BudgetWarningResponse;
import com.example.studentassistant.dto.habit.HabitStatisticsResponse;
import com.example.studentassistant.dto.task.TaskStatisticsResponse;
import com.example.studentassistant.entity.Course;
import com.example.studentassistant.entity.Goal;
import com.example.studentassistant.entity.HealthRecord;
import com.example.studentassistant.entity.Task;

import java.util.List;

public class DashboardSummaryResponse {
    private List<Course> todayCourses;
    private List<Task> todoTasks;
    private HealthRecord healthOverview;
    private List<BudgetWarningResponse> budgetOverview;
    private List<Goal> goalProgress;
    private TaskStatisticsResponse taskStatistics;
    private HabitStatisticsResponse habitStatistics;
    private List<String> statusFlags;

    public List<Course> getTodayCourses() { return todayCourses; }
    public void setTodayCourses(List<Course> todayCourses) { this.todayCourses = todayCourses; }
    public List<Task> getTodoTasks() { return todoTasks; }
    public void setTodoTasks(List<Task> todoTasks) { this.todoTasks = todoTasks; }
    public HealthRecord getHealthOverview() { return healthOverview; }
    public void setHealthOverview(HealthRecord healthOverview) { this.healthOverview = healthOverview; }
    public List<BudgetWarningResponse> getBudgetOverview() { return budgetOverview; }
    public void setBudgetOverview(List<BudgetWarningResponse> budgetOverview) { this.budgetOverview = budgetOverview; }
    public List<Goal> getGoalProgress() { return goalProgress; }
    public void setGoalProgress(List<Goal> goalProgress) { this.goalProgress = goalProgress; }
    public TaskStatisticsResponse getTaskStatistics() { return taskStatistics; }
    public void setTaskStatistics(TaskStatisticsResponse taskStatistics) { this.taskStatistics = taskStatistics; }
    public HabitStatisticsResponse getHabitStatistics() { return habitStatistics; }
    public void setHabitStatistics(HabitStatisticsResponse habitStatistics) { this.habitStatistics = habitStatistics; }
    public List<String> getStatusFlags() { return statusFlags; }
    public void setStatusFlags(List<String> statusFlags) { this.statusFlags = statusFlags; }
}

