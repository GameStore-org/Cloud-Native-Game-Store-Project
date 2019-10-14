package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LevelUpService {

    LevelUpDao levelUpDao;

    @Autowired
    public LevelUpService(LevelUpDao levelUpDao) {

        this.levelUpDao = levelUpDao;
    }

    public LevelUp addLevelUp(LevelUp levelUp) {

        return levelUpDao.addLevelUp(levelUp);
    }

    public LevelUp getLevelUp(int id) {

        return levelUpDao.getLevelUp(id);
    }

    public void updateLevelUp(LevelUp levelUp) {

        levelUpDao.updateLevelUp(levelUp);
    }

    public void deleteLevelUp(int id) {

        levelUpDao.deleteLevelUp(id);
    }

    public List<LevelUp> getAllLevelUps() {

        return levelUpDao.getAllLevelUps();
    }

    public LevelUp getLevelUpByCustomerId(int customerId) {

        return levelUpDao.getLevelUpByCustomerId(customerId);
    }

}
