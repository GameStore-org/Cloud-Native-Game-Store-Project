package com.trilogyed.adminapi.util.feign;

import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.model.LevelUp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "level-up-service")
public interface LevelUpClient {

    @RequestMapping(value = "/levelups", method = RequestMethod.POST)
    LevelUp createLevelUp(@RequestBody @Valid LevelUp levelUp);

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.GET)
    LevelUp getLevelUp(@PathVariable int id);

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.PUT)
    void updateLevelUp(@PathVariable int id, @RequestBody @Valid LevelUp levelUp);

    @RequestMapping(value = "/levelups/{id}", method = RequestMethod.DELETE)
    void deleteLevelUp(@PathVariable int id);

    @RequestMapping(value = "/levelups", method = RequestMethod.GET)
    List<LevelUp> getAllLevelUps();

    @RequestMapping(value = "/levelups/customer/{id}", method = RequestMethod.GET)
    Integer getLevelUpPointsByCustomerId(@PathVariable int id);


}
