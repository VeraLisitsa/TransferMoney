package com.example.transfermoney.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorTransferException extends RuntimeException {

    public ErrorTransferException(String message) {
        super(message);
    }
}
