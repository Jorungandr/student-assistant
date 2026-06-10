package com.example.studentassistant.service;

import com.example.studentassistant.dto.analysis.DateValueResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.study.StudyRecordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AnalysisServiceTest {
    @Autowired private AuthService authService;
    @Autowired private StudyRecordService studyRecordService;

    @Test
    void studyDurationGroupedByDate() {
        Long userId = createUser("analysis_duration");
        studyRecordService.create(userId, studyRecord(LocalDate.now(), 30));
        studyRecordService.create(userId, studyRecord(LocalDate.now(), 45));

        List<DateValueResponse> trend = studyRecordService.durationTrend(userId);

        assertThat(trend).anyMatch(item -> item.getDate().equals(LocalDate.now()) && item.getValue() == 75);
    }

    @Test
    void courseRatioIncludesUnlinkedCourseRecords() {
        Long userId = createUser("analysis_ratio");
        studyRecordService.create(userId, studyRecord(LocalDate.now(), 60));

        List<NameValueResponse> ratio = studyRecordService.courseRatio(userId);

        assertThat(ratio).anyMatch(item -> "未关联课程".equals(item.getName()) && item.getValue().intValue() == 60);
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private StudyRecordRequest studyRecord(LocalDate date, int minutes) {
        StudyRecordRequest request = new StudyRecordRequest();
        request.setStudyDate(date);
        request.setDurationMinutes(minutes);
        request.setContent("测试学习记录");
        return request;
    }
}

