package com.example.transfermoney.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorConfirmationException extends RuntimeException {

    public ErrorConfirmationException(String message) {
        super(message);
    }
}
