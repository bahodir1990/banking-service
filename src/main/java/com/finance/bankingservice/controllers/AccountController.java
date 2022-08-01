package com.finance.bankingservice.controllers;

import com.finance.bankingservice.enums.ServiceMessages;
import com.finance.bankingservice.models.Account;
import com.finance.bankingservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/accounts")
public class AccountController {
    //private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{account_number}")
    public ResponseEntity<Object> getAccountDetails(@PathVariable("account_number")
                                                    String accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber);

        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceMessages.ACCOUNT_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(account);

    }

    @GetMapping("/balance/{account_number}")
    public ResponseEntity<Object> getAccountBalance(@PathVariable("account_number")
                                                    String accountNumber) {
        BigDecimal balance = accountService.getCurrentBalance(accountNumber);

        if (balance == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServiceMessages.ACCOUNT_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body("Current balance of the customer is " +
                balance + " euro");
    }

    @PostMapping("/add/{customer_identification_number}")
    public void addNewAccount(@RequestBody Account account,
                              @PathVariable("customer_identification_number")
                              String customerIdentificationNumber) throws Exception {

        accountService.addNewAccount(account, customerIdentificationNumber);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
