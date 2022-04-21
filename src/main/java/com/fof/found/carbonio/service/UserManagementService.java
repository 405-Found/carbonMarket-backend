package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.DailyShare;
import com.fof.found.carbonio.entity.UserStatus;
import com.fof.found.carbonio.entity.activity.ActivityItem;
import com.fof.found.carbonio.entity.activity.TransportationType;
import com.fof.found.carbonio.repository.ActivityRepository;
import com.fof.found.carbonio.repository.UserCurrentStatusRepository;
import com.fof.found.carbonio.repository.UserRepository;
import com.fof.found.carbonio.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserManagementService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    UserCurrentStatusRepository statusRepository;

    public User findUserByEmail(String email){
        Page<User> page = userRepository.findByEmail(email, Pageable.ofSize(1));
        return page.getContent().isEmpty() ? null : page.getContent().get(0);
    }

    public void registerUser(User user){
        userRepository.save(user);
    }
    public void createUserActivity(Activity activity, String email){
        //find corresponding user
        User user = findUserByEmail(email);
        //add timestamp on activity
        activity.setDate(LocalDateTime.now());
        activity.setUserID(user.getUserID());

        //do Calculations on carbon emission

        //TODO  setup the carbonAmount
        //update current User status
        updateCurrentUserStatus(activity,user);
        //save activity in database (es)
        activityRepository.save(activity);
        //save activity in redis
    }
    private void updateCurrentUserStatus(Activity activity,User user){
        UserStatus curStatus = statusRepository.findByUserID(user.getUserID(),Pageable.ofSize(1)).getContent().get(0);
        //update the data of user status in the database
        float carbonEmission = curStatus.getCurCarbonEmission();
        for(ActivityItem item: activity.getActivityItems()){
            DailyShare dailyShare = curStatus.getDailyShares().get(item.getType());
            float amount = dailyShare.getAmount()+ item.getCarbonAmount();
            dailyShare.setAmount(amount);
            //update the overall carbon emission
            carbonEmission = carbonEmission+ item.getCarbonAmount();

        }
        for(DailyShare share: curStatus.getDailyShares().values()){
            share.setPercentage(share.getAmount()/carbonEmission);
        }
        //Set the green level of the userStatus


    }
    public UserStatus getUserStatus(User user){
        //TODO further version, we get UserStatus from redis, which could expires
        return statusRepository.findByUserID(user.getUserID(),Pageable.ofSize(1)).getContent().get(0);
    }
}
