package com.example.studentassistant.service;

import com.example.studentassistant.dto.dashboard.DashboardSummaryResponse;

public interface DashboardService {
    DashboardSummaryResponse summary(Long userId);
}

