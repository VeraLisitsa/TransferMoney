package com.example.transfermoney.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorConfirmationInputDataException extends RuntimeException {

    public ErrorConfirmationInputDataException(String message) {
        super(message);
    }
}
