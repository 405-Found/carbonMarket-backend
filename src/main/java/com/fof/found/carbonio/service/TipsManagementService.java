package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.Tip;
import com.fof.found.carbonio.repository.TipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipsManagementService {
    @Autowired
    TipsRepository tipsRepository;

    public List<Tip> getRandomTips(){
        //TODO
        return null;
    }
}
