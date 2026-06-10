package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.course.CourseRequest;
import com.example.studentassistant.entity.Course;
import com.example.studentassistant.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CourseServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private CourseService courseService;

    @Test
    void userCanCreateAndListOwnCourses() {
        Long userId = createUser("course_owner");
        Long otherUserId = createUser("course_other");

        courseService.create(userId, courseRequest("软件测试", 1));
        courseService.create(otherUserId, courseRequest("高等数学", 1));

        List<Course> courses = courseService.list(userId);

        assertThat(courses).extracting(Course::getCourseName).contains("软件测试");
        assertThat(courses).extracting(Course::getCourseName).doesNotContain("高等数学");
    }

    @Test
    void todayCoursesAreFilteredByWeekday() {
        Long userId = createUser("course_today");
        int today = LocalDate.now().getDayOfWeek().getValue();
        int anotherDay = today == 7 ? 1 : today + 1;

        courseService.create(userId, courseRequest("今日课程", today));
        courseService.create(userId, courseRequest("其他日期课程", anotherDay));

        List<Course> courses = courseService.today(userId);

        assertThat(courses).extracting(Course::getCourseName).contains("今日课程");
        assertThat(courses).extracting(Course::getCourseName).doesNotContain("其他日期课程");
    }

    @Test
    void userCannotUpdateAnotherUsersCourse() {
        Long ownerId = createUser("course_real_owner");
        Long attackerId = createUser("course_attacker");
        Course course = courseService.create(ownerId, courseRequest("被保护课程", 2));

        assertThatThrownBy(() -> courseService.update(attackerId, course.getId(), courseRequest("非法修改", 2)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("课程不存在");
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private CourseRequest courseRequest(String name, int weekday) {
        CourseRequest request = new CourseRequest();
        request.setCourseName(name);
        request.setTeacher("张老师");
        request.setClassroom("A101");
        request.setWeekday(weekday);
        request.setStartSection(1);
        request.setEndSection(2);
        request.setStartWeek(1);
        request.setEndWeek(18);
        request.setColor("#409EFF");
        return request;
    }
}

