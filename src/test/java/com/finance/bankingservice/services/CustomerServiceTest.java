package com.finance.bankingservice.services;

import com.finance.bankingservice.models.Customer;
import com.finance.bankingservice.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerRepository);
    }

    @Test
    @Disabled
    void findAllCustomers() {
    }

    @Test
    void addCustomer() {
        //Given
        Customer customer = new Customer(
                "457123",
                "Akhmedov Bakhodir",
                "015234212120",
                "test@gmail.com",
                "Passau",
                LocalDate.now(),
                LocalDate.now()
        );

        //when
        underTest.addCustomer(customer);

        //then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).
                save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer).isEqualTo(customer);
    }

    @Test
    @Disabled
    void updateCustomer() {
    }

    @Test
    @Disabled
    void findCustomerByIdentificationNumber() {
    }

    @Test
    void deleteCustomer() {
    }
}