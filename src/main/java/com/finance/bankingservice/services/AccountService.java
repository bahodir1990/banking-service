package com.finance.bankingservice.services;

import com.finance.bankingservice.enums.ServiceMessages;
import com.finance.bankingservice.models.Account;
import com.finance.bankingservice.models.Customer;
import com.finance.bankingservice.repositories.AccountRepository;
import com.finance.bankingservice.repositories.CustomerRepository;
import com.finance.bankingservice.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Find Account
     *
     * @param accountNumber
     * @return
     */
    public Account findByAccountNumber(String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);

        account.ifPresent(value -> value.setTransactions(transactionRepository
                        .findAllByTransactionDateBetweenAndAccount_IdOrTargetAccountNumber(
                                LocalDateTime.now().minusDays(2),
                                LocalDateTime.now(),
                                value.getId(),
                                value.getAccountNumber())));

        return account.orElse(null);
    }

    /**
     * Read account current balance
     *
     * @param accountNumber
     *
     * @return
     */
    public BigDecimal getCurrentBalance(String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);
        return account.map(Account::getActualBalance).orElse(null);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Create new account
     *
     * @param account
     * @param customerIdentificationNumber
     *
     * @return
     */
    public ResponseEntity<Object> addNewAccount(Account account, String customerIdentificationNumber) throws Exception {
        Optional<Customer> customer = customerRepository.findByIdentificationNumber(customerIdentificationNumber);

        if (customer.isPresent()) {
            account.setCustomer(customer.get());
            accountRepository.save(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(ServiceMessages.ACCOUNT_CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceMessages.ACCOUNT_NOT_CREATED);
        }
    }
}
