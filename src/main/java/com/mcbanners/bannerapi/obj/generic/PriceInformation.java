package com.mcbanners.bannerapi.obj.generic;

public class PriceInformation {
    private double amount;
    private final String currency;

    public PriceInformation(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }
}
