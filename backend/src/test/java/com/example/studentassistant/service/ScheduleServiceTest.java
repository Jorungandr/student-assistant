package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.schedule.ScheduleRequest;
import com.example.studentassistant.entity.Schedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ScheduleServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private ScheduleService scheduleService;

    @Test
    void schedulesAreListedByStartTime() {
        Long userId = createUser("schedule_order");
        LocalDateTime base = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);

        scheduleService.create(userId, scheduleRequest("下午讨论", base.plusHours(6)));
        scheduleService.create(userId, scheduleRequest("上午讨论", base));

        assertThat(scheduleService.list(userId))
                .extracting(Schedule::getTitle)
                .containsExactly("上午讨论", "下午讨论");
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private ScheduleRequest scheduleRequest(String title, LocalDateTime startTime) {
        ScheduleRequest request = new ScheduleRequest();
        request.setTitle(title);
        request.setDescription("讨论任务安排");
        request.setLocation("自习室");
        request.setStartTime(startTime);
        request.setEndTime(startTime.plusHours(1));
        request.setScheduleType("STUDY");
        return request;
    }
}
