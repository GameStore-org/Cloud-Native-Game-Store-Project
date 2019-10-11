package com.trilogyed.levelupservice.controller;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
//@RefreshScope
public class LevelUpController {

    @Autowired
    LevelUpDao dao;

    public LevelUpController(LevelUpDao dao){
        this.dao = dao;
    }

    @RequestMapping(value = "/levelups", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp) {

        return dao.addLevelUp(levelUp);
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public LevelUp getLevelUp(@PathVariable int id) {
        if (id < 1) {
            throw new IllegalArgumentException("Id must be greater than 0.");
        }
        LevelUp levelUp = dao.getLevelUp(id);

        return levelUp;
    }

    @RequestMapping(value = "/levelups", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<LevelUp> getAllLevelUps() {

        List<LevelUp> list = dao.getAllLevelUps();

        return list;
    }

    @RequestMapping(value = "/levelups/customerId/{customerId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<LevelUp> getAllLevelUpsByCustomerId(@PathVariable int customerId){

        List<LevelUp> list = dao.getAllLevelUpsByCustomerId(customerId);

        return list;
    }

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLevelUp(@RequestBody LevelUp levelUp, @PathVariable int id) {

        dao.updateLevelUp(levelUp);
    }


    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLevelUp(@PathVariable int id) {

        dao.deleteLevelUp(id);
    }
}
