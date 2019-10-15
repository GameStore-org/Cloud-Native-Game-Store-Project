package com.trilogyed.inventoryservice.dao;

import com.trilogyed.inventoryservice.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoTest {

    @Autowired
    InventoryDao dao;

    @Before
    public void setUp() throws Exception {
        List<Inventory> invList = dao.getAllInventory();
        for (Inventory inv : invList) {
            dao.deleteInventory(inv.getInventoryId());
        }
    }



    @Test
    public void createGetDelete(){
        //create
        Inventory inv = new Inventory();
        inv.setProductId(1);
        inv.setQuantity(20);

        inv = dao.createInventory(inv);

        Inventory inv2 = dao.getInventory(inv.getInventoryId());

        //assert
        assertEquals(inv, inv2);

        //delete
        dao.deleteInventory(inv.getInventoryId());
        inv2 = dao.getInventory(inv.getInventoryId());

        //checking if id has been deleted
        assertNull(inv2);
    }


    @Test
    public void getAllInventory() {
        Inventory inv = new Inventory();
        inv.setProductId(1);
        inv.setQuantity(21);

        inv = dao.createInventory(inv);

        inv = new Inventory();
        inv.setProductId(2);
        inv.setQuantity(34);

        inv = dao.createInventory(inv);

        List<Inventory> listOfInv = dao.getAllInventory();
        assertEquals(listOfInv.size(), 2);
    }

    @Test
    public void updateInventory() {
        Inventory inv = new Inventory ();
        inv.setProductId(23);
        inv.setQuantity(1);

        inv = dao.createInventory(inv);

        inv.setQuantity(23);

        dao.updateInventory(inv);

        Inventory inv2 = dao.getInventory(inv.getInventoryId());

        assertEquals(inv, inv2);
    }
}