package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Inventory;
import com.trilogyed.adminapi.model.Product;
import com.trilogyed.adminapi.util.feign.InventoryClient;
import com.trilogyed.adminapi.util.feign.ProductClient;
import com.trilogyed.adminapi.viewModels.ItemViewModel;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class ProductServiceLayerTest {

    private ProductClient productClient;
    private InventoryClient inventoryClient;
    ProductService sl;

    @Test
    public void addGetUpdateDeleteProduct() {
        productClient = mock(ProductClient.class);
        inventoryClient = mock(InventoryClient.class);

        Product product = new Product();
        product.setProductName("Anca");
        product.setProductDescription("beautiful");
        product.setListPrice(new BigDecimal("21.21"));
        product.setUnitCost(new BigDecimal("10.10"));

        Product product1 = new Product();
        product1.setProductId(100);
        product1.setProductName("Anca");
        product1.setProductDescription("beautiful");
        product1.setListPrice(new BigDecimal("21.21"));
        product1.setUnitCost(new BigDecimal("10.10"));

        Product product2 = new Product();
        product2.setProductId(200);
        product2.setProductName("Anca Cani");
        product2.setProductDescription("beautiful");
        product2.setListPrice(new BigDecimal("21.21"));
        product2.setUnitCost(new BigDecimal("10.10"));

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        //updated product
        Product product3 = new Product();
        product3.setProductId(300);
        product3.setProductName("Anca");
        product3.setProductDescription("beautiful");
        product3.setListPrice(new BigDecimal("21.21"));
        product3.setUnitCost(new BigDecimal("10.10"));


        //deleted product
        Product product4 = new Product();
        product4.setProductId(400);
        product4.setProductName("Anca");
        product4.setProductDescription("beautiful");
        product4.setListPrice(new BigDecimal("21.21"));
        product4.setUnitCost(new BigDecimal("10.10"));


        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setQuantity(250);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1100);
        inventory1.setProductId(100);
        inventory1.setQuantity(250);

        Inventory inventory2 = new Inventory();
        inventory2.setInventoryId(2200);
        inventory2.setProductId(200);
        inventory2.setQuantity(300);

        List<Inventory> invList = new ArrayList<>();
        invList.add(inventory1);
        invList.add(inventory2);

        List<Inventory> invListByProductId0 = new ArrayList<>();
        invListByProductId0.add(inventory1);

        List<Inventory> invListByProductId = new ArrayList<>();
        invListByProductId.add(inventory2);

        //updated Inventory
        Inventory inventory3 = new Inventory();
        inventory3.setInventoryId(3300);
        inventory3.setProductId(300);
        inventory3.setQuantity(250);

        List<Inventory> invListByProductId1 = new ArrayList<>();
        invListByProductId1.add(inventory3);

        //deleted inventory
        Inventory inventory4 = new Inventory();
        inventory4.setInventoryId(4400);
        inventory4.setProductId(400);
        inventory4.setQuantity(250);

        doReturn(product1).when(productClient).createProduct(product);
        doReturn(product1).when(productClient).getProduct(100);
        doReturn(productList).when(productClient).getAllProducts();


        doReturn(product2).when(productClient).getProduct(200);
        doNothing().when(productClient).updateProduct(product3.getProductId(),product3);
        doReturn(product3).when(productClient).getProduct(300);


        doNothing().when(productClient).deleteProduct(400);
        doReturn(null).when(productClient).getProduct(400);

        doReturn(inventory1).when(inventoryClient).createInventory(inventory);
        doReturn(inventory1).when(inventoryClient).getInventory(1100);
        doReturn(invListByProductId0).when(inventoryClient).getInventoryByProductId(100);
        doReturn(invList).when(inventoryClient).getAllInventory();


        doReturn(invListByProductId).when(inventoryClient).getInventoryByProductId(200);
        doReturn(inventory2).when(inventoryClient).getInventory(2200);
        doNothing().when(inventoryClient).updateInventory(inventory3.getInventoryId(),inventory3);
        doReturn(inventory3).when(inventoryClient).getInventory(3300);
        doReturn(invListByProductId1).when(inventoryClient).getInventoryByProductId(300);


        doNothing().when(inventoryClient).deleteInventory(4400);
        doReturn(null).when(inventoryClient).getInventoryByProductId(400);
        doReturn(null).when(inventoryClient).getInventory(4400);


        sl = new ProductService(productClient, inventoryClient);

        //add inventory
        ItemViewModel ivm = new ItemViewModel();
        ivm.setProductName(product.getProductName());
        ivm.setProductDescription(product.getProductDescription());
        ivm.setListPrice(product.getListPrice());
        ivm.setUnitCost(product.getUnitCost());
        ivm.setQuantityInInventory(inventory.getQuantity());

        ivm = sl.createItem(ivm);
        ItemViewModel ivm1 = sl.findItemByProductId(100);
        assertEquals(ivm, ivm1);

        // create inventory
        ItemViewModel itemViewModel = sl.createInventory(inventory);
        ItemViewModel itemViewModel1 = sl.findItemByProductId(itemViewModel.getProductId());
        assertEquals(itemViewModel, itemViewModel1);

        // all Items
        List<ItemViewModel> itemViewModelList = sl.findAllItems();
        assertEquals(itemViewModelList.size(), 2);

        // Item By Inventory Id
        ItemViewModel itemViewModel2 = sl.findItemByInventoryId(1100);
        ItemViewModel itemViewModel3 = sl.findItemByProductId(100);
        assertEquals(itemViewModel2, itemViewModel3);

        // update Item Or Inventory
        ItemViewModel itemViewModel4 = sl.findItemByProductId(300);
        sl.updateInventory(itemViewModel4);
        ItemViewModel itemViewModel5 = sl.findItemByProductId(itemViewModel4.getProductId());
        assertEquals(itemViewModel5, itemViewModel4);
        assertEquals(itemViewModel5.getQuantityInInventory(), itemViewModel4.getQuantityInInventory());

        // delete Item
        sl.deleteItem(400);
        assertNull(sl.findItemByProductId(400));
    }
}
