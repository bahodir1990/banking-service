package com.finance.bankingservice.dbinitializer;

import com.finance.bankingservice.models.*;
import com.finance.bankingservice.repositories.AccountRepository;
import com.finance.bankingservice.repositories.CustomerRepository;
import com.finance.bankingservice.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataBaseInitializer implements CommandLineRunner {
    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    private final CustomerRepository customerRepository;

    public DataBaseInitializer(AccountRepository accountRepository,
                               TransactionRepository transactionRepository,
                               CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.accountRepository.deleteAll();
        this.transactionRepository.deleteAll();
        this.customerRepository.deleteAll();
        Customer customer1 = new Customer(
                "1234567",
                "Akhmedov Bakhodir",
                "015734712120",
                "bahodir.axmedov@gmail.com",
                "Breaslauer str",
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(3)
        );

        Customer customer2 = new Customer(
                "7654321",
                "Akhmedov Firdavs",
                "015223456130",
                "firdavs.axmedov@gmail.com",
                "Mokhon",
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(3)
        );

        this.customerRepository.saveAll(List.of(customer1, customer2));

        Account bankAccount1 = new Account(
                AccountType.CHECKING_ACCOUNT,
                "1234567",
                new BigDecimal(1234.9),
                "Spakasse Passau",
                customer1,
                500,
                0);
        Account bankAccount2 = new Account(
                AccountType.CHECKING_ACCOUNT,
                "89567546",
                new BigDecimal(123),
                "CommerzBank",
                customer2,
                300,
                0);
        this.accountRepository.saveAll(List.of(bankAccount1, bankAccount2));

        Transaction bankTransaction1 = new Transaction(
                "89567546",
                "Akhmedov Firdavs",
                new BigDecimal(400),
                LocalDateTime.now(),
                TransactionType.TRANSFER,
                bankAccount1
        );
        this.transactionRepository.save(bankTransaction1);
        System.out.println("Database has been initialized");
    }
}
