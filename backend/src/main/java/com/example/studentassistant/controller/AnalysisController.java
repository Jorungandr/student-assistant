package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.analysis.DateValueResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.analysis.TaskCompletionResponse;
import com.example.studentassistant.entity.Task;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.AnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {
    private final AnalysisService service;

    public AnalysisController(AnalysisService service) { this.service = service; }

    @GetMapping("/study-duration")
    public ApiResponse<List<DateValueResponse>> studyDuration() { return ApiResponse.success(service.studyDuration(SecurityUtils.currentUserId())); }

    @GetMapping("/task-completion")
    public ApiResponse<TaskCompletionResponse> taskCompletion() { return ApiResponse.success(service.taskCompletion(SecurityUtils.currentUserId())); }

    @GetMapping("/course-ratio")
    public ApiResponse<List<NameValueResponse>> courseRatio() { return ApiResponse.success(service.courseRatio(SecurityUtils.currentUserId())); }

    @GetMapping("/priority-tasks")
    public ApiResponse<List<Task>> priorityTasks() { return ApiResponse.success(service.priorityTasks(SecurityUtils.currentUserId())); }
}

