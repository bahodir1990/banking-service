package com.finance.bankingservice.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface MessageEnums {

    String getMessage();

    int getCode();
}
