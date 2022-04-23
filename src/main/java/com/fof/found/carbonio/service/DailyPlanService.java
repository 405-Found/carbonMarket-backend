package com.fof.found.carbonio.service;

import com.fof.found.carbonio.calculationModel.CarbonEmissionBaseModel;
import com.fof.found.carbonio.entity.activity.DailyPlan;
import com.fof.found.carbonio.entity.activity.PlanItem;
import com.fof.found.carbonio.entity.activity.TransportationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DailyPlanService {
    @Autowired
    CarbonEmissionBaseModel carbonEmissionBaseModel;
    public float calculateDailyEmission(DailyPlan dailyPlan){
        float sum =0;
        for(Map.Entry<TransportationType, PlanItem> entry:dailyPlan.getDailyItems().entrySet()){
            sum = sum+ carbonEmissionBaseModel.calculateByDuration(entry.getKey(),entry.getValue().getDuration());
        }
        return sum;
    }
}
