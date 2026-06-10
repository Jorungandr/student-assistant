package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.habit.HabitCheckinRequest;
import com.example.studentassistant.dto.habit.HabitRequest;
import com.example.studentassistant.entity.Habit;
import com.example.studentassistant.entity.HabitCheckin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HabitServiceTest {
    @Autowired private AuthService authService;
    @Autowired private HabitService habitService;

    @Test
    void habitCheckinCreatesOrUpdatesTodayRecord() {
        Long userId = createUser("habit_checkin");
        Habit habit = habitService.create(userId, habit("早起"));

        habitService.checkin(userId, habit.getId(), checkin(LocalDate.now(), 1));
        HabitCheckin updated = habitService.checkin(userId, habit.getId(), checkin(LocalDate.now(), 2));

        assertThat(updated.getCheckinCount()).isEqualTo(3);
    }

    @Test
    void continuousCheckinDaysCalculation() {
        Long userId = createUser("habit_streak");
        Habit habit = habitService.create(userId, habit("背单词"));
        habitService.checkin(userId, habit.getId(), checkin(LocalDate.now(), 1));
        habitService.checkin(userId, habit.getId(), checkin(LocalDate.now().minusDays(1), 1));
        habitService.checkin(userId, habit.getId(), checkin(LocalDate.now().minusDays(2), 1));

        assertThat(habitService.continuousDays(userId, habit.getId())).isEqualTo(3);
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private HabitRequest habit(String name) {
        HabitRequest request = new HabitRequest();
        request.setHabitName(name);
        request.setFrequency("DAILY");
        request.setTargetCount(1);
        return request;
    }

    private HabitCheckinRequest checkin(LocalDate date, int count) {
        HabitCheckinRequest request = new HabitCheckinRequest();
        request.setCheckinDate(date);
        request.setCheckinCount(count);
        return request;
    }
}

