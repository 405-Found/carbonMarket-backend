package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.Share;
import com.fof.found.carbonio.entity.UserStatus;
import com.fof.found.carbonio.entity.activity.Goal;
import com.fof.found.carbonio.repository.ActivityRepository;
import com.fof.found.carbonio.repository.UserCurrentStatusRepository;
import com.fof.found.carbonio.repository.UserRepository;
import com.fof.found.carbonio.entity.User;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
public class UserManagementService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    UserCurrentStatusRepository statusRepository;
    @Autowired
    ElasticsearchRestTemplate restTemplate;

    public User findUserByEmail(String email){
        Page<User> page = userRepository.findByEmail(email, Pageable.ofSize(1));
        return page.getContent().isEmpty() ? null : page.getContent().get(0);
    }
    public User findUserByToken(String token){
        Page<User> page = userRepository.findByToken(token, Pageable.ofSize(1));
        return page.getContent().isEmpty() ? null : page.getContent().get(0);
    }

    public void registerUser(User user){
        UserStatus status = new UserStatus();
        status.setUserID(user.getUserID());
        user.setCurrentStatus(status);
        userRepository.save(user);
        statusRepository.save(status);
    }
    public void updateUser(User user){
        userRepository.save(user);
    }
    public void updateUserStatus(UserStatus userStatus){
        statusRepository.save(userStatus);
    }
    public void setGoalForUser(User user,float goal){
        Goal todayGoal = new Goal(goal);
        user.setTodayGoal(todayGoal);
        UserStatus userStatus = user.getCurrentStatus();
        if(userStatus == null){
            userStatus = new UserStatus();
            userStatus.setUserID(user.getUserID());
            //update the user with current user status
        }
        userStatus.setCurCarbonEmission(userStatus.getCurCarbonEmission()+goal);
        updateUser(user);
        updateUserStatus(userStatus);

    }
    public Activity createUserActivity(Activity activity, String token){
        //find corresponding user
        User user = findUserByToken(token);
        //add timestamp on activity
        activity.setDate(LocalDate.now());
        activity.setUserID(user.getUserID());

        //do Calculations on carbon emission

        //TODO  setup the carbonAmount
        //update current User status
        updateCurrentUserStatus(activity,user);
        //save activity in database (es)
        activityRepository.save(activity);
        //save activity in redis

        return activity;
    }
    public List<Activity> findActivityForToday(String token){
        User user = findUserByToken(token);
        if(user==null){
            return Collections.emptyList();
        }
        //TODO
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(rangeQuery("date")
                        .gte(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).toString()))
                .must(termQuery("userID",user.getUserID().toString().toLowerCase()));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();
        SearchHits<Activity> eventSearchHits = restTemplate.search(searchQuery,Activity.class, IndexCoordinates.of("activities10"));
        return eventSearchHits.getSearchHits().stream().map(x->x.getContent()).collect(Collectors.toList());
    }
    public List<Activity> findAllActivities(String token){
        User user = findUserByToken(token);
        if(user==null){
            return Collections.emptyList();
        }
        List<Activity> activities =activityRepository.findByUserID(user.getUserID(),Pageable.ofSize(15)).getContent();
        activities = new ArrayList<>(activities);
        activities.sort((x1,x2)->(x1.getDate().isAfter(x2.getDate())?1:-1));
        return activities;
    }
    private void updateUserGoalStatus(User user,Activity activity){
        //TODO
    }
    private void updateCurrentUserStatus(Activity activity,User user){
        List<UserStatus> statuses = statusRepository.findByUserID(user.getUserID(),Pageable.ofSize(1)).getContent();
        UserStatus curStatus = statuses.isEmpty()?null:statuses.get(0);
        if(curStatus == null){
            curStatus = new UserStatus();
            curStatus.setUserID(user.getUserID());
            //update the user with current user status
        }
        //update the data of user status in the database
        float carbonEmission = curStatus.getCurCarbonEmission();
        //TODO calculate carbon amount
        /*
        * fake for test*/
        activity.getActivityItem().setCarbonAmount(9);
        //
        curStatus.setCurCarbonEmission(curStatus.getCurCarbonEmission()+activity.getActivityItem().getCarbonAmount());
        Share share = curStatus.getShares().get(activity.getActivityItem().getType());
        share.setAmount(share.getAmount()+activity.getActivityItem().getCarbonAmount());
        //update the percentage
        for(Share s: curStatus.getShares().values()){
            s.setPercentage(s.getAmount()/ curStatus.getCurCarbonEmission());
        }
        //update the status
        statusRepository.save(curStatus);
        user.setCurrentStatus(curStatus);
        updateUser(user);
        //Set the green level of the userStatus

    }
    public UserStatus getUserStatus(User user){
        //TODO further version, we get UserStatus from redis, which could expires
        return statusRepository.findByUserID(user.getUserID(),Pageable.ofSize(1)).getContent().get(0);
    }
    public String generateToken(User user) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getEmail());
        sb.append(user.getUserName());
        sb.append(user.getPassword());

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(sb.toString().getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return myHash;
    }
}
