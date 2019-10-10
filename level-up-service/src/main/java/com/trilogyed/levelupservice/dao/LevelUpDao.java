package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.model.LevelUp;

import java.util.List;

public interface LevelUpDao {

    public LevelUp addLevelUp(LevelUp levelUp);
    public LevelUp getLevelUp(int id);
    public List<LevelUp> getAllLevelUps();
    public List<LevelUp> getAllLevelUpsByCustomerId(int customerId);
    public void updateLevelUp(LevelUp levelUp);
    public void deleteLevelUp(int id);
}
