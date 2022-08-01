package com.finance.bankingservice.services;

import com.finance.bankingservice.enums.ServiceMessages;
import com.finance.bankingservice.models.*;
import com.finance.bankingservice.repositories.AccountRepository;
import com.finance.bankingservice.repositories.CustomerRepository;
import com.finance.bankingservice.repositories.TransactionRepository;
import com.finance.bankingservice.utils.TransactionInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class TransactionService {

    private static final int MAX_TRANSACTIONS_PER_MONTH_FOR_SAVING_ACCOUNT = 6;


    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;

    public TransactionService(AccountRepository accountRepository,
                              TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    public ResponseEntity<Object> makeOperation(TransactionInput transactionInput,
                                                String customerIdentificationNumber) {
        Optional<Customer> customer = customerRepository.findByIdentificationNumber(customerIdentificationNumber);
        if (customer.isPresent()) {
            TransactionType type = transactionInput.getType();
            switch(type) {
                case TRANSFER -> {
                    return transfer(transactionInput);
                }
                case WITHDRAWAL -> {
                    return withdrawal(transactionInput);
                }
                case DEPOSIT -> {
                    return deposit();
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceMessages.CUSTOMER_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    private ResponseEntity<Object> withdrawal(TransactionInput transactionInput) {
        Optional<Account> sourceAccount = accountRepository
                .findByAccountNumber(transactionInput.getSourceAccountNumber());

        Map<String, Optional<Account>> accounts = new HashMap<>();
        accounts.put("sourceAccount", sourceAccount);

        try {
            checkForValidityTransaction(accounts, transactionInput);
        } catch(IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        updateAccountBalance(sourceAccount.get(), transactionInput.getAmount(), true);
        saveTransaction(sourceAccount.get(), null, transactionInput);

        return ResponseEntity.status(HttpStatus.OK).body(ServiceMessages.SUCCESSFUL_WITHDRAWAL);
    }

    private ResponseEntity<Object> transfer(TransactionInput transactionInput) {
        Optional<Account> sourceAccount = accountRepository
                .findByAccountNumber(transactionInput.getSourceAccountNumber());

        Optional<Account> targetAccount = accountRepository
                .findByAccountNumber(transactionInput.getTargetAccountNumber());

        Map<String, Optional<Account>> accounts = new HashMap<>();
        accounts.put("sourceAccount", sourceAccount);
        accounts.put("targetAccount", targetAccount);
        try {
            checkForValidityTransaction(accounts, transactionInput);
        } catch(IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        updateAccountBalance(sourceAccount.get(), transactionInput.getAmount(), true);
        updateAccountBalance(targetAccount.get(), transactionInput.getAmount(), false);
        saveTransaction(sourceAccount.get(), targetAccount.get(), transactionInput);

        return ResponseEntity.status(HttpStatus.OK).body(ServiceMessages.SUCCESSFUL_TRANSFER);
    }

    // Have to implement
    private ResponseEntity<Object> deposit() {
        return ResponseEntity.status(HttpStatus.OK).body(ServiceMessages.SUCCESSFUL_DEPOSIT);
    }

    private boolean checkForValidityTransaction(Map<String, Optional<Account>> accounts, TransactionInput transactionInput) throws IllegalStateException{
        for (String key:accounts.keySet()) {
            if (accounts.get(key).isEmpty()) {
                throw new IllegalStateException(ServiceMessages.ACCOUNT_NOT_FOUND.getMessage());
            }
        }
        Optional<Account> account = accounts.get("sourceAccount");
        if (account.isPresent() && account.get().getType().equals(AccountType.SAVINGS_ACCOUNT)
                && (getNumberOfTransactions(account.get().getId()) > MAX_TRANSACTIONS_PER_MONTH_FOR_SAVING_ACCOUNT)) {
            throw new IllegalStateException(ServiceMessages.SAVINGS_ACCOUNT_EXCEED_TRANSACTION_LIMIT.getMessage());
        }

        if (account.isPresent() && isAmountAvailable(transactionInput.getAmount(), account.get().getActualBalance())) {
            throw new IllegalStateException(ServiceMessages.NOT_ENOUGH_MONEY.getMessage());
        }
        return true;
    }


    private int getNumberOfTransactions(Long accountId) {
        LocalDateTime today = LocalDateTime.now();
        List<Transaction> transactions = transactionRepository.findAllByTransactionDateBetweenAndAccount_Id(
                today.withDayOfMonth(1),
                LocalDateTime.now(),
                accountId
        );
        return transactions.size();
    }

    private void updateAccountBalance(Account account, BigDecimal amount, boolean isSourceAccount) {
        account.setActualBalance(isSourceAccount ? account.getActualBalance().subtract(amount):
                account.getActualBalance().add(amount));
        accountRepository.save(account);
    }

    private void saveTransaction(Account sourceAccount,
                                 Account targetAccount,
                                 TransactionInput transactionInput) {
        Transaction transaction = new Transaction();
        transaction.setType(transactionInput.getType());
        transaction.setAccount(sourceAccount);
        transaction.setAmount(transactionInput.getAmount());
        if (targetAccount != null) {
            transaction.setTargetAccountNumber(targetAccount.getAccountNumber());
            transaction.setTargetOwnerName(targetAccount.getCustomer().getName());
        } else {
            //  in the case of withdrawal
            transaction.setTargetAccountNumber("Account number of ATM");
            transaction.setTargetOwnerName("ATM");
        }
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
    private boolean isAmountAvailable(BigDecimal amount, BigDecimal accountBalance) {
        return accountBalance.subtract(amount).compareTo(BigDecimal.ZERO) <= 0;
    }
}
