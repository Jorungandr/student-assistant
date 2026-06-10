package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.schedule.ScheduleRequest;
import com.example.studentassistant.entity.Schedule;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.ScheduleMapper;
import com.example.studentassistant.service.ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleMapper scheduleMapper;

    public ScheduleServiceImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public List<Schedule> list(Long userId) {
        return scheduleMapper.selectList(new LambdaQueryWrapper<Schedule>()
                .eq(Schedule::getUserId, userId)
                .orderByAsc(Schedule::getStartTime));
    }

    @Override
    @Transactional
    public Schedule create(Long userId, ScheduleRequest request) {
        Schedule schedule = new Schedule();
        schedule.setUserId(userId);
        copy(request, schedule);
        scheduleMapper.insert(schedule);
        return schedule;
    }

    @Override
    @Transactional
    public Schedule update(Long userId, Long id, ScheduleRequest request) {
        Schedule schedule = requireOwned(userId, id);
        copy(request, schedule);
        scheduleMapper.updateById(schedule);
        return schedule;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        scheduleMapper.deleteById(id);
    }

    private Schedule requireOwned(Long userId, Long id) {
        Schedule schedule = scheduleMapper.selectById(id);
        if (schedule == null || !userId.equals(schedule.getUserId())) {
            throw new BusinessException(404, "日程不存在");
        }
        return schedule;
    }

    private void copy(ScheduleRequest request, Schedule schedule) {
        schedule.setTitle(request.getTitle());
        schedule.setDescription(request.getDescription());
        schedule.setLocation(request.getLocation());
        schedule.setStartTime(request.getStartTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setScheduleType(request.getScheduleType() == null ? "NORMAL" : request.getScheduleType());
        schedule.setRemindTime(request.getRemindTime());
    }
}

