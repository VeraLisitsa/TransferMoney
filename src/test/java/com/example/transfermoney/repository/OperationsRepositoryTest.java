package com.example.transfermoney.repository;

import com.example.transfermoney.entity.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class OperationsRepositoryTest {
    private OperationsRepository operationsRepository;
    @Mock
    private Operation operation1;
    @Mock
    private Operation operation2;


    @BeforeEach
    protected void beforeEach() {
        MockitoAnnotations.initMocks(this);
        operationsRepository = new OperationsRepository();

        when(operation1.getId()).thenReturn("1");
        when(operation2.getId()).thenReturn("2");

        operationsRepository.addOperation(operation1);
        operationsRepository.addOperation(operation2);
    }

    @AfterEach
    protected void afterEach() {
        operationsRepository = null;
    }

    @Test
    protected void addOperationTest() {
        int expected = operationsRepository.allOperations().size() + 1;
        final Operation operation = Mockito.mock(Operation.class);
        when(operation.getId()).thenReturn("3");

        operationsRepository.addOperation(operation);
        int actual = operationsRepository.allOperations().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void allOperationsTest() {

        int expected = 2;
        int actual = operationsRepository.allOperations().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void getByIdTest_exist() {
        Boolean expected = true;

        Boolean actual = operationsRepository.getById("1").isPresent();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void getByIdTest_notExist() {
        Boolean expected = false;

        Boolean actual = operationsRepository.getById("4").isPresent();

        Assertions.assertEquals(expected, actual);
    }
}
