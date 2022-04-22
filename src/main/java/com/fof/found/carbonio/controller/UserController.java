package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.User;
import com.fof.found.carbonio.entity.UserStatus;
import com.fof.found.carbonio.entity.activity.DailyPlan;
import com.fof.found.carbonio.entity.activity.Goal;
import com.fof.found.carbonio.entity.activity.PlanItem;
import com.fof.found.carbonio.entity.registration.RegistrationResult;
import com.fof.found.carbonio.service.DailyPlanService;
import com.fof.found.carbonio.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    UserManagementService userManagementService;
    @Autowired
    DailyPlanService dailyPlanService;



    //find the user by its email, which is the unique identifier
    @GetMapping("/carbonMarket/user")
    @ResponseBody
    public User getUserInfo(@RequestParam(name = "email")String email) {
        return userManagementService.findUserByEmail(email);
    }
    //find the user by token
    @GetMapping("/carbonMarket/userToken")
    @ResponseBody
    public User getUserByToken(@RequestParam(name = "token")String token) {
        return userManagementService.findUserByToken(token);
    }

    //register the user
    @PostMapping("/carbonMarket/user/register")
    @ResponseBody
    public RegistrationResult registerUser(@RequestBody User user) throws NoSuchAlgorithmException {
        if (userManagementService.findUserByEmail(user.getEmail()) != null) {
            RegistrationResult result = new RegistrationResult(false, "User already exists","");
            return result;
        } else {
            String token = userManagementService.generateToken(user);
            //Save user info into database
            user.setToken(token);
            user.setUserID(UUID.randomUUID());
            userManagementService.registerUser(user);

            return new RegistrationResult(true,"Registration success!",token);
        }
    }
    @PostMapping("/carbonMarket/user/addActivity")
    public Activity addActivity(@RequestBody Activity activity,@RequestParam(name="token") String token){
        //create activity
        return userManagementService.createUserActivity(activity,token);
    }
    @GetMapping("/carbonMarket/user/currentStatus")
    @ResponseBody
    public UserStatus getUserCurrentStatus(@RequestParam(name = "token")String token){
        return userManagementService.getUserStatus(userManagementService.findUserByToken(token));
    }
    //TODO
    @PostMapping("/carbonMarket/user/setGoal")
    @ResponseBody
    public float setGoal(@RequestParam(name="goal") float goal,@RequestParam(name = "token")String token){
        User user = userManagementService.findUserByToken(token);
        userManagementService.setGoalForUser(user,goal);
        return goal;
    }
    //TODO
    @PostMapping("/carbonMarket/user/setDailyPlan")
    @ResponseBody
    public float setDailyPlan(@RequestBody List<PlanItem> items){
        DailyPlan plan = new DailyPlan(items);
        return dailyPlanService.calculateDailyEmission(plan);
    }
    @GetMapping("/carbonMarket/user/currentActivities")
    @ResponseBody
    public List<Activity> getTodayActivity(@RequestParam(name="token")String token){
        return userManagementService.findActivityForToday(token);
    }
    @GetMapping("/carbonMarket/user/historicalActivities")
    @ResponseBody
    public List<Activity> getHistoricalActivity(@RequestParam(name="token")String token){
        return userManagementService.findAllActivities(token);
    }
}
