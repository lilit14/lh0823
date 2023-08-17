package com.pos.service.toolsRentalSystem.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class RentalAgreementException extends RuntimeException {
    private String name;

    public RentalAgreementException(String name, String message) {
        super(message);
        this.name = name;
    }

    public RentalAgreementException() {}
}
