package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.Tip;
import com.fof.found.carbonio.service.TipsManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class TipsController {
    @Autowired
    TipsManagementService tipsManagementService;

    @GetMapping("/carbonMarket/tips")
    @ResponseBody
    public List<Tip> getTips(){
        return tipsManagementService.getRandomTips();
    }
}
