package com.fof.found.carbonio.entity;

import com.fof.found.carbonio.entity.activity.TransportationType;
import lombok.Data;

@Data
public class Share {
    private float percentage;
    private TransportationType type;
    //Actual amount of carbon emission
    private float amount;

    public Share(TransportationType type) {
        this.type = type;
    }
}
