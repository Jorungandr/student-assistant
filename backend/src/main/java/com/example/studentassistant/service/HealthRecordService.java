package com.example.studentassistant.service;

import com.example.studentassistant.dto.health.HealthRecordRequest;
import com.example.studentassistant.entity.HealthRecord;

import java.util.List;

public interface HealthRecordService {
    List<HealthRecord> list(Long userId);
    HealthRecord today(Long userId);
    HealthRecord create(Long userId, HealthRecordRequest request);
    HealthRecord update(Long userId, Long id, HealthRecordRequest request);
    void delete(Long userId, Long id);
    List<HealthRecord> statistics(Long userId);
}

