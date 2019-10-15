package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.LevelUp;
import com.trilogyed.adminapi.util.feign.LevelUpClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class LevelUpService {


    private LevelUpClient levelUpClient;

    public LevelUpService(LevelUpClient levelUpClient){
        this.levelUpClient=levelUpClient;
    }

    @Transactional
    public LevelUp saveLevel(LevelUp levelUp){

        levelUp=levelUpClient.createLevelUp(levelUp);

        return levelUp;
    }

    public LevelUp findLevelById(int levelUpId){

        LevelUp levelUp= levelUpClient.getLevelUp(levelUpId);

        return levelUp;
    }

    public LevelUp getLevelUpByCustomerId(int customerId){

        List<LevelUp> levelByCustomerId=levelUpClient.getAllLevelUpsByCustomerId(customerId);

        return (LevelUp) levelByCustomerId;
    }


    public List<LevelUp> getAllLevels(){
        List<LevelUp> levels= levelUpClient.getAllLevelUps();

        return levels;
    }

    public void updateLevel(LevelUp levelUp, int levelUpId){

        levelUpClient.updateLevelUp(levelUp, levelUpId);
    }

    public void deleteLevel(int levelUpId){

        levelUpClient.deleteLevelUp(levelUpId);
    }
}
