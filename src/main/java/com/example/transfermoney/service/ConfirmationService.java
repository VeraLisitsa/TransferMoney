package com.example.transfermoney.service;

import com.example.transfermoney.entity.Confirmation;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.entity.OperationStatus;
import com.example.transfermoney.exception.ErrorConfirmationException;
import com.example.transfermoney.exception.ErrorConfirmationInputDataException;
import com.example.transfermoney.repository.OperationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ConfirmationService {
    private final OperationsRepository operationsRepository;
    private final Logger logger;

    public ConfirmationService(OperationsRepository operationsRepository, Logger logger) {
        this.operationsRepository = operationsRepository;
        this.logger = logger;
    }

    public String confirmOperation(Confirmation confirmation){
            if (!operationsRepository.getById(confirmation.getOperationId()).isPresent()) {
                logger.writeToLoggerFile("[" + DateTimeFormatter.ofPattern("dd-MM-yyyy, kk:mm:ss)")
                                        .format(LocalDateTime.now()) + "] Operation id = " + confirmation.getOperationId() + " not exist");
                throw new ErrorConfirmationException();
            };
            Operation operation = operationsRepository.getById(confirmation.getOperationId()).get();
            if (operation.getSecretCode().equals(confirmation.getCode())) {
                operation.setStatus(OperationStatus.SUCCESS);
                logger.writeToLoggerFile(messageForLogger(operation));
                return confirmation.getOperationId();
            } else {
                operation.setStatus(OperationStatus.ERROR_CONFIRMATION);
                logger.writeToLoggerFile(messageForLogger(operation));
                throw new ErrorConfirmationInputDataException();
            }


    }

    protected String messageForLogger(Operation operation){
        return "[" + DateTimeFormatter.ofPattern("dd-MM-yyyy, kk:mm:ss")
                .format(LocalDateTime.now()) + "] Operation id = " + operation.getId() +
                " changed status. Operation status = " + operation.getStatus();
    }

}
