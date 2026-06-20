package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.goal.GoalRequest;
import com.example.studentassistant.entity.Goal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GoalServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private GoalService goalService;

    @Test
    void updatingProgressToTargetCompletesGoal() {
        Long userId = createUser("goal_progress");
        Goal goal = goalService.create(userId, goalRequest());

        Goal updated = goalService.updateProgress(userId, goal.getId(), new BigDecimal("100"));

        assertThat(updated.getCurrentValue()).isEqualByComparingTo(new BigDecimal("100"));
        assertThat(updated.getStatus()).isEqualTo("COMPLETED");
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private GoalRequest goalRequest() {
        GoalRequest request = new GoalRequest();
        request.setTitle("完成测试讨论");
        request.setDescription("完成讨论报告和PPT");
        request.setGoalType("STUDY");
        request.setTargetValue(new BigDecimal("100"));
        request.setCurrentValue(BigDecimal.ZERO);
        request.setUnit("%");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now().plusDays(7));
        request.setStatus("ACTIVE");
        return request;
    }
}
