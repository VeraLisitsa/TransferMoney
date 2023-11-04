package com.example.transfermoney.repository;

import com.example.transfermoney.entity.CreditCard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CardRepositoryTest {

    private CardRepository cardRepository;

    @BeforeEach
    protected void beforeEach() {
        cardRepository = new CardRepository();

    }

    @AfterEach
    protected void afterEach() {
        cardRepository = null;
    }

    @Test
    protected void getByNumberTest_exist() {
        CreditCard expected = new CreditCard("1111222233334444", "12/24", "111");
        Boolean isPresentExpected = true;

        Boolean isPresentResult = cardRepository.getByNumber("1111222233334444").isPresent();
        CreditCard actual = cardRepository.getByNumber("1111222233334444").get();

        Assertions.assertEquals(isPresentExpected, isPresentResult);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void getByNumberTest_notExist() {
        Boolean isPresentExpected = false;

        Boolean isPresentActual = cardRepository.getByNumber("1111222211112222").isPresent();

        Assertions.assertEquals(isPresentExpected, isPresentActual);
    }

    @Test
    protected void allCreditCardsTest() {
        int expected = 2;
        int actual = cardRepository.allCreditCards().size();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    protected void addCreditCardTest() {
        int expected = 3;
        cardRepository.addCreditCard(new CreditCard("2222111122223333", "12/25", "222", 1000));
        int actual = cardRepository.allCreditCards().size();

        Assertions.assertEquals(expected, actual);

    }
}
