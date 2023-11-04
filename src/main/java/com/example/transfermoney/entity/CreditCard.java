package com.example.transfermoney.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CreditCard {
    private String number;
    private String validTill;
    private String cvv;

    @EqualsAndHashCode.Exclude
    private double balance;

    public CreditCard(String number, String validTill, String cvv) {
        this.number = number;
        this.validTill = validTill;
        this.cvv = cvv;
    }

}
