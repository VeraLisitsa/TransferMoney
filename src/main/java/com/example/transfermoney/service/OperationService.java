package com.example.transfermoney.service;

import com.example.transfermoney.entity.CreditCard;
import com.example.transfermoney.entity.Operation;
import com.example.transfermoney.entity.OperationStatus;
import com.example.transfermoney.exception.ErrorInputDataException;
import com.example.transfermoney.exception.ErrorTransferException;
import com.example.transfermoney.repository.CardRepository;
import com.example.transfermoney.repository.OperationsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OperationService {
    private final CardRepository cardRepository;
    private final OperationsRepository operationsRepository;
    private final Logger logger;
    private static final double COMMISSION_RATE = 0.01;
    private static final String SECRET_CODE = "0000";

    private final AtomicLong count = new AtomicLong(0);

    public OperationService(CardRepository cardRepository, OperationsRepository operationsRepository, Logger logger) {
        this.cardRepository = cardRepository;
        this.operationsRepository = operationsRepository;
        this.logger = logger;
    }

    public String operationHandler(Operation operation) {
        try {
            long id = count.incrementAndGet();
            operation.setId(Long.toString(id));
            operationsRepository.addOperation(operation);
            operation.setDateOperation();
            operation.setTimeOperation();
            CreditCard creditCardFrom = getCreditCardFrom(operation);
            CreditCard getCreditCardTo = getCreditCardTo(operation);
            transferMoney(creditCardFrom, getCreditCardTo, operation);
            return operation.getId();
        } finally {
            logger.writeToLoggerFile(messageForLogger(operation));
        }
    }

    protected CreditCard getCreditCardFrom(Operation operation) {

        if (cardRepository.getByNumber(operation.getCardFromNumber()).isPresent()) {
            CreditCard creditCardFrom = cardRepository.getByNumber(operation.getCardFromNumber()).get();
            if (creditCardFrom.getValidTill().equals(operation.getCardFromValidTill()) &&
                    creditCardFrom.getCvv().equals(operation.getCardFromCVV())) {
                return creditCardFrom;
            } else {
                operation.setStatus(OperationStatus.ERROR_INVALID_REQUISITES);
                throw new ErrorInputDataException();
            }
        } else {
            operation.setStatus(OperationStatus.ERROR_INVALID_REQUISITES);
            throw new ErrorInputDataException();
        }
    }


    protected CreditCard getCreditCardTo(Operation operation) {
        if (cardRepository.getByNumber(operation.getCardToNumber()).isPresent()) {
            return cardRepository.getByNumber(operation.getCardToNumber()).get();
        } else {
            operation.setStatus(OperationStatus.ERROR_INVALID_REQUISITES);
            throw new ErrorInputDataException();
        }

    }

    protected void transferMoney(CreditCard creditCardFrom, CreditCard creditCardTo, Operation operation) {
        double creditCardFromBalance = creditCardFrom.getBalance();
        double amountTransfer = (double) operation.getAmount().getValue() / 100;
        double commission = calculationCommission(amountTransfer, operation);
        double creditCardToBalance = creditCardTo.getBalance();
        if (creditCardFromBalance >= amountTransfer) {
            creditCardFrom.setBalance(creditCardFromBalance - amountTransfer);
            creditCardTo.setBalance(creditCardToBalance + amountTransfer - commission);
            operation.setSecretCode(SECRET_CODE);
            operation.setStatus(OperationStatus.WAITING_CONFIRMATION);
        } else {
            operation.setStatus(OperationStatus.ERROR_INSUFFICIENT_FUNDS_ON_CARD);
            throw new ErrorTransferException();
        }
    }

    protected double calculationCommission(double amount, Operation operation) {
        double commission = amount * COMMISSION_RATE;
        operation.setCommission(commission);
        return commission;
    }

    protected String messageForLogger(Operation operation) {
        return "[" + DateTimeFormatter.ofPattern("dd-MM-yyyy, kk:mm:ss")
                .format(LocalDateTime.now()) + "] Operation id = " + operation.getId() +
                ", Operation date = " + operation.getDateOperation() +
                ", Operation time = " + operation.getTimeOperation() +
                ", Credit Card From = " + operation.getCardFromNumber() +
                ", Credit Card To = " + operation.getCardToNumber() +
                ", Amount = " + operation.getAmount().getValue() / 100 +
                " " + operation.getAmount().getCurrency() +
                ", Commission = " + operation.getCommission() +
                ", Operation status = " + operation.getStatus();
    }

    protected AtomicLong getCount() {
        return count;
    }
}
