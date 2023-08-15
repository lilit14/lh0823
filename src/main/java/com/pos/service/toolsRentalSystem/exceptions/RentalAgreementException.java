package com.pos.service.toolsRentalSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class RentalAgreementException extends RuntimeException {
    String name;

    public RentalAgreementException(String name, String message) {
        super(message);
        this.name = name;
    }

    public RentalAgreementException() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
