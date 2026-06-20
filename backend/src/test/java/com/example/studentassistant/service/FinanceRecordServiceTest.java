package com.example.studentassistant.service;

import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.finance.FinanceRecordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FinanceRecordServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private FinanceRecordService financeRecordService;

    @Test
    void statisticsOnlyGroupsExpenseRecordsByCategory() {
        Long userId = createUser("finance_stats");
        financeRecordService.create(userId, financeRequest("EXPENSE", "学习用品", "39.90"));
        financeRecordService.create(userId, financeRequest("EXPENSE", "学习用品", "10.10"));
        financeRecordService.create(userId, financeRequest("INCOME", "生活费", "500.00"));

        List<NameValueResponse> statistics = financeRecordService.statistics(userId);

        assertThat(statistics).hasSize(1);
        assertThat(statistics.get(0).getName()).isEqualTo("学习用品");
        assertThat(statistics.get(0).getValue()).isEqualByComparingTo(new BigDecimal("50.00"));
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private FinanceRecordRequest financeRequest(String type, String category, String amount) {
        FinanceRecordRequest request = new FinanceRecordRequest();
        request.setRecordType(type);
        request.setCategory(category);
        request.setAmount(new BigDecimal(amount));
        request.setDescription("测试收支记录");
        request.setRecordDate(LocalDate.now());
        return request;
    }
}
