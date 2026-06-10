package com.example.studentassistant.service;

import com.example.studentassistant.dto.task.TaskRequest;
import com.example.studentassistant.dto.task.TaskStatisticsResponse;
import com.example.studentassistant.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> list(Long userId);
    List<Task> upcoming(Long userId);
    Task create(Long userId, TaskRequest request);
    Task update(Long userId, Long id, TaskRequest request);
    Task updateStatus(Long userId, Long id, String status);
    TaskStatisticsResponse statistics(Long userId);
    void delete(Long userId, Long id);
}

