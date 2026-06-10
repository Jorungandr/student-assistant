package com.example.studentassistant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentassistant.entity.Habit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HabitMapper extends BaseMapper<Habit> {}

