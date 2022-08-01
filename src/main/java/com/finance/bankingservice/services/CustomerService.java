package com.finance.bankingservice.services;

import com.finance.bankingservice.enums.ServiceMessages;
import com.finance.bankingservice.models.Customer;
import com.finance.bankingservice.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * CREATE Customer
     *
     * @param customer
     * @return
     */
    public ResponseEntity<Object> addCustomer(Customer customer) {
        Optional<Customer> customerByIdentificationNumber = customerRepository.
                findByIdentificationNumber(customer.getIdentificationNumber());
        if(customerByIdentificationNumber.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServiceMessages.CUSTOMER_NOT_CREATED.getMessage());
        }
        customer.setCreatedDate(LocalDate.now());
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(ServiceMessages.CUSTOMER_CREATED);
    }

    /**
     * UPDATE Customer
     *
     * @param customerInput
     * @param identificationNumber
     * @return
     */
    public ResponseEntity<Object> updateCustomer(Customer customerInput,
                                                 String identificationNumber) {
        Optional<Customer> customer = customerRepository.findByIdentificationNumber(identificationNumber);

        if (customer.isPresent()) {
            Customer currentCustomer = customer.get();

            currentCustomer.setIdentificationNumber(customerInput.getIdentificationNumber());
            currentCustomer.setName(customerInput.getName());
            currentCustomer.setPhone(customerInput.getPhone());
            currentCustomer.setEmail(customerInput.getEmail());
            currentCustomer.setAddress(customerInput.getAddress());
            currentCustomer.setUpdatedDate(LocalDate.now());

            customerRepository.save(currentCustomer);
            return ResponseEntity.status(HttpStatus.OK).body(ServiceMessages.SUCCESSFUL_CUSTOMER_UPDATE);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceMessages.CUSTOMER_NOT_FOUND);
        }
    }

    /**
     * READ Customer
     *
     * @param identificationNumber
     * @return
     */
    public Customer findCustomerByIdentificationNumber(String identificationNumber) {
        Optional<Customer> customer =
                customerRepository.findByIdentificationNumber(identificationNumber);
        return customer.orElse(null);
    }

    /**
     * DELETE Customer
     *
     * @param identificationNumber
     * @return
     */
    public ResponseEntity<Object> deleteCustomer(String identificationNumber) {
        Optional<Customer> customer = customerRepository.findByIdentificationNumber(identificationNumber);

        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
            return ResponseEntity.status(HttpStatus.OK).body(ServiceMessages.SUCCESSFUL_CUSTOMER_DELETE);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServiceMessages.CUSTOMER_NOT_FOUND);
        }
    }
}
