package com.example.studentassistant.service;

import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.finance.FinanceRecordRequest;
import com.example.studentassistant.entity.FinanceRecord;

import java.util.List;

public interface FinanceRecordService {
    List<FinanceRecord> list(Long userId);
    FinanceRecord create(Long userId, FinanceRecordRequest request);
    FinanceRecord update(Long userId, Long id, FinanceRecordRequest request);
    void delete(Long userId, Long id);
    List<NameValueResponse> statistics(Long userId);
}

