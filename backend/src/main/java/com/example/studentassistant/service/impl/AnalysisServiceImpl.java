package com.example.studentassistant.service.impl;

import com.example.studentassistant.dto.analysis.DateValueResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.analysis.TaskCompletionResponse;
import com.example.studentassistant.entity.Task;
import com.example.studentassistant.service.AnalysisService;
import com.example.studentassistant.service.StudyRecordService;
import com.example.studentassistant.service.TaskService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    private final StudyRecordService studyRecordService;
    private final TaskService taskService;

    public AnalysisServiceImpl(StudyRecordService studyRecordService, TaskService taskService) {
        this.studyRecordService = studyRecordService;
        this.taskService = taskService;
    }

    public List<DateValueResponse> studyDuration(Long userId) {
        return studyRecordService.durationTrend(userId);
    }

    public TaskCompletionResponse taskCompletion(Long userId) {
        List<Task> tasks = taskService.list(userId);
        long completed = tasks.stream().filter(task -> "COMPLETED".equals(task.getStatus())).count();
        TaskCompletionResponse response = new TaskCompletionResponse();
        response.setTotal(tasks.size());
        response.setCompleted(completed);
        response.setCompletionRate(tasks.isEmpty() ? BigDecimal.ZERO : BigDecimal.valueOf(completed).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(tasks.size()), 2, RoundingMode.HALF_UP));
        return response;
    }

    public List<NameValueResponse> courseRatio(Long userId) {
        return studyRecordService.courseRatio(userId);
    }

    public List<Task> priorityTasks(Long userId) {
        return taskService.list(userId).stream()
                .filter(task -> !"COMPLETED".equals(task.getStatus()))
                .sorted(Comparator.comparing(this::priorityScore).reversed().thenComparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder())))
                .limit(10)
                .toList();
    }

    private int priorityScore(Task task) {
        return switch (task.getPriority()) {
            case "URGENT" -> 4;
            case "HIGH" -> 3;
            case "MEDIUM" -> 2;
            default -> 1;
        };
    }
}

