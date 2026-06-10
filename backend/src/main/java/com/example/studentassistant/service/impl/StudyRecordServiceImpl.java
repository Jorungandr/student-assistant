package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.analysis.DateValueResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.study.StudyRecordRequest;
import com.example.studentassistant.entity.Course;
import com.example.studentassistant.entity.StudyRecord;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.CourseMapper;
import com.example.studentassistant.mapper.StudyRecordMapper;
import com.example.studentassistant.service.StudyRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudyRecordServiceImpl implements StudyRecordService {
    private final StudyRecordMapper mapper;
    private final CourseMapper courseMapper;

    public StudyRecordServiceImpl(StudyRecordMapper mapper, CourseMapper courseMapper) {
        this.mapper = mapper;
        this.courseMapper = courseMapper;
    }

    public List<StudyRecord> list(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<StudyRecord>().eq(StudyRecord::getUserId, userId).orderByDesc(StudyRecord::getStudyDate));
    }

    @Transactional
    public StudyRecord create(Long userId, StudyRecordRequest request) {
        validateCourse(userId, request.getCourseId());
        StudyRecord record = new StudyRecord();
        record.setUserId(userId);
        copy(request, record);
        mapper.insert(record);
        return record;
    }

    @Transactional
    public StudyRecord update(Long userId, Long id, StudyRecordRequest request) {
        validateCourse(userId, request.getCourseId());
        StudyRecord record = requireOwned(userId, id);
        copy(request, record);
        mapper.updateById(record);
        return record;
    }

    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        mapper.deleteById(id);
    }

    public List<DateValueResponse> durationTrend(Long userId) {
        return list(userId).stream()
                .collect(Collectors.groupingBy(StudyRecord::getStudyDate, Collectors.summingInt(StudyRecord::getDurationMinutes)))
                .entrySet().stream()
                .map(entry -> new DateValueResponse(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(DateValueResponse::getDate))
                .toList();
    }

    public List<NameValueResponse> courseRatio(Long userId) {
        Map<Long, String> courseNames = courseMapper.selectList(new LambdaQueryWrapper<Course>().eq(Course::getUserId, userId))
                .stream().collect(Collectors.toMap(Course::getId, Course::getCourseName));
        return list(userId).stream()
                .collect(Collectors.groupingBy(record -> courseNames.getOrDefault(record.getCourseId(), "未关联课程"), Collectors.summingInt(StudyRecord::getDurationMinutes)))
                .entrySet().stream()
                .map(entry -> new NameValueResponse(entry.getKey(), BigDecimal.valueOf(entry.getValue())))
                .toList();
    }

    private StudyRecord requireOwned(Long userId, Long id) {
        StudyRecord record = mapper.selectById(id);
        if (record == null || !userId.equals(record.getUserId())) {
            throw new BusinessException(404, "学习记录不存在");
        }
        return record;
    }

    private void validateCourse(Long userId, Long courseId) {
        if (courseId == null) return;
        Course course = courseMapper.selectById(courseId);
        if (course == null || !userId.equals(course.getUserId())) {
            throw new BusinessException("关联课程不存在");
        }
    }

    private void copy(StudyRecordRequest request, StudyRecord record) {
        record.setCourseId(request.getCourseId());
        record.setStudyDate(request.getStudyDate());
        record.setDurationMinutes(request.getDurationMinutes());
        record.setContent(request.getContent());
    }
}

