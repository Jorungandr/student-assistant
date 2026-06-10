package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.schedule.ScheduleRequest;
import com.example.studentassistant.entity.Schedule;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.ScheduleService;
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
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ApiResponse<List<Schedule>> list() {
        return ApiResponse.success(scheduleService.list(SecurityUtils.currentUserId()));
    }

    @PostMapping
    public ApiResponse<Schedule> create(@Valid @RequestBody ScheduleRequest request) {
        return ApiResponse.success(scheduleService.create(SecurityUtils.currentUserId(), request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Schedule> update(@PathVariable Long id, @Valid @RequestBody ScheduleRequest request) {
        return ApiResponse.success(scheduleService.update(SecurityUtils.currentUserId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        scheduleService.delete(SecurityUtils.currentUserId(), id);
        return ApiResponse.success();
    }
}

