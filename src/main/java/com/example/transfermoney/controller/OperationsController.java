package com.example.transfermoney.controller;

import com.example.transfermoney.dto.OperationDto;
import com.example.transfermoney.entity.Confirmation;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.service.ConfirmationService;
import com.example.transfermoney.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://serp-ya.github.io")
@AllArgsConstructor

public class OperationsController {

    private final OperationService operationService;
    private final ConfirmationService confirmationService;

    @PostMapping(path = "${endpoint.transfer}")

    public OperationDto transfer(@RequestBody Operation operation) {
        return operationService.operationHandler(operation);
    }

    @PostMapping(path = "${endpoint.confirm}")
    public OperationDto confirmOperation(@RequestBody Confirmation confirmation) {
        return confirmationService.confirmOperation(confirmation);
    }

}
