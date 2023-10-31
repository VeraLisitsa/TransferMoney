package com.example.transfermoney.entity;

import java.util.Objects;

public class CreditCard {
    private String number;
    private String validTill;
    private String cvv;
    private double balance;

    public CreditCard() {
    }

    public CreditCard(String number, String validTill, String cvv) {
        this.number = number;
        this.validTill = validTill;
        this.cvv = cvv;
    }

    public CreditCard(String number, String validTill, String cvv, double balance) {
        this.number = number;
        this.validTill = validTill;
        this.cvv = cvv;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "number='" + number + '\'' +
                ", validTill='" + validTill + '\'' +
                ", cvv='" + cvv + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(number, that.number) && Objects.equals(validTill, that.validTill) && Objects.equals(cvv, that.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, validTill, cvv);
    }
}
