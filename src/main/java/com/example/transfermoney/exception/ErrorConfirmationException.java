package com.example.transfermoney.exception;

public class ErrorConfirmationException extends RuntimeException {

    public ErrorConfirmationException() {
    }

    public ErrorConfirmationException(String message) {
        super(message);
    }
}
