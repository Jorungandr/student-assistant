package com.example.studentassistant.dto.budget;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class BudgetRequest {
    @NotBlank
    private String month;
    @NotBlank
    private String category;
    @NotNull
    @Positive
    private BigDecimal amount;
    private BigDecimal warningThreshold;

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getWarningThreshold() { return warningThreshold; }
    public void setWarningThreshold(BigDecimal warningThreshold) { this.warningThreshold = warningThreshold; }
}

