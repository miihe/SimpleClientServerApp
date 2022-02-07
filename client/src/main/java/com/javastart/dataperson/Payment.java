package com.javastart.dataperson;

import java.io.Serializable;

public class Payment implements Serializable {
    private static final long serialVersionUID = 123L;
    private Integer pay;

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "pay = " + pay +
                "$}";
    }
}
