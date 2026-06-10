package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.habit.HabitCheckinRequest;
import com.example.studentassistant.dto.habit.HabitRequest;
import com.example.studentassistant.dto.habit.HabitStatisticsResponse;
import com.example.studentassistant.entity.Habit;
import com.example.studentassistant.entity.HabitCheckin;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.HabitCheckinMapper;
import com.example.studentassistant.mapper.HabitMapper;
import com.example.studentassistant.service.HabitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HabitServiceImpl implements HabitService {
    private final HabitMapper habitMapper;
    private final HabitCheckinMapper checkinMapper;

    public HabitServiceImpl(HabitMapper habitMapper, HabitCheckinMapper checkinMapper) {
        this.habitMapper = habitMapper;
        this.checkinMapper = checkinMapper;
    }

    public List<Habit> list(Long userId) {
        return habitMapper.selectList(new LambdaQueryWrapper<Habit>().eq(Habit::getUserId, userId).orderByDesc(Habit::getCreatedAt));
    }

    @Transactional
    public Habit create(Long userId, HabitRequest request) {
        Habit habit = new Habit();
        habit.setUserId(userId);
        copy(request, habit);
        habitMapper.insert(habit);
        return habit;
    }

    @Transactional
    public Habit update(Long userId, Long id, HabitRequest request) {
        Habit habit = requireOwned(userId, id);
        copy(request, habit);
        habitMapper.updateById(habit);
        return habit;
    }

    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        habitMapper.deleteById(id);
    }

    @Transactional
    public HabitCheckin checkin(Long userId, Long habitId, HabitCheckinRequest request) {
        requireOwned(userId, habitId);
        LocalDate date = request.getCheckinDate() == null ? LocalDate.now() : request.getCheckinDate();
        HabitCheckin checkin = checkinMapper.selectOne(new LambdaQueryWrapper<HabitCheckin>().eq(HabitCheckin::getHabitId, habitId).eq(HabitCheckin::getCheckinDate, date).last("LIMIT 1"));
        if (checkin == null) {
            checkin = new HabitCheckin();
            checkin.setUserId(userId);
            checkin.setHabitId(habitId);
            checkin.setCheckinDate(date);
            checkin.setCheckinCount(request.getCheckinCount() == null ? 1 : request.getCheckinCount());
            checkin.setNote(request.getNote());
            checkinMapper.insert(checkin);
        } else {
            checkin.setCheckinCount(checkin.getCheckinCount() + (request.getCheckinCount() == null ? 1 : request.getCheckinCount()));
            checkin.setNote(request.getNote());
            checkinMapper.updateById(checkin);
        }
        return checkin;
    }

    public List<HabitCheckin> checkins(Long userId, Long habitId) {
        requireOwned(userId, habitId);
        return checkinMapper.selectList(new LambdaQueryWrapper<HabitCheckin>().eq(HabitCheckin::getUserId, userId).eq(HabitCheckin::getHabitId, habitId).orderByDesc(HabitCheckin::getCheckinDate));
    }

    public HabitStatisticsResponse statistics(Long userId) {
        HabitStatisticsResponse response = new HabitStatisticsResponse();
        List<Habit> habits = list(userId);
        response.setHabitCount(habits.size());
        response.setTodayCheckinCount(checkinMapper.selectCount(new LambdaQueryWrapper<HabitCheckin>().eq(HabitCheckin::getUserId, userId).eq(HabitCheckin::getCheckinDate, LocalDate.now())));
        response.setLongestStreak(habits.stream().mapToInt(habit -> continuousDays(userId, habit.getId())).max().orElse(0));
        return response;
    }

    public int continuousDays(Long userId, Long habitId) {
        requireOwned(userId, habitId);
        Set<LocalDate> dates = new HashSet<>(checkinMapper.selectList(new LambdaQueryWrapper<HabitCheckin>().eq(HabitCheckin::getUserId, userId).eq(HabitCheckin::getHabitId, habitId)).stream().map(HabitCheckin::getCheckinDate).toList());
        LocalDate cursor = LocalDate.now();
        int streak = 0;
        while (dates.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }
        return streak;
    }

    private Habit requireOwned(Long userId, Long id) {
        Habit habit = habitMapper.selectById(id);
        if (habit == null || !userId.equals(habit.getUserId())) throw new BusinessException(404, "习惯不存在");
        return habit;
    }

    private void copy(HabitRequest request, Habit habit) {
        habit.setHabitName(request.getHabitName());
        habit.setDescription(request.getDescription());
        habit.setFrequency(request.getFrequency() == null ? "DAILY" : request.getFrequency());
        habit.setTargetCount(request.getTargetCount() == null ? 1 : request.getTargetCount());
        habit.setStatus(request.getStatus() == null ? "ACTIVE" : request.getStatus());
    }
}

