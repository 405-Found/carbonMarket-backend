package com.fof.found.carbonio.entity.activity;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class DailyPlan {
    private HashMap<TransportationType,PlanItem> dailyItems;


    public DailyPlan(List<PlanItem> items) {
        this.dailyItems = new HashMap<>();
        for(PlanItem item:items){
            dailyItems.put(item.getType(),item);
        }
    }
}
