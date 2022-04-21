package com.fof.found.carbonio.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(indexName = "users",createIndex = true)
public class User {
    private String id;
    private UUID userID;
    private String userName;
    private String password;
    private String email;
    private boolean isCompanyUser;
    //How much you could use to donate
    private float balance;

    private UserStatus currentStatus;

    private List<Activity> activities;

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User() {
    }
}
