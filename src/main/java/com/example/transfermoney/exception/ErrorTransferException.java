package com.example.transfermoney.exception;

public class ErrorTransferException extends RuntimeException {
    public ErrorTransferException() {
    }

    public ErrorTransferException(String message) {
        super(message);
    }
}
