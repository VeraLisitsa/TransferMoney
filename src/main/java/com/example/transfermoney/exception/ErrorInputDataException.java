package com.example.transfermoney.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorInputDataException extends RuntimeException {

    public ErrorInputDataException(String message) {
        super(message);
    }
}
