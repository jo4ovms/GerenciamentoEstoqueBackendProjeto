package com.cadastro1.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SalesData {

    private int day;
    private int sales;

    public SalesData(int day, int sales) {
        this.day = day;
        this.sales = sales;
    }
}
