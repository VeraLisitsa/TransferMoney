package com.example.transfermoney.controller;

import com.example.transfermoney.entity.Confirmation;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.exception.ErrorConfirmationException;
import com.example.transfermoney.exception.ErrorConfirmationInputDataException;
import com.example.transfermoney.exception.ErrorInputDataException;
import com.example.transfermoney.exception.ErrorTransferException;
import com.example.transfermoney.service.ConfirmationService;
import com.example.transfermoney.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://serp-ya.github.io")

public class OperationsController {
    private static final String APPLICATION_JSON = "application/json";
    private static final String MESSAGE = "{\"message\": ";
    private static final String OPERATION_ID = "{\"operationId\": \"";
    private static final String ERROR_CUSTOMER_MESSAGE = "{\"message\": \"Error customer message\"}";
    private final OperationService operationService;
    private final ConfirmationService confirmationService;

    public OperationsController(OperationService operationService, ConfirmationService confirmationService) {
        this.operationService = operationService;
        this.confirmationService = confirmationService;
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody Operation operation) {
        return OPERATION_ID + operationService.operationHandler(operation) + "\"}";
    }

    @PostMapping("/confirmOperation")
    public String confirmOperation(@RequestBody Confirmation confirmation) {
        return OPERATION_ID + confirmationService.confirmOperation(confirmation) + "\"}";
    }

    @ExceptionHandler
    public ResponseEntity<String> errorInputDataExceptionHandler(ErrorInputDataException e) {
        return new ResponseEntity<>(MESSAGE + "\"Error input data\"}", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> errorTransferExceptionHandler(ErrorTransferException e) {
        return new ResponseEntity<>(MESSAGE + "\"Error transfer\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> errorConfirmationExceptionHandler(ErrorConfirmationException e) {
        return new ResponseEntity<>(ERROR_CUSTOMER_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> errorConfirmationInputDataExceptionHandler(ErrorConfirmationInputDataException e) {
        return new ResponseEntity<>(ERROR_CUSTOMER_MESSAGE, HttpStatus.BAD_REQUEST);
    }

}
