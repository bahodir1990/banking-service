package com.finance.bankingservice.controllers;

import com.finance.bankingservice.services.TransactionService;
import com.finance.bankingservice.utils.TransactionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    /*private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);*/
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/operation/{customer_identification_number}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> makeOperation(@Valid @RequestBody TransactionInput transactionInput,
                                           @PathVariable("customer_identification_number")
                                           String customerIdentificationNumber) {
        return transactionService.makeOperation(transactionInput, customerIdentificationNumber);
    }
}
