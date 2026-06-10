package com.example.studentassistant.dto.finance;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FinanceRecordRequest {
    @NotBlank
    private String recordType;
    @NotBlank
    private String category;
    @NotNull
    @Positive
    private BigDecimal amount;
    private String description;
    @NotNull
    private LocalDate recordDate;

    public String getRecordType() { return recordType; }
    public void setRecordType(String recordType) { this.recordType = recordType; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
}

