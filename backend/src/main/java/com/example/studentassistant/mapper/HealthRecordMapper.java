package com.example.studentassistant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.studentassistant.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthRecordMapper extends BaseMapper<HealthRecord> {}

