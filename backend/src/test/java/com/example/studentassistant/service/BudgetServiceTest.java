package com.example.studentassistant.service;

import com.example.studentassistant.dto.auth.RegisterRequest;
import com.example.studentassistant.dto.budget.BudgetRequest;
import com.example.studentassistant.dto.budget.BudgetWarningResponse;
import com.example.studentassistant.dto.finance.FinanceRecordRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BudgetServiceTest {
    @Autowired private AuthService authService;
    @Autowired private BudgetService budgetService;
    @Autowired private FinanceRecordService financeRecordService;

    @Test
    void budgetWarningWhenSpendingReachesThreshold() {
        Long userId = createUser("budget_warning");
        String month = YearMonth.now().toString();
        budgetService.create(userId, budget(month, "餐饮", "100.00", "80.00"));
        financeRecordService.create(userId, expense("餐饮", "85.00"));

        List<BudgetWarningResponse> warnings = budgetService.warnings(userId);

        assertThat(warnings).anyMatch(item -> "餐饮".equals(item.getCategory()) && "WARNING".equals(item.getStatus()));
    }

    @Test
    void creatingSameMonthCategoryBudgetUpdatesExistingRecord() {
        Long userId = createUser("budget_upsert");
        String month = YearMonth.now().toString();
        budgetService.create(userId, budget(month, "交通", "100.00", "80.00"));
        budgetService.create(userId, budget(month, "交通", "200.00", "70.00"));

        List<BudgetWarningResponse> warnings = budgetService.warnings(userId);

        assertThat(warnings).filteredOn(item -> "交通".equals(item.getCategory()))
                .singleElement()
                .satisfies(item -> {
                    assertThat(item.getBudgetAmount()).isEqualByComparingTo("200.00");
                    assertThat(item.getStatus()).isEqualTo("NORMAL");
                });
    }

    private Long createUser(String suffix) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("test_" + suffix + "_" + System.nanoTime());
        request.setPassword("password123");
        request.setNickname("测试用户");
        return authService.register(request).getId();
    }

    private BudgetRequest budget(String month, String category, String amount, String threshold) {
        BudgetRequest request = new BudgetRequest();
        request.setMonth(month);
        request.setCategory(category);
        request.setAmount(new BigDecimal(amount));
        request.setWarningThreshold(new BigDecimal(threshold));
        return request;
    }

    private FinanceRecordRequest expense(String category, String amount) {
        FinanceRecordRequest request = new FinanceRecordRequest();
        request.setRecordType("EXPENSE");
        request.setCategory(category);
        request.setAmount(new BigDecimal(amount));
        request.setRecordDate(LocalDate.now());
        return request;
    }
}
