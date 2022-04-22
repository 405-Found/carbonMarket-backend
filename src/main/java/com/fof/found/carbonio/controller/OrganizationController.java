package com.fof.found.carbonio.controller;

import com.fof.found.carbonio.entity.CharityOrganization;
import com.fof.found.carbonio.service.CharityOrganizationManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {
    @Autowired
    CharityOrganizationManagementService charityOrganizationManagementService;

    @GetMapping("/carbonMarket/charityOrgs")
    @ResponseBody
    public List<CharityOrganization> getCharityOrgs(@RequestParam(name = "keyword",required = false) String keyword,
                                                    @RequestParam(name = "sort") boolean ifSort){
        List<CharityOrganization> charityOrganizations;
        if(StringUtils.isEmpty(keyword)){
            charityOrganizations = charityOrganizationManagementService.findAll();
        }else{
            charityOrganizations = charityOrganizationManagementService.findByKeyword(keyword);
        }
        if(ifSort){
            charityOrganizations.sort((x1,x2)->(x1.getMoneyPerKilo()-x2.getMoneyPerKilo()>0?1:-1));
        }
        return  charityOrganizations;

    }
    @PostMapping("/carbonMarket/addOrgs")
    public void addOrgs(@RequestBody List<CharityOrganization> orgs){
        charityOrganizationManagementService.addOrgs(orgs);
    }
}
