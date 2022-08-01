package com.finance.bankingservice.utils;

import com.finance.bankingservice.models.TransactionType;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransactionInput {
    private String sourceAccountNumber;

    private String targetAccountNumber;

    @Positive(message = "Transaction amount must be positive")
    @Min(value = 1, message = "Amount must be larger than 1")
    private BigDecimal amount;

    private TransactionType type;

    public TransactionInput() {}

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionInput{" +
                "sourceAccountNumber='" + sourceAccountNumber + '\'' +
                ", targetAccountNumber='" + targetAccountNumber + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
