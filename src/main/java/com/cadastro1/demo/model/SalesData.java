package com.cadastro1.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesData {
    private String date;
    private int sales;

    public SalesData(String date, int sales) {
        this.date = date;
        this.sales = sales;
    }
}
