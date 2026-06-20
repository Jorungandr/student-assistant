package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.study.StudyRecordRequest;
import com.example.studentassistant.entity.StudyRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudyRecordServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private StudyRecordService studyRecordService;

    @Test
    void userCanCreateAndListStudyRecord() {
        Long userId = createUser("study_record");

        StudyRecord created = studyRecordService.create(userId, studyRecordRequest());

        assertThat(created.getId()).isNotNull();
        assertThat(studyRecordService.list(userId))
                .extracting(StudyRecord::getContent)
                .contains("复习软件测试课程");
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private StudyRecordRequest studyRecordRequest() {
        StudyRecordRequest request = new StudyRecordRequest();
        request.setStudyDate(LocalDate.now());
        request.setDurationMinutes(90);
        request.setContent("复习软件测试课程");
        return request;
    }
}
