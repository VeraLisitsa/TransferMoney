package com.example.transfermoney.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Operation {
    @EqualsAndHashCode.Include
    private String id;

    private String cardFromNumber;

    private String cardFromValidTill;

    private String cardFromCVV;

    private String cardToNumber;

    private Amount amount;

    @Setter(AccessLevel.NONE)
    private String dateOperation;

    @Setter(AccessLevel.NONE)
    private String timeOperation;

    private OperationStatus status;

    private Double commission;

    @ToString.Exclude
    private String secretCode;


    public Operation(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;

    }

    public void setDateOperation() {
        this.dateOperation = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now());
    }

    public void setTimeOperation() {
        this.timeOperation = DateTimeFormatter.ofPattern("kk:mm:ss").format(LocalTime.now());
    }

}
