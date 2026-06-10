package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.budget.BudgetRequest;
import com.example.studentassistant.dto.budget.BudgetWarningResponse;
import com.example.studentassistant.entity.Budget;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService service;

    public BudgetController(BudgetService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<Budget>> list() { return ApiResponse.success(service.list(SecurityUtils.currentUserId())); }

    @GetMapping("/warnings")
    public ApiResponse<List<BudgetWarningResponse>> warnings() { return ApiResponse.success(service.warnings(SecurityUtils.currentUserId())); }

    @PostMapping
    public ApiResponse<Budget> create(@Valid @RequestBody BudgetRequest request) { return ApiResponse.success(service.create(SecurityUtils.currentUserId(), request)); }

    @PutMapping("/{id}")
    public ApiResponse<Budget> update(@PathVariable Long id, @Valid @RequestBody BudgetRequest request) { return ApiResponse.success(service.update(SecurityUtils.currentUserId(), id, request)); }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { service.delete(SecurityUtils.currentUserId(), id); return ApiResponse.success(); }
}

