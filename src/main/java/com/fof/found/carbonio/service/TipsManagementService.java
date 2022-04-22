package com.fof.found.carbonio.service;

import com.fof.found.carbonio.entity.Tip;
import com.fof.found.carbonio.repository.TipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TipsManagementService {
    @Autowired
    TipsRepository tipsRepository;

    public List<Tip> getRandomTips(){
        //TODO
        return null;
    }
    public Tip getOneTip(){
        //TODO need further improve
        List<Tip> tips = new ArrayList<>();
        Iterable<Tip> tipIter = tipsRepository.findAll();
        tipIter.forEach(tips::add);
        Random rd = new Random();
        return tips.get(rd.nextInt(tips.size()));
    }
    public void saveTips(List<Tip> tips){
        tipsRepository.saveAll(tips);
    }
}
