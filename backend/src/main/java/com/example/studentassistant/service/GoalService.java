package com.example.studentassistant.service;

import com.example.studentassistant.dto.goal.GoalRequest;
import com.example.studentassistant.entity.Goal;

import java.math.BigDecimal;
import java.util.List;

public interface GoalService {
    List<Goal> list(Long userId);
    Goal create(Long userId, GoalRequest request);
    Goal update(Long userId, Long id, GoalRequest request);
    Goal updateProgress(Long userId, Long id, BigDecimal currentValue);
    void delete(Long userId, Long id);
}

