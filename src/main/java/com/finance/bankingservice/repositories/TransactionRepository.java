package com.finance.bankingservice.repositories;

import com.finance.bankingservice.models.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Qualifier("transaction")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction>  findAllByTransactionDateBetweenAndAccount_Id(LocalDateTime StartOfDay, LocalDateTime endOfDay, Long accountId);

    List<Transaction> findAllByTransactionDateBetweenAndAccount_IdOrTargetAccountNumber(LocalDateTime StartOfDay, LocalDateTime endOfDay, Long accountId, String targetAccountNumber);
}
