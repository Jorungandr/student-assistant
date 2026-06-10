package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.finance.FinanceRecordRequest;
import com.example.studentassistant.entity.FinanceRecord;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.FinanceRecordMapper;
import com.example.studentassistant.service.FinanceRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceRecordServiceImpl implements FinanceRecordService {
    private final FinanceRecordMapper mapper;

    public FinanceRecordServiceImpl(FinanceRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<FinanceRecord> list(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<FinanceRecord>().eq(FinanceRecord::getUserId, userId).orderByDesc(FinanceRecord::getRecordDate));
    }

    @Transactional
    public FinanceRecord create(Long userId, FinanceRecordRequest request) {
        FinanceRecord record = new FinanceRecord();
        record.setUserId(userId);
        copy(request, record);
        mapper.insert(record);
        return record;
    }

    @Transactional
    public FinanceRecord update(Long userId, Long id, FinanceRecordRequest request) {
        FinanceRecord record = requireOwned(userId, id);
        copy(request, record);
        mapper.updateById(record);
        return record;
    }

    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        mapper.deleteById(id);
    }

    public List<NameValueResponse> statistics(Long userId) {
        return list(userId).stream()
                .filter(record -> "EXPENSE".equals(record.getRecordType()))
                .collect(Collectors.groupingBy(FinanceRecord::getCategory, Collectors.mapping(FinanceRecord::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))))
                .entrySet().stream()
                .map(entry -> new NameValueResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    private FinanceRecord requireOwned(Long userId, Long id) {
        FinanceRecord record = mapper.selectById(id);
        if (record == null || !userId.equals(record.getUserId())) throw new BusinessException(404, "收支记录不存在");
        return record;
    }

    private void copy(FinanceRecordRequest request, FinanceRecord record) {
        record.setRecordType(request.getRecordType());
        record.setCategory(request.getCategory());
        record.setAmount(request.getAmount());
        record.setDescription(request.getDescription());
        record.setRecordDate(request.getRecordDate());
    }
}

