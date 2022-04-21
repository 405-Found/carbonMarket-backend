package com.fof.found.carbonio.entity.activity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityItem {
    private TransportationType type;
    private String subtype;
    private String departurePos;
    private String arrivalPos;
    private LocalDateTime start;
    private LocalDateTime end;
    private float duration;
    private float distance;
    private float carbonAmount;

}
