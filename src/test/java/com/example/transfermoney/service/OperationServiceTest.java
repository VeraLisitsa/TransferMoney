package com.example.transfermoney.service;

import com.example.transfermoney.entity.Amount;
import com.example.transfermoney.entity.CreditCard;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.entity.OperationStatus;
import com.example.transfermoney.exception.ErrorInputDataException;
import com.example.transfermoney.exception.ErrorTransferException;
import com.example.transfermoney.repository.CardRepository;
import com.example.transfermoney.repository.OperationsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class OperationServiceTest {

    private CardRepository cardRepository;
    @Mock
    private OperationsRepository operationsRepository;
    @Mock
    private Logger logger;
    @Mock
    private Operation operation;
    @Mock
    private CreditCard creditCardFrom;
    @Mock
    private CreditCard creditCardTo;
    @InjectMocks
    private OperationService operationService;

    @BeforeEach
    protected void beforeEach() {
        MockitoAnnotations.initMocks(this);
        cardRepository = new CardRepository();

        operationService = new OperationService(cardRepository, operationsRepository, logger);

        when(operation.getCardFromNumber()).thenReturn("1111222233334444");
        when(operation.getCardFromValidTill()).thenReturn("12/24");
        when(operation.getCardFromCVV()).thenReturn("111");
        when(operation.getCardToNumber()).thenReturn("5555666677778888");
        when(operation.getAmount()).thenReturn(new Amount(500000, "rub"));
        when(operation.getStatus()).thenReturn(OperationStatus.WAITING_CONFIRMATION);
        when(operation.getCommission()).thenReturn(50.0);
        when(operation.getId()).thenReturn("1");

    }

    @AfterEach
    protected void afterEach() {
        cardRepository = null;
        operationService = null;
    }


    @Test
    protected void calculationCommissionTest() {
        final double amount = 1000.0;

        double expected = 10.0;
        double actual = operationService.calculationCommission(amount, operation);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void getCreditCardFromTest() {

        CreditCard expected = new CreditCard("1111222233334444", "12/24", "111");
        CreditCard actual = operationService.getCreditCardFrom(operation);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("methodSource")
    protected void getCreditCardFromTest_exception(Operation operationMethodSource) {
        Class<ErrorInputDataException> expected = ErrorInputDataException.class;

        Assertions.assertThrows(expected, () -> operationService.getCreditCardFrom(operationMethodSource));

    }

    public static Stream<Arguments> methodSource() {

        return Stream.of(
                Arguments.of(new Operation("1111222233334444", "12/24", "112", "", new Amount(0, ""))),
                Arguments.of(new Operation("1111222233334444", "12/25", "111", "", new Amount(0, ""))),
                Arguments.of(new Operation("1111222233334445", "12/24", "111", "", new Amount(0, ""))));

    }

    @Test
    protected void getCreditCardToTest() {

        CreditCard expected = new CreditCard("5555666677778888", "12/24", "111");
        CreditCard actual = operationService.getCreditCardTo(operation);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void getCreditCardToTest_exception() {
        final Operation operationException = mock(Operation.class);
        when(operationException.getCardToNumber()).thenReturn("1111222233334445");

        Class<ErrorInputDataException> expected = ErrorInputDataException.class;

        Assertions.assertThrows(expected, () -> operationService.getCreditCardTo(operationException));
    }

    @Test
    protected void transferMoneyTest() {

        when(creditCardFrom.getBalance()).thenReturn(10000.0);

        operationService.transferMoney(creditCardFrom, creditCardTo, operation);

        Mockito.verify(operation, Mockito.times(1)).setStatus(Mockito.any());
    }

    @Test
    protected void transferMoneyTest_exception() {
        when(creditCardFrom.getBalance()).thenReturn(10000.0);

        final Operation operationException = mock(Operation.class);
        when(operationException.getAmount()).thenReturn(new Amount(50000000, "rub"));

        Class<ErrorTransferException> expected = ErrorTransferException.class;

        Assertions.assertThrows(expected, () -> operationService.transferMoney(creditCardFrom, creditCardTo, operationException));
    }

    @Test
    protected void operationHandlerTest() {
        when(logger.writeToLoggerFile(Mockito.any())).thenReturn(true);
        when(operationsRepository.addOperation(Mockito.any())).thenReturn(true);

        String expected = "1";
        String actual = operationService.operationHandler(operation);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void messageForLoggerTest() {

        String expected = "CreditCardFrom=1111222233334444,CreditCardTo=5555666677778888,Amount=5000rub,Commission=50.0,Operationstatus=WAITING_CONFIRMATION";
        String actual = operationService.messageForLogger(operation).replaceAll(" ", "").replaceAll("\r\n", "");

        Assertions.assertEquals(expected, actual.substring(actual.length() - expected.length()));
    }
}
