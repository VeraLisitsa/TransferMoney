package com.example.transfermoney.entity;

public enum OperationStatus {
    SUCCESS,
    ERROR_INVALID_REQUISITES,
    ERROR_INSUFFICIENT_FUNDS_ON_CARD,
    WAITING_CONFIRMATION,
    ERROR_CONFIRMATION;
}
