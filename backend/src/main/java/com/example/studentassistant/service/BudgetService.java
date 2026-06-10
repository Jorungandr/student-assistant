package com.example.studentassistant.service;

import com.example.studentassistant.dto.budget.BudgetRequest;
import com.example.studentassistant.dto.budget.BudgetWarningResponse;
import com.example.studentassistant.entity.Budget;

import java.util.List;

public interface BudgetService {
    List<Budget> list(Long userId);
    Budget create(Long userId, BudgetRequest request);
    Budget update(Long userId, Long id, BudgetRequest request);
    void delete(Long userId, Long id);
    List<BudgetWarningResponse> warnings(Long userId);
}

