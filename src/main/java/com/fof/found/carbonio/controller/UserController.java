package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.User;
import com.fof.found.carbonio.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserManagementService userManagementService;

    @GetMapping("/carbonMarket/user")
    @ResponseBody
    public User getUserInfo(@RequestParam(name = "email")String emailAddress){
        return userManagementService.findUserByEmail(emailAddress);
    }
}
