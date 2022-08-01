package com.finance.bankingservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bank_transactions")
public class Transaction {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "transaction_sequence")
    private Long id;

    private String targetAccountNumber;

    private String targetOwnerName;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType type;


    @JsonBackReference(value = "account")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Transaction() {
    }

    public Transaction(String targetAccountNumber,
                       String targetOwnerName,
                       BigDecimal amount,
                       LocalDateTime transactionDate,
                       TransactionType type,
                       Account account) {
        this.targetAccountNumber = targetAccountNumber;
        this.targetOwnerName = targetOwnerName;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public String getTargetOwnerName() {
        return targetOwnerName;
    }

    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @JsonBackReference
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "BankTransaction{" +
                "id=" + id +
                ", targetAccountNumber=" + targetAccountNumber +
                ", targetOwnerName='" + targetOwnerName + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                ", account=" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) && Objects.equals(targetAccountNumber, that.targetAccountNumber) && Objects.equals(targetOwnerName, that.targetOwnerName) && Objects.equals(amount, that.amount) && Objects.equals(transactionDate, that.transactionDate) && type == that.type && account.equals(that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, targetAccountNumber, targetOwnerName, amount, transactionDate, type, account);
    }
}