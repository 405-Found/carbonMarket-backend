package com.fof.found.carbonio.entity.redisModel;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Friend")
public class Friend implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    private String name;
    private String email;
    private float carbonCredit;

    public Friend() {
    }

    public Friend(String name, String email, float carbonCredit) {
        this.name = name;
        this.email = email;
        this.carbonCredit = carbonCredit;
    }
}
