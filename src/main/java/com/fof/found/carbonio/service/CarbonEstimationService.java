package com.fof.found.carbonio.service;

import com.fof.found.carbonio.calculationModel.EmissionModel;
import com.fof.found.carbonio.entity.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarbonEstimationService {
    public float estimateWithActivity(Activity activity, EmissionModel model){
        return model.calculate(activity);
    }
}
