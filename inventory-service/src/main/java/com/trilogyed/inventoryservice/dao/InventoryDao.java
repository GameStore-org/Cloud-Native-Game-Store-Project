package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;

import java.util.List;

public interface InventoryDao {


    Inventory createInventory (Inventory inventory);

    Inventory getInventory (int inventoryId);

    List<Inventory> getAllInventory();

    void updateInventory(Inventory inventory);

    void deleteInventory(int inventoryId);

}
