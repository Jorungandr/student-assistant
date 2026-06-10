package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.goal.GoalRequest;
import com.example.studentassistant.dto.goal.UpdateGoalProgressRequest;
import com.example.studentassistant.entity.Goal;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    private final GoalService service;

    public GoalController(GoalService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<Goal>> list() { return ApiResponse.success(service.list(SecurityUtils.currentUserId())); }

    @PostMapping
    public ApiResponse<Goal> create(@Valid @RequestBody GoalRequest request) { return ApiResponse.success(service.create(SecurityUtils.currentUserId(), request)); }

    @PutMapping("/{id}")
    public ApiResponse<Goal> update(@PathVariable Long id, @Valid @RequestBody GoalRequest request) { return ApiResponse.success(service.update(SecurityUtils.currentUserId(), id, request)); }

    @PutMapping("/{id}/progress")
    public ApiResponse<Goal> updateProgress(@PathVariable Long id, @Valid @RequestBody UpdateGoalProgressRequest request) { return ApiResponse.success(service.updateProgress(SecurityUtils.currentUserId(), id, request.getCurrentValue())); }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { service.delete(SecurityUtils.currentUserId(), id); return ApiResponse.success(); }
}

