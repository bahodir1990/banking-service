package com.finance.bankingservice.repositories;

import com.finance.bankingservice.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void findByIdentificationNumber() {
        Customer customer = new Customer(
                "457123",
                "Akhmedov Bakhodir",
                "015234212120",
                "test@gmail.com",
                "Passau",
                LocalDate.now(),
                LocalDate.now()
        );

        underTest.save(customer);

        // when
        Optional<Customer> test = underTest.findByIdentificationNumber(customer.getIdentificationNumber());

        // then
        assertTrue(test.isPresent());
        assertThat(test.get().getIdentificationNumber()).isEqualTo(customer.getIdentificationNumber());
    }
}