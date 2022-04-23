package com.fof.found.carbonio.calculationModel;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.activity.DailyPlan;
import com.fof.found.carbonio.entity.activity.TransportationType;

public interface EmissionModel {
    float calculate(Activity activity);
    float calculateByDuration(TransportationType type,float duration);
}
