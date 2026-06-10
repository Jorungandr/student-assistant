package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.finance.FinanceRecordRequest;
import com.example.studentassistant.entity.FinanceRecord;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.FinanceRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance-records")
public class FinanceRecordController {
    private final FinanceRecordService service;

    public FinanceRecordController(FinanceRecordService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<FinanceRecord>> list() { return ApiResponse.success(service.list(SecurityUtils.currentUserId())); }

    @GetMapping("/statistics")
    public ApiResponse<List<NameValueResponse>> statistics() { return ApiResponse.success(service.statistics(SecurityUtils.currentUserId())); }

    @PostMapping
    public ApiResponse<FinanceRecord> create(@Valid @RequestBody FinanceRecordRequest request) { return ApiResponse.success(service.create(SecurityUtils.currentUserId(), request)); }

    @PutMapping("/{id}")
    public ApiResponse<FinanceRecord> update(@PathVariable Long id, @Valid @RequestBody FinanceRecordRequest request) { return ApiResponse.success(service.update(SecurityUtils.currentUserId(), id, request)); }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { service.delete(SecurityUtils.currentUserId(), id); return ApiResponse.success(); }
}

