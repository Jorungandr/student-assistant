package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.health.HealthRecordRequest;
import com.example.studentassistant.entity.HealthRecord;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.HealthRecordMapper;
import com.example.studentassistant.service.HealthRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class HealthRecordServiceImpl implements HealthRecordService {
    private final HealthRecordMapper mapper;

    public HealthRecordServiceImpl(HealthRecordMapper mapper) {
        this.mapper = mapper;
    }

    public List<HealthRecord> list(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<HealthRecord>().eq(HealthRecord::getUserId, userId).orderByDesc(HealthRecord::getRecordDate));
    }

    public HealthRecord today(Long userId) {
        return mapper.selectOne(new LambdaQueryWrapper<HealthRecord>().eq(HealthRecord::getUserId, userId).eq(HealthRecord::getRecordDate, LocalDate.now()).last("LIMIT 1"));
    }

    @Transactional
    public HealthRecord create(Long userId, HealthRecordRequest request) {
        HealthRecord record = mapper.selectOne(new LambdaQueryWrapper<HealthRecord>()
                .eq(HealthRecord::getUserId, userId)
                .eq(HealthRecord::getRecordDate, request.getRecordDate())
                .last("LIMIT 1"));
        boolean exists = record != null;
        if (!exists) {
            record = new HealthRecord();
            record.setUserId(userId);
        }
        copy(request, record);
        if (exists) {
            mapper.updateById(record);
        } else {
            mapper.insert(record);
        }
        return record;
    }

    @Transactional
    public HealthRecord update(Long userId, Long id, HealthRecordRequest request) {
        HealthRecord record = requireOwned(userId, id);
        copy(request, record);
        mapper.updateById(record);
        return record;
    }

    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        mapper.deleteById(id);
    }

    public List<HealthRecord> statistics(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<HealthRecord>().eq(HealthRecord::getUserId, userId).orderByAsc(HealthRecord::getRecordDate).last("LIMIT 30"));
    }

    private HealthRecord requireOwned(Long userId, Long id) {
        HealthRecord record = mapper.selectById(id);
        if (record == null || !userId.equals(record.getUserId())) throw new BusinessException(404, "健康记录不存在");
        return record;
    }

    private void copy(HealthRecordRequest request, HealthRecord record) {
        record.setRecordDate(request.getRecordDate());
        record.setWeather(request.getWeather());
        record.setSleepHours(request.getSleepHours());
        record.setWaterMl(request.getWaterMl());
        record.setExerciseMinutes(request.getExerciseMinutes());
        record.setMoodScore(request.getMoodScore());
        record.setStressScore(request.getStressScore());
        record.setNote(request.getNote());
    }
}
