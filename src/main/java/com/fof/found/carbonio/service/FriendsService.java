package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.User;
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
    @Autowired
    private UserManagementService userManagementService;

    // inject the template as ListOperations
    @Resource(name="redisTemplate")
    private ZSetOperations<String, Friend> ZSets;

    public List<Friend> getFriends(String email){
        Set<Friend> sets = ZSets.range(email,0,ZSets.zCard(email));
        ArrayList<Friend> friends = new ArrayList<>();
        sets.forEach(friends::add);
        friends.sort((x1,x2)->(x1.getCarbonCredit()> x2.getCarbonCredit()?-1:1));
        return friends;
    }
    public void addFriend(Friend friend,String email){
        ZSets.add(email,friend, friend.getCarbonCredit());
        User user = userManagementService.findUserByEmail(email);
        ZSets.add(friend.getEmail(), new Friend(user.getUserName(), user.getEmail(), user.getCarbonCredit()), user.getCarbonCredit());
    }
    public void giveGift(String friendEmail,String token,float amount){
        User user = userManagementService.findUserByToken(token);
        User friend = userManagementService.findUserByEmail(friendEmail);
        //refresh leaderboard here
        refreshLeaderBoardDiscard(user,friend);
        user.setCarbonCredit(user.getCarbonCredit()-amount);
        friend.setCarbonCredit(friend.getCarbonCredit()+amount);
        userManagementService.updateUser(user);
        userManagementService.updateUser(friend);
        //refresh the leaderboard
        refreshLeaxerBoardAdd(user,friend);

    }
    private void refreshLeaderBoardDiscard(User user,User friend){
        Friend userF = new Friend(user.getUserName(), user.getEmail(), user.getCarbonCredit());
        Friend friendF = new Friend(friend.getUserName(), friend.getEmail(), friend.getCarbonCredit());
        ZSets.remove(user.getEmail(),userF);
        ZSets.remove(user.getEmail(),friendF);
        ZSets.remove(friend.getEmail(),friendF);
        ZSets.remove(friend.getEmail(),userF);
    }
    private void refreshLeaxerBoardAdd(User user,User friend){
        Friend userF = new Friend(user.getUserName(), user.getEmail(), user.getCarbonCredit());
        Friend friendF = new Friend(friend.getUserName(), friend.getEmail(), friend.getCarbonCredit());
        ZSets.add(user.getEmail(),userF, userF.getCarbonCredit());
        ZSets.add(user.getEmail(),friendF, friendF.getCarbonCredit());
        ZSets.add(friend.getEmail(),userF, userF.getCarbonCredit());
        ZSets.add(friend.getEmail(),friendF, friendF.getCarbonCredit());
    }
    //single side
    public void deleteFriendOnLeaderBoard(Friend friend,String email){
        ZSets.remove(email,friend);
    }
    public void addFriendOnLeaderBoard(Friend friend,String email){
        ZSets.add(email,friend, friend.getCarbonCredit());
    }


}
