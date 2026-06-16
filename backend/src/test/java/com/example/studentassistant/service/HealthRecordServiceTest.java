package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.health.HealthRecordRequest;
import com.example.studentassistant.entity.HealthRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HealthRecordServiceTest {
    @Autowired private AuthService authService;
    @Autowired private HealthRecordService healthRecordService;

    @Test
    void creatingSameDateHealthRecordUpdatesExistingRecord() {
        Long userId = createUser("health_upsert");
        LocalDate today = LocalDate.now();
        HealthRecord first = healthRecordService.create(userId, record(today, 1200));
        HealthRecord second = healthRecordService.create(userId, record(today, 2000));

        assertThat(second.getId()).isEqualTo(first.getId());
        assertThat(healthRecordService.today(userId).getWaterMl()).isEqualTo(2000);
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private HealthRecordRequest record(LocalDate date, int waterMl) {
        HealthRecordRequest request = new HealthRecordRequest();
        request.setRecordDate(date);
        request.setWeather("晴");
        request.setSleepHours(new BigDecimal("7.0"));
        request.setWaterMl(waterMl);
        request.setExerciseMinutes(30);
        request.setMoodScore(8);
        request.setStressScore(4);
        return request;
    }
}
