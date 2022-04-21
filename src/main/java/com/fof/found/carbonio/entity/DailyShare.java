package com.fof.found.carbonio.entity;

import lombok.Data;

@Data
public class DailyShare {
    private int percentage;
    private String type;
    //Actual amount of carbon emission
    private float amount;
}
