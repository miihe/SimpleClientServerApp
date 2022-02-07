package com.javastart.dataperson;

import java.io.Serializable;

public class Account implements Serializable {
    private static final long serialVersionUID = 123L;
    private String name;
    private Bill bill;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", bill=" + bill +
                '}';
    }
}
