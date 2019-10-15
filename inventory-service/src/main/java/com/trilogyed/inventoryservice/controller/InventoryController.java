package com.trilogyed.inventoryservice.controller;


import com.trilogyed.inventoryservice.exception.NotFoundException;
import com.trilogyed.inventoryservice.model.Inventory;
import com.trilogyed.inventoryservice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class InventoryController {

    @Autowired
    ServiceLayer service;


    //creates inventory
    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory createInventory(@RequestBody @Valid Inventory inventory) {
        return service.createInventory(inventory);
    }

    //read inventory id
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventory(@PathVariable int id) {
        Inventory inventory = service.getInvenotry(id);
        if (inventory == null)
            throw new NotFoundException("Could not find inventory id: " + id);
        return inventory;
    }


    //updating the inventory
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable int id, @RequestBody @Valid Inventory inventory) {
        if (inventory.getInventoryId() == 0)
            inventory.setInventoryId(id);
        if (id != inventory.getInventoryId()) {
            throw new IllegalArgumentException("Please enter a valid id.");
        }
        service.updateInventory(inventory);
    }

    // handles requests to delete an inventory by inventory id
    @RequestMapping(value = "/inventory/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable int id) {
        service.deleteInventory(id);
    }

    //get all inventory
    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventory() {
        List<Inventory> invList = service.getAllInventory();
        return invList;
    }

}


