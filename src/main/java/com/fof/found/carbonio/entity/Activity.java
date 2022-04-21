package com.fof.found.carbonio.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Activity {
    private String activityName;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    //carbon emission amount
    private float carbonAmount;

}
