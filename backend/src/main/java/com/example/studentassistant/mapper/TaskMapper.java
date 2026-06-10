package com.example.studentassistant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentassistant.entity.Task;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}

