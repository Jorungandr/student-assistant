package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.task.TaskRequest;
import com.example.studentassistant.dto.task.TaskStatisticsResponse;
import com.example.studentassistant.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private TaskService taskService;

    @Test
    void taskStatusCanChangeToCompleted() {
        Long userId = createUser("task_status");
        Task task = taskService.create(userId, taskRequest("完成实验报告", LocalDateTime.now().plusDays(1)));

        Task updated = taskService.updateStatus(userId, task.getId(), "COMPLETED");

        assertThat(updated.getStatus()).isEqualTo("COMPLETED");
        assertThat(updated.getCompletedAt()).isNotNull();
    }

    @Test
    void overdueStatisticsCountOnlyCurrentUserRecords() {
        Long userId = createUser("task_owner");
        Long otherUserId = createUser("task_other");
        taskService.create(userId, taskRequest("我的逾期任务", LocalDateTime.now().minusDays(1)));
        taskService.create(otherUserId, taskRequest("他人的逾期任务", LocalDateTime.now().minusDays(1)));
        taskService.create(userId, taskRequest("未来任务", LocalDateTime.now().plusDays(1)));

        TaskStatisticsResponse statistics = taskService.statistics(userId);

        assertThat(statistics.getTotal()).isEqualTo(2);
        assertThat(statistics.getOverdue()).isEqualTo(1);
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private TaskRequest taskRequest(String title, LocalDateTime deadline) {
        TaskRequest request = new TaskRequest();
        request.setTitle(title);
        request.setDescription("测试任务");
        request.setTaskType("HOMEWORK");
        request.setPriority("HIGH");
        request.setStatus("TODO");
        request.setDeadline(deadline);
        return request;
    }
}

