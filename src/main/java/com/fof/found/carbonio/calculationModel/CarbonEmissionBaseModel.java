package com.fof.found.carbonio.calculationModel;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.activity.DailyPlan;
import com.fof.found.carbonio.entity.activity.TransportationType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

@Component
public class CarbonEmissionBaseModel implements EmissionModel {
    @Value("#{${carbonEmissionMapping}}")
    private HashMap<TransportationType,Float> mappingUnit;


    @Override
    public float calculate(Activity activity) {
        Duration duration = Duration.between ( activity.getActivityItem().getStart() , activity.getActivityItem().getEnd());
        float durationInHour = duration.toMillis()/3600000f;
        return mappingUnit.get(activity.getActivityItem().getType())*durationInHour;
    }

    @Override
    public float calculateByDuration(TransportationType type,float duration) {
        return mappingUnit.get(type)*duration;
    }
}
