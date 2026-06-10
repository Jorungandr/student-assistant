package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.course.CourseRequest;
import com.example.studentassistant.entity.Course;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.CourseMapper;
import com.example.studentassistant.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Course> list(Long userId) {
        return courseMapper.selectList(new LambdaQueryWrapper<Course>()
                .eq(Course::getUserId, userId)
                .orderByAsc(Course::getWeekday)
                .orderByAsc(Course::getStartSection));
    }

    @Override
    public List<Course> today(Long userId) {
        int weekday = LocalDate.now().getDayOfWeek().getValue();
        return courseMapper.selectList(new LambdaQueryWrapper<Course>()
                .eq(Course::getUserId, userId)
                .eq(Course::getWeekday, weekday)
                .orderByAsc(Course::getStartSection));
    }

    @Override
    @Transactional
    public Course create(Long userId, CourseRequest request) {
        Course course = new Course();
        course.setUserId(userId);
        copy(request, course);
        courseMapper.insert(course);
        return course;
    }

    @Override
    @Transactional
    public Course update(Long userId, Long id, CourseRequest request) {
        Course course = requireOwned(userId, id);
        copy(request, course);
        courseMapper.updateById(course);
        return course;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        courseMapper.deleteById(id);
    }

    private Course requireOwned(Long userId, Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null || !userId.equals(course.getUserId())) {
            throw new BusinessException(404, "课程不存在");
        }
        return course;
    }

    private void copy(CourseRequest request, Course course) {
        course.setCourseName(request.getCourseName());
        course.setTeacher(request.getTeacher());
        course.setClassroom(request.getClassroom());
        course.setWeekday(request.getWeekday());
        course.setStartSection(request.getStartSection());
        course.setEndSection(request.getEndSection());
        course.setStartWeek(request.getStartWeek());
        course.setEndWeek(request.getEndWeek());
        course.setColor(request.getColor());
    }
}

