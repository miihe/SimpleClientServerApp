package com.javastart.com.javastart.dataperson;

import java.io.Serializable;

public class Bill implements Serializable {
    private static final long serialVersionUID = 123L;
    private Integer amount;

    public Bill(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "amount = " + amount +
                "$}";
    }
}
