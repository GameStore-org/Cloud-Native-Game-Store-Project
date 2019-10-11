package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Inventory;
import com.trilogyed.adminapi.util.feign.InventoryClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class InventoryService {

    private InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient){
        this.inventoryClient=inventoryClient;
    }

    @Transactional
    public Inventory saveInventory(Inventory inventory){

        inventory=inventoryClient.createInventory(inventory);

        return inventory;
    }

    public Inventory findInventoryById(int inventoryId){

        Inventory inventory= inventoryClient.getInventory(inventoryId);

        return inventory;
    }

//    public List<Inventory> getCustomerByProductId(int productId){
//
//        List<Inventory> inventoryByProductId=inventoryClient.getInventoryByProductId(productId);
//
//        return inventoryByProductId;
//    }


    public List<Inventory> getAllInventories(){
        List<Inventory> inventories= inventoryClient.getAllInventory();

        return inventories;
    }

    public void updateInventories(int inventoryId, Inventory inventory){

        inventoryClient.updateInventory(inventoryId, inventory);
    }

    public void deleteInventory(int inventoryId){

        inventoryClient.deleteInventory(inventoryId);
    }



}
