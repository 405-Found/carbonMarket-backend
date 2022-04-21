package com.fof.found.carbonio.entity;

import com.fof.found.carbonio.entity.activity.TransportationType;
import lombok.Data;

@Data
public class DailyShare {
    private float percentage;
    private TransportationType type;
    //Actual amount of carbon emission
    private float amount;
}
