package com.fof.found.carbonio.entity;

import com.fof.found.carbonio.entity.activity.ActivityItem;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(indexName = "activities",createIndex = true)
public class Activity {
    private UUID userID;
    private String id;
    private String activityName;
    private LocalDateTime date;
    private ActivityItem activityItem;
    private String description;

}
