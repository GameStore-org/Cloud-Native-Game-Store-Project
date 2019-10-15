package com.trilogyed.inventoryservice.service;

import com.trilogyed.inventoryservice.dao.InventoryDao;
import com.trilogyed.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceLayer {
    InventoryDao dao;

    @Autowired
    public ServiceLayer(InventoryDao dao) {
        this.dao = dao;
    }

    public Inventory createInventory(Inventory inventory) {
        return dao.createInventory(inventory);
    }

    public Inventory getInvenotry(int inventoryId) {
        return dao.getInventory(inventoryId);
    }

    public void updateInventory(Inventory inventory) {
        dao.updateInventory(inventory);
    }

    public void deleteInventory(int inventoryId) {
        dao.deleteInventory(inventoryId);
    }

    public List<Inventory> getAllInventory() {
        return dao.getAllInventory();
    }

}
