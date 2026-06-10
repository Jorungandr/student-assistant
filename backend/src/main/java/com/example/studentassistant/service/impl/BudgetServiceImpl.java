package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.budget.BudgetRequest;
import com.example.studentassistant.dto.budget.BudgetWarningResponse;
import com.example.studentassistant.entity.Budget;
import com.example.studentassistant.entity.FinanceRecord;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.BudgetMapper;
import com.example.studentassistant.mapper.FinanceRecordMapper;
import com.example.studentassistant.service.BudgetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetMapper budgetMapper;
    private final FinanceRecordMapper financeRecordMapper;

    public BudgetServiceImpl(BudgetMapper budgetMapper, FinanceRecordMapper financeRecordMapper) {
        this.budgetMapper = budgetMapper;
        this.financeRecordMapper = financeRecordMapper;
    }

    public List<Budget> list(Long userId) {
        return budgetMapper.selectList(new LambdaQueryWrapper<Budget>().eq(Budget::getUserId, userId).orderByDesc(Budget::getMonth));
    }

    @Transactional
    public Budget create(Long userId, BudgetRequest request) {
        Budget budget = new Budget();
        budget.setUserId(userId);
        copy(request, budget);
        budgetMapper.insert(budget);
        return budget;
    }

    @Transactional
    public Budget update(Long userId, Long id, BudgetRequest request) {
        Budget budget = requireOwned(userId, id);
        copy(request, budget);
        budgetMapper.updateById(budget);
        return budget;
    }

    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        budgetMapper.deleteById(id);
    }

    public List<BudgetWarningResponse> warnings(Long userId) {
        return list(userId).stream().map(budget -> {
            BigDecimal used = spentFor(userId, budget.getMonth(), budget.getCategory());
            BigDecimal rate = budget.getAmount().compareTo(BigDecimal.ZERO) == 0
                    ? BigDecimal.ZERO
                    : used.multiply(BigDecimal.valueOf(100)).divide(budget.getAmount(), 2, RoundingMode.HALF_UP);
            BudgetWarningResponse response = new BudgetWarningResponse();
            response.setBudgetId(budget.getId());
            response.setMonth(budget.getMonth());
            response.setCategory(budget.getCategory());
            response.setBudgetAmount(budget.getAmount());
            response.setUsedAmount(used);
            response.setUsageRate(rate);
            response.setStatus(rate.compareTo(BigDecimal.valueOf(100)) >= 0 ? "EXCEEDED" : rate.compareTo(budget.getWarningThreshold()) >= 0 ? "WARNING" : "NORMAL");
            return response;
        }).toList();
    }

    private BigDecimal spentFor(Long userId, String month, String category) {
        YearMonth ym = YearMonth.parse(month);
        return financeRecordMapper.selectList(new LambdaQueryWrapper<FinanceRecord>()
                        .eq(FinanceRecord::getUserId, userId)
                        .eq(FinanceRecord::getRecordType, "EXPENSE")
                        .eq(FinanceRecord::getCategory, category)
                        .ge(FinanceRecord::getRecordDate, ym.atDay(1))
                        .le(FinanceRecord::getRecordDate, ym.atEndOfMonth()))
                .stream().map(FinanceRecord::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Budget requireOwned(Long userId, Long id) {
        Budget budget = budgetMapper.selectById(id);
        if (budget == null || !userId.equals(budget.getUserId())) throw new BusinessException(404, "预算不存在");
        return budget;
    }

    private void copy(BudgetRequest request, Budget budget) {
        budget.setMonth(request.getMonth());
        budget.setCategory(request.getCategory());
        budget.setAmount(request.getAmount());
        budget.setWarningThreshold(request.getWarningThreshold() == null ? BigDecimal.valueOf(80) : request.getWarningThreshold());
    }
}

