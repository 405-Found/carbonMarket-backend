package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.Activity;
import com.fof.found.carbonio.entity.User;
import com.fof.found.carbonio.entity.UserStatus;
import com.fof.found.carbonio.entity.registration.RegistrationResult;
import com.fof.found.carbonio.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserManagementService userManagementService;
    @Autowired


    //find the user by its email, which is the unique identifier
    @GetMapping("/carbonMarket/user")
    @ResponseBody
    public User getUserInfo(@RequestParam(name = "email")String email) {
        return userManagementService.findUserByEmail(email);
    }

    //register the user
    @PostMapping("/carbonMarket/user/register")
    @ResponseBody
    public RegistrationResult registerUser(@RequestBody User user) {
        if (userManagementService.findUserByEmail(user.getEmail()) != null) {
            RegistrationResult result = new RegistrationResult(false, "User already exists");
            return result;
        } else {
            //Save user info into database
            userManagementService.registerUser(user);
            return new RegistrationResult(true,"Registration success!");
        }
    }
    @PostMapping("/carbonMarket/user/addActivity")
    public void addActivity(@RequestBody Activity activity,@RequestParam(name="email") String email){
        //create activity
        userManagementService.createUserActivity(activity,email);
    }
    @GetMapping("/carbonMarket/user/currentStatus")
    @ResponseBody
    public UserStatus getUserCurrentStatus(@RequestParam(name = "email")String email){
        return userManagementService.getUserStatus(userManagementService.findUserByEmail(email));
    }
}
