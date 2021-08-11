package com.javastart.com.javastart.dataperson;

import java.io.Serializable;

public class Adjustment implements Serializable {
    private static final long serialVersionUID = 123L;
    private Integer adjustment;

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public String toString() {
        return "Adjustment{" +
                "adjustment = " + adjustment +
                "$}";
    }
}
