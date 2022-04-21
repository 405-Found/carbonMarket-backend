package com.fof.found.carbonio.entity;

import com.fof.found.carbonio.entity.activity.TransportationType;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Data
public class UserStatus {
    private UUID userID;
    //the current carbonEmission amount
    private float curCarbonEmission;
    private LocalDate localDate;
    private HashMap<TransportationType,DailyShare> dailyShares;
    private GreenLevel greenLevel;
}
