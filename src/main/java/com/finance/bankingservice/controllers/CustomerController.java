package com.finance.bankingservice.controllers;

import com.finance.bankingservice.enums.ServiceMessages;
import com.finance.bankingservice.models.Customer;
import com.finance.bankingservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{identification_number}")
    public ResponseEntity<Object> getCustomer(@PathVariable("identification_number")
                                         String identificationNumber) {
        Customer customer = customerService.findCustomerByIdentificationNumber(identificationNumber);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceMessages.CUSTOMER_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(customer);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createNewCustomer(@RequestBody Customer customer) {
        return  customerService.addCustomer(customer);
    }

    @PutMapping("/update/{identification_number}")
    public ResponseEntity<Object> updateCustomer(@PathVariable("identification_number") String identificationNumber,
                                                 @RequestBody Customer customer) {
        return customerService.updateCustomer(customer, identificationNumber);
    }

    @DeleteMapping("/delete/{identification_number}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable("identification_number") String identificationNumber) {
        return customerService.deleteCustomer(identificationNumber);
    }

}
