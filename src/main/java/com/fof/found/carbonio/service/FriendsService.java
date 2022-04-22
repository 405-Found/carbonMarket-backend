package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.redisModel.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class FriendsService {
    @Autowired
    private RedisTemplate<String, Friend> template;

    // inject the template as ListOperations
    @Resource(name="redisTemplate")
    private ZSetOperations<String, Friend> ZSets;

    public List<Friend> getFriends(String email){
        Set<Friend> sets = ZSets.range(email,1,10);
        ArrayList<Friend> friends = new ArrayList<>();
        sets.forEach(friends::add);
        friends.sort((x1,x2)->(x1.getCarbonCredit()> x2.getCarbonCredit()?-1:1));
        return friends;
    }
    public void addFriend(Friend friend,String email){
        ZSets.add(email,friend, friend.getCarbonCredit());
    }

}
