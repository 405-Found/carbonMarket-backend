package com.fof.found.carbonio.entity;

import com.fof.found.carbonio.entity.activity.ActivityItem;
import lombok.Data;
import org.elasticsearch.search.DocValueFormat;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(indexName = "activities10",createIndex = true)
public class Activity {
    @Field(type = FieldType.Keyword)
    private UUID userID;
    private String id;
    private String activityName;
    @Field(type = FieldType.Date)
    private LocalDate date;
    private ActivityItem activityItem;
    private String description;

}
