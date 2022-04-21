package com.fof.found.carbonio.entity;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class User {
    private UUID userID;
    private String userName;
    private String email;
    private boolean isCompanyUser;
    //How much you could use to donate
    private float balance;

    private UserStatus currentStatus;

    private List<Activity> activities;

}
