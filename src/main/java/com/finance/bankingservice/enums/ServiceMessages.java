package com.finance.bankingservice.enums;

public enum ServiceMessages implements MessageEnums{
    ACCOUNT_CREATED("New account has been created successful", 1),

    ACCOUNT_NOT_CREATED("Bank account was not saved. Customer not found", 2),

    CUSTOMER_NOT_CREATED("Identification Number is taken" , 3),

    CUSTOMER_CREATED("New customer has been created successful", 4),

    ACCOUNT_NOT_FOUND("Unable to find an account matching this account number", 5),

    CUSTOMER_NOT_FOUND("Unable to find the customer matching this identification number", 6),

    SUCCESSFUL_CUSTOMER_UPDATE("The customer has been updated successful", 7),

    SUCCESSFUL_CUSTOMER_DELETE("The customer has been deleted successful", 8),

    SAVINGS_ACCOUNT_EXCEED_TRANSACTION_LIMIT("The transaction limit is exceeded for a savings account", 10),

    NOT_ENOUGH_MONEY("This customer has not enough money", 11),

    SUCCESSFUL_DEPOSIT("Customer has made a successful deposit", 12),

    SUCCESSFUL_WITHDRAWAL("Customer has made a successful withdrawal", 13),

    SUCCESSFUL_TRANSFER("Customer has made a successful transfer", 14);



    private final String message;
    private final int code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    ServiceMessages(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
