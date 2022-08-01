package com.finance.bankingservice.repositories;

import com.finance.bankingservice.models.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("account")
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByAccountNumber(String accountNumber);
}
