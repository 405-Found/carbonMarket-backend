package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.Tip;
import com.fof.found.carbonio.service.TipsManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/carbonMarket/tip")
    @ResponseBody
    public Tip getOneTip(){
        return tipsManagementService.getOneTip();
    }
    @PostMapping("/carbonMarket/saveTips")
    public void saveTips(@RequestBody List<Tip> tips){
        tipsManagementService.saveTips(tips);
    }
}
