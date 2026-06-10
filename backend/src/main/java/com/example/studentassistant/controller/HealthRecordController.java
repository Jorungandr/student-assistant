package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.health.HealthRecordRequest;
import com.example.studentassistant.entity.HealthRecord;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.HealthRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-records")
public class HealthRecordController {
    private final HealthRecordService service;

    public HealthRecordController(HealthRecordService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<HealthRecord>> list() { return ApiResponse.success(service.list(SecurityUtils.currentUserId())); }

    @GetMapping("/today")
    public ApiResponse<HealthRecord> today() { return ApiResponse.success(service.today(SecurityUtils.currentUserId())); }

    @GetMapping("/statistics")
    public ApiResponse<List<HealthRecord>> statistics() { return ApiResponse.success(service.statistics(SecurityUtils.currentUserId())); }

    @PostMapping
    public ApiResponse<HealthRecord> create(@Valid @RequestBody HealthRecordRequest request) { return ApiResponse.success(service.create(SecurityUtils.currentUserId(), request)); }

    @PutMapping("/{id}")
    public ApiResponse<HealthRecord> update(@PathVariable Long id, @Valid @RequestBody HealthRecordRequest request) { return ApiResponse.success(service.update(SecurityUtils.currentUserId(), id, request)); }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { service.delete(SecurityUtils.currentUserId(), id); return ApiResponse.success(); }
}

