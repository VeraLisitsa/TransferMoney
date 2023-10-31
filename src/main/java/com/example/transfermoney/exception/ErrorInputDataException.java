package com.example.transfermoney.exception;

public class ErrorInputDataException extends RuntimeException {

    public ErrorInputDataException() {
    }

    public ErrorInputDataException(String message) {
        super(message);
    }
}
