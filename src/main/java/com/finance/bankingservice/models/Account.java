package com.finance.bankingservice.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bank_accounts")
public class Account {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "account_sequence")
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private String accountNumber;

    private BigDecimal actualBalance;

    private String bankName;

    @JsonBackReference(value = "customer")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    private double maxWithdrawalAmount;

    private double interestRate;

    @JsonManagedReference(value = "account")
    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;
    //transient

    public Account() {
    }

    public Account(AccountType type,
                   String accountNumber,
                   BigDecimal actualBalance,
                   String bankName,
                   double maxWithdrawalAmount,
                   double interestRate) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.actualBalance = actualBalance;
        this.bankName = bankName;
        this.maxWithdrawalAmount = maxWithdrawalAmount;
        this.interestRate = interestRate;
    }

    public Account(AccountType type,
                   String accountNumber,
                   BigDecimal actualBalance,
                   String bankName,
                   Customer customer,
                   double maxWithdrawalAmount,
                   double interestRate) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.actualBalance = actualBalance;
        this.bankName = bankName;
        this.customer = customer;
        this.maxWithdrawalAmount = maxWithdrawalAmount;
        this.interestRate = interestRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @JsonBackReference
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getMaxWithdrawalAmount() {
        return maxWithdrawalAmount;
    }

    public void setMaxWithdrawalAmount(double maxWithdrawalAmount) {
        this.maxWithdrawalAmount = maxWithdrawalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @JsonBackReference(value = "account")
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", type=" + type +
                ", accountNumber='" + accountNumber + '\'' +
                ", actualBalance=" + actualBalance +
                ", bankName='" + bankName + '\'' +
                ", customer=" + customer +
                ", maxWithdrawalAmount=" + maxWithdrawalAmount +
                ", interestRate=" + interestRate +
                ", transactions=" + transactions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.maxWithdrawalAmount, maxWithdrawalAmount) == 0 && Double.compare(account.interestRate, interestRate) == 0 && id.equals(account.id) && type == account.type && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(actualBalance, account.actualBalance) && Objects.equals(bankName, account.bankName) && customer.equals(account.customer) && Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, accountNumber, actualBalance, bankName, customer, maxWithdrawalAmount, interestRate, transactions);
    }
}
