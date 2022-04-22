package com.fof.found.carbonio.entity;

import com.fof.found.carbonio.entity.activity.TransportationType;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

@Data
@Document(indexName = "userstatus")
public class UserStatus {
    private UUID userID;
    private String id;
    //the current carbonEmission amount
    private float curCarbonEmission;
    private LocalDate localDate;
    private HashMap<TransportationType, Share> Shares;
    private GreenLevel greenLevel;

    public UserStatus() {
        this.Shares = new HashMap<>();
        for(TransportationType type:TransportationType.values()){
            this.Shares.put(type,new Share(type));
        }
    }
}
