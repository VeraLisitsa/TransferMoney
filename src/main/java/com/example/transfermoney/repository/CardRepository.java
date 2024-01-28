package com.example.transfermoney.repository;

import com.example.transfermoney.entity.CreditCard;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CardRepository {

    private final ConcurrentHashMap<String, CreditCard> creditCardList = new ConcurrentHashMap<>();

    public CardRepository() {
        creditCardList.put("1111222233334444", new CreditCard("1111222233334444", "12/24", "111", 100000));
        creditCardList.put("5555666677778888", new CreditCard("5555666677778888", "12/24", "111", 12000));

    }

    public List<CreditCard> allCreditCards() {
        List<CreditCard> list = creditCardList.values().stream()
                .collect(Collectors.toList());
        return list;
    }

    public Optional<CreditCard> getByNumber(String creditCardNumber) {
        CreditCard creditCard = creditCardList.get(creditCardNumber);
        return Optional.ofNullable(creditCard);
    }

    public void addCreditCard(CreditCard creditCard) {
        creditCardList.put(creditCard.getNumber(), creditCard);
    }
}
