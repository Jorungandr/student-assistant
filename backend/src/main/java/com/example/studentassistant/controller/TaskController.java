package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.task.TaskRequest;
import com.example.studentassistant.dto.task.TaskStatisticsResponse;
import com.example.studentassistant.dto.task.UpdateTaskStatusRequest;
import com.example.studentassistant.entity.Task;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ApiResponse<List<Task>> list() {
        return ApiResponse.success(taskService.list(SecurityUtils.currentUserId()));
    }

    @GetMapping("/upcoming")
    public ApiResponse<List<Task>> upcoming() {
        return ApiResponse.success(taskService.upcoming(SecurityUtils.currentUserId()));
    }

    @GetMapping("/statistics")
    public ApiResponse<TaskStatisticsResponse> statistics() {
        return ApiResponse.success(taskService.statistics(SecurityUtils.currentUserId()));
    }

    @PostMapping
    public ApiResponse<Task> create(@Valid @RequestBody TaskRequest request) {
        return ApiResponse.success(taskService.create(SecurityUtils.currentUserId(), request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Task> update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return ApiResponse.success(taskService.update(SecurityUtils.currentUserId(), id, request));
    }

    @PutMapping("/{id}/status")
    public ApiResponse<Task> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        return ApiResponse.success(taskService.updateStatus(SecurityUtils.currentUserId(), id, request.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        taskService.delete(SecurityUtils.currentUserId(), id);
        return ApiResponse.success();
    }
}

