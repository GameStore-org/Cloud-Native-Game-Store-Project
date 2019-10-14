package com.trilogyed.levelupservice.controller;

import com.trilogyed.levelupservice.service.LevelUpService;
import com.trilogyed.levelupservice.exception.NotFoundException;
import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class LevelUpController {

    /**
    LevelUp methods are not cached because values change frequently
     */

    @Autowired
    LevelUpService levelUpService;

    @RequestMapping(value = "/levelups", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LevelUp addLevelUp(@RequestBody @Valid LevelUp levelUp) {
        return levelUpService.addLevelUp(levelUp);
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable int id) {
        LevelUp levelUp = levelUpService.getLevelUp(id);
        if (levelUp == null)
            throw new NotFoundException("LevelUp could not be retrieved for id " + id);
        return levelUp;
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLevelUp(@PathVariable int id, @RequestBody @Valid LevelUp levelUp) {
        if (levelUp.getLevelUpId() == 0)
            levelUp.setLevelUpId(id);
        if (id != levelUp.getLevelUpId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the LevelUp object");
        }
        levelUpService.updateLevelUp(levelUp);
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int id) {
        levelUpService.deleteLevelUp(id);
    }


    @RequestMapping(value = "/levelups", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {
        List<LevelUp> levelUps = levelUpService.getAllLevelUps();
        return levelUps;
    }

    /**
    get levelup by customerId
     */
    @RequestMapping(value = "/levelups/customerId/{customerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LevelUp getLevelUpByCustomerId(@PathVariable("customerId") int customerId) {
        LevelUp levelUp = levelUpService.getLevelUpByCustomerId(customerId);
        if (levelUp == null) {
            throw new NotFoundException("Level up could not be retrieved for this customer.");
        }
        return levelUp;
    }

}

