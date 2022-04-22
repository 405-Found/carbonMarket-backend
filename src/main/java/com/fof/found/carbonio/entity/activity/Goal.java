package com.fof.found.carbonio.entity.activity;

import lombok.Data;

@Data
public class Goal {
    private float goalToday;
    private float curStatus;

    public Goal(float goalToday) {
        this.goalToday = goalToday;
        this.curStatus = goalToday;
    }
}
