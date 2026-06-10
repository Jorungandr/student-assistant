package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.habit.HabitCheckinRequest;
import com.example.studentassistant.dto.habit.HabitRequest;
import com.example.studentassistant.dto.habit.HabitStatisticsResponse;
import com.example.studentassistant.entity.Habit;
import com.example.studentassistant.entity.HabitCheckin;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService service;

    public HabitController(HabitService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<Habit>> list() { return ApiResponse.success(service.list(SecurityUtils.currentUserId())); }

    @GetMapping("/statistics")
    public ApiResponse<HabitStatisticsResponse> statistics() { return ApiResponse.success(service.statistics(SecurityUtils.currentUserId())); }

    @PostMapping
    public ApiResponse<Habit> create(@Valid @RequestBody HabitRequest request) { return ApiResponse.success(service.create(SecurityUtils.currentUserId(), request)); }

    @PutMapping("/{id}")
    public ApiResponse<Habit> update(@PathVariable Long id, @Valid @RequestBody HabitRequest request) { return ApiResponse.success(service.update(SecurityUtils.currentUserId(), id, request)); }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { service.delete(SecurityUtils.currentUserId(), id); return ApiResponse.success(); }

    @PostMapping("/{id}/checkin")
    public ApiResponse<HabitCheckin> checkin(@PathVariable Long id, @Valid @RequestBody HabitCheckinRequest request) { return ApiResponse.success(service.checkin(SecurityUtils.currentUserId(), id, request)); }

    @GetMapping("/{id}/checkins")
    public ApiResponse<List<HabitCheckin>> checkins(@PathVariable Long id) { return ApiResponse.success(service.checkins(SecurityUtils.currentUserId(), id)); }
}

