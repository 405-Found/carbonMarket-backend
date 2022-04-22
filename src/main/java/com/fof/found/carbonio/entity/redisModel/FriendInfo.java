package com.fof.found.carbonio.entity.redisModel;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("FriendInfo")
public class FriendInfo  implements Serializable {
    private String name;
    private String email;
}
