package com.example.transfermoney.service;

import com.example.transfermoney.dto.OperationDto;
import com.example.transfermoney.entity.Confirmation;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.entity.OperationStatus;
import com.example.transfermoney.exception.ErrorConfirmationException;
import com.example.transfermoney.exception.ErrorConfirmationInputDataException;
import com.example.transfermoney.repository.OperationsRepository;
import com.example.transfermoney.util.Logger;
import com.example.transfermoney.util.MappingDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationService {
    private final OperationsRepository operationsRepository;
    private final Logger logger;
    private final MappingDto mappingDto;

    public OperationDto confirmOperation(Confirmation confirmation) {
        logger.createLogFile();
        if (!operationsRepository.getById(confirmation.getOperationId()).isPresent()) {
            logger.writeToLoggerFile(" Operation id = " + confirmation.getOperationId() + " not exist");
            throw new ErrorConfirmationException("Error customer message");
        }

        Operation operation = operationsRepository.getById(confirmation.getOperationId()).get();
        if (operation.getStatus() != OperationStatus.WAITING_CONFIRMATION) {
            logger.writeToLoggerFile(" Operation id = " + confirmation.getOperationId() + " is failed");
            throw new ErrorConfirmationException("Error customer message");
        }
        if (operation.getSecretCode().equals(confirmation.getCode())) {
            operation.setStatus(OperationStatus.SUCCESS);
            logger.writeToLoggerFile(messageForLogger(operation));
            return mappingDto.operationToDto(operation);
        } else {
            operation.setStatus(OperationStatus.ERROR_CONFIRMATION);
            logger.writeToLoggerFile(messageForLogger(operation));
            throw new ErrorConfirmationInputDataException("Error customer message");
        }


    }

    protected String messageForLogger(Operation operation) {
        return " Operation id = " + operation.getId() +
                " changed status. Operation status = " + operation.getStatus();
    }

}
