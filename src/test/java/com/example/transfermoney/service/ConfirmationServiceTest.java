package com.example.transfermoney.service;

import com.example.transfermoney.entity.Confirmation;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.entity.OperationStatus;
import com.example.transfermoney.exception.ErrorConfirmationException;
import com.example.transfermoney.exception.ErrorConfirmationInputDataException;
import com.example.transfermoney.repository.OperationsRepository;
import com.example.transfermoney.util.Logger;
import com.example.transfermoney.util.MappingDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ConfirmationServiceTest {
    @InjectMocks
    private ConfirmationService confirmationService;
    @Mock
    private OperationsRepository operationsRepository;
    @Mock
    private Logger logger;
    @Mock
    private Operation operation;
    @Mock
    private MappingDto mappingDto;


    private Confirmation confirmation;

    @BeforeEach
    protected void beforeEach() {
        MockitoAnnotations.initMocks(this);
        confirmationService = new ConfirmationService(operationsRepository, logger, mappingDto);
        confirmation = new Confirmation("A1", "0000");
    }

    @AfterEach
    protected void afterEach() {
        confirmationService = null;
        confirmation = null;
    }

    @Test
    protected void confirmOperationTest_sucess() {

        when(operationsRepository.getById(Mockito.any())).thenReturn(Optional.ofNullable(operation));
        when(operation.getSecretCode()).thenReturn("0000");
        when(operation.getStatus()).thenReturn(OperationStatus.WAITING_CONFIRMATION);

        confirmationService.confirmOperation(confirmation);
        verify(mappingDto, Mockito.times(1)).operationToDto(Mockito.any());

    }

    @Test
    protected void confirmOperationTest_errorConfirmationException() {

        when(operationsRepository.getById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        Class<ErrorConfirmationException> expected = ErrorConfirmationException.class;
        Assertions.assertThrows(expected, () -> confirmationService.confirmOperation(confirmation));

    }

    @Test
    protected void confirmOperationTest_errorConfirmationInputDataException() {

        when(operationsRepository.getById(Mockito.any())).thenReturn(Optional.ofNullable(operation));
        when(operation.getSecretCode()).thenReturn("0001");
        when(operation.getStatus()).thenReturn(OperationStatus.WAITING_CONFIRMATION);

        Class<ErrorConfirmationInputDataException> expected = ErrorConfirmationInputDataException.class;
        Assertions.assertThrows(expected, () -> confirmationService.confirmOperation(confirmation));
    }

    @Test
    protected void messageForLoggerTest() {
        when(operation.getId()).thenReturn("1");
        when(operation.getStatus()).thenReturn(OperationStatus.SUCCESS);

        String expected = "Operationid=1changedstatus.Operationstatus=SUCCESS";
        String actual = confirmationService.messageForLogger(operation).replaceAll(" ", "").replaceAll("\r\n", "");

        Assertions.assertEquals(expected, actual.substring(actual.length() - expected.length()));
    }
}
