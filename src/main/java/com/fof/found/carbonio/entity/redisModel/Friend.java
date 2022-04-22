package com.fof.found.carbonio.entity.redisModel;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Friend")
public class Friend implements Serializable {
    private String name;
    private String email;
    private float carbonCredit;
}
