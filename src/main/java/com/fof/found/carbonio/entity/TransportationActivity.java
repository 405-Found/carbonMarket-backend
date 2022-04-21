package com.fof.found.carbonio.entity;

import lombok.Data;

@Data
public class TransportationActivity extends Activity{
    private String transPortationType;
    //Further refine to geolocation
    private float distance;
}
