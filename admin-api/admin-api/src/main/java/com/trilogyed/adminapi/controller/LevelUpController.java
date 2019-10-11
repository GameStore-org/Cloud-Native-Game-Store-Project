package com.trilogyed.adminapi.controller;

import com.trilogyed.adminapi.model.LevelUp;
import com.trilogyed.adminapi.service.LevelUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class LevelUpController {

    @Autowired
    LevelUpService levelUpService;

    @RequestMapping(value = "/levelups", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp addLevelUp(@RequestBody @Valid LevelUp levelUp) {
        return levelUpService.saveLevel(levelUp);
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable int id) {
        return levelUpService.findLevelById(id);
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable int id, @RequestBody @Valid LevelUp levelUp) {
        if (levelUp.getLevelUpId() == 0)
            levelUp.setLevelUpId(id);
        if (id != levelUp.getLevelUpId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the LevelUp object");
        }
        levelUpService.updateLevel(id, levelUp);
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int id) {
        levelUpService.deleteLevel(id);
    }

    @RequestMapping(value = "/levelups", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {
        List<LevelUp> levelUps = levelUpService.getAllLevels();
        return levelUps;
    }

//    @RequestMapping(value = "/levelups/customer/{id}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public Integer getLevelUpPointsByCustomerId(@PathVariable int id) {
//        return levelUpService.findLevelByCustomerId(id);
//    }

}
