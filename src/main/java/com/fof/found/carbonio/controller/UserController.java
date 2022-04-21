package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.User;
import com.fof.found.carbonio.entity.registration.RegistrationResult;
import com.fof.found.carbonio.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserManagementService userManagementService;

    @GetMapping("/carbonMarket/user")
    @ResponseBody
    public User getUserInfo(@RequestParam(name = "email") String emailAddress) {
        return userManagementService.findUserByEmail(emailAddress);
    }

    //
    @PostMapping("/carbonMarket/user/register")
    @ResponseBody
    public RegistrationResult registerUser(@RequestParam(name = "email") String email, @RequestParam(name = "userName") String userName,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name="isCompanyUser",required = false) Boolean isCompanyUser) {
        if (userManagementService.findUserByEmail(email) != null) {
            RegistrationResult result = new RegistrationResult(false, "User already exists");
            return result;
        } else {
            //Save user info into database
            User user = new User(userName,password,email);
            if(isCompanyUser != null && isCompanyUser.equals(true)){
                user.setCompanyUser(true);
            }
            userManagementService.registerUser(user);
            return new RegistrationResult(true,"Registration success!");
        }
    }
}
