package com.fof.found.carbonio.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserStatus {
    private UUID userID;
    //the current carbonEmission amount
    private float curCarbonEmission;
    private LocalDate localDate;
    private List<DailyShare> dailyShares;
    private GreenLevel greenLevel;
}
