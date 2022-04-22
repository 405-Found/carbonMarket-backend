package com.fof.found.carbonio.entity.activity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_time_no_millis;

@Data
public class ActivityItem {
    private TransportationType type;
    private String subtype;
    private String departurePos;
    private String arrivalPos;
    @Field(type = FieldType.Date,format=date_time_no_millis)
    private ZonedDateTime start;
    @Field(type = FieldType.Date,format=date_time_no_millis)
    private ZonedDateTime end;
    private float duration;
    private float distance;
    private float carbonAmount;

}
