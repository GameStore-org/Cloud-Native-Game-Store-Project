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

        Product savedProduct = new Product();
        savedProduct.setProductId(100);
        savedProduct.setProductName("Anca");
        savedProduct.setProductDescription("beautiful");
        savedProduct.setListPrice(new BigDecimal("21.21"));
        savedProduct.setUnitCost(new BigDecimal("10.10"));

        Product anotherProduct = new Product();
        anotherProduct.setProductId(200);
        anotherProduct.setProductName("Anca Cani");
        anotherProduct.setProductDescription("beautiful");
        anotherProduct.setListPrice(new BigDecimal("21.21"));
        anotherProduct.setUnitCost(new BigDecimal("10.10"));

        List<Product> productList = new ArrayList<>();
        productList.add(savedProduct);
        productList.add(anotherProduct);

        Product updateProduct = new Product();
        updateProduct.setProductId(300);
        updateProduct.setProductName("Anca");
        updateProduct.setProductDescription("beautiful");
        updateProduct.setListPrice(new BigDecimal("21.21"));
        updateProduct.setUnitCost(new BigDecimal("10.10"));

        Product deleteProduct = new Product();
        deleteProduct.setProductId(400);
        deleteProduct.setProductName("Anca");
        deleteProduct.setProductDescription("beautiful");
        deleteProduct.setListPrice(new BigDecimal("21.21"));
        deleteProduct.setUnitCost(new BigDecimal("10.10"));

        Inventory inventory = new Inventory();
        inventory.setProductId(100);
        inventory.setQuantity(250);

        Inventory savedInventory = new Inventory();
        savedInventory.setInventoryId(1100);
        savedInventory.setProductId(100);
        savedInventory.setQuantity(250);

        Inventory anotherInventory = new Inventory();
        anotherInventory.setInventoryId(2200);
        anotherInventory.setProductId(200);
        anotherInventory.setQuantity(300);

        List<Inventory> invList = new ArrayList<>();
        invList.add(savedInventory);
        invList.add(anotherInventory);

        List<Inventory> invListByProductId0 = new ArrayList<>();
        invListByProductId0.add(savedInventory);

        List<Inventory> invListByProductId = new ArrayList<>();
        invListByProductId.add(anotherInventory);

        Inventory updateInventory = new Inventory();
        updateInventory.setInventoryId(3300);
        updateInventory.setProductId(300);
        updateInventory.setQuantity(250);

        List<Inventory> invListByProductId1 = new ArrayList<>();
        invListByProductId1.add(updateInventory);

        Inventory deleteInventory = new Inventory();
        deleteInventory.setInventoryId(4400);
        deleteInventory.setProductId(400);
        deleteInventory.setQuantity(250);

        doReturn(savedProduct).when(productClient).createProduct(product);
        doReturn(savedProduct).when(productClient).getProduct(100);
        doReturn(productList).when(productClient).getAllProducts();
        doReturn(anotherProduct).when(productClient).getProduct(200);
        doNothing().when(productClient).updateProduct(updateProduct.getProductId(),updateProduct);
        doReturn(updateProduct).when(productClient).getProduct(300);
        doNothing().when(productClient).deleteProduct(400);
        doReturn(null).when(productClient).getProduct(400);

        doReturn(savedInventory).when(inventoryClient).createInventory(inventory);
        doReturn(savedInventory).when(inventoryClient).getInventory(1100);
        doReturn(invListByProductId0).when(inventoryClient).getInventoryByProductId(100);
        doReturn(invList).when(inventoryClient).getAllInventory();
        doReturn(invListByProductId).when(inventoryClient).getInventoryByProductId(200);
        doReturn(anotherInventory).when(inventoryClient).getInventory(2200);
        doNothing().when(inventoryClient).updateInventory(updateInventory.getInventoryId(),updateInventory);
        doReturn(updateInventory).when(inventoryClient).getInventory(3300);
        doReturn(invListByProductId1).when(inventoryClient).getInventoryByProductId(300);
        doNothing().when(inventoryClient).deleteInventory(4400);
        doReturn(null).when(inventoryClient).getInventoryByProductId(400);
        doReturn(null).when(inventoryClient).getInventory(4400);

        sl = new ProductService(productClient, inventoryClient);

        //add inventory */
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
        ItemViewModel ivmCreate = sl.createInventory(inventory);
        ItemViewModel ivmGetBack = sl.findItemByProductId(ivmCreate.getProductId());
        assertEquals(ivmCreate, ivmGetBack);

        // all Items
        List<ItemViewModel> ivmListAllProducts = sl.findAllItems();
        assertEquals(ivmListAllProducts.size(), 2);

        // Item By Inventory Id
        ItemViewModel ivmFindItem = sl.findItemByInventoryId(1100);
        ItemViewModel ivmFindByProductId = sl.findItemByProductId(100);
        assertEquals(ivmFindItem, ivmFindByProductId);

        // update Item Or Inventory
        ItemViewModel updateIvm = sl.findItemByProductId(300);
        sl.updateInventory(updateIvm);
        ItemViewModel updatedIvm = sl.findItemByProductId(updateIvm.getProductId());
        assertEquals(updatedIvm, updateIvm);
        assertEquals(updatedIvm.getQuantityInInventory(), updateIvm.getQuantityInInventory());

        // delete Item
        sl.deleteItem(400);
        assertNull(sl.findItemByProductId(400));
    }
}
