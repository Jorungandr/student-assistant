package com.example.studentassistant.dto.budget;

import java.math.BigDecimal;

public class BudgetWarningResponse {
    private Long budgetId;
    private String month;
    private String category;
    private BigDecimal budgetAmount;
    private BigDecimal usedAmount;
    private BigDecimal usageRate;
    private String status;

    public Long getBudgetId() { return budgetId; }
    public void setBudgetId(Long budgetId) { this.budgetId = budgetId; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getBudgetAmount() { return budgetAmount; }
    public void setBudgetAmount(BigDecimal budgetAmount) { this.budgetAmount = budgetAmount; }
    public BigDecimal getUsedAmount() { return usedAmount; }
    public void setUsedAmount(BigDecimal usedAmount) { this.usedAmount = usedAmount; }
    public BigDecimal getUsageRate() { return usageRate; }
    public void setUsageRate(BigDecimal usageRate) { this.usageRate = usageRate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

