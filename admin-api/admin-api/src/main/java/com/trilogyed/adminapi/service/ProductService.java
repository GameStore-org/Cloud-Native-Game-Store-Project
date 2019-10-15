package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Inventory;
import com.trilogyed.adminapi.model.Product;
import com.trilogyed.adminapi.util.feign.InventoryClient;
import com.trilogyed.adminapi.util.feign.ProductClient;
import com.trilogyed.adminapi.viewModels.ItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductService {

    private ProductClient productClient;
    private InventoryClient inventoryClient;

    @Autowired

    public ProductService(ProductClient productClient, InventoryClient inventoryClient) {
        this.productClient = productClient;
        this.inventoryClient = inventoryClient;
    }

    @Transactional
    public ItemViewModel createItem(ItemViewModel ivm){

        //create product
        Product product = new Product();
        product.setProductName(ivm.getProductName());
        product.setProductDescription(ivm.getProductDescription());
        product.setListPrice(ivm.getListPrice());
        product.setUnitCost(ivm.getUnitCost());
        product = productClient.createProduct(product);

        // create inventory
        Inventory inventory = new Inventory();
        inventory.setProductId(ivm.getProductId());
        if (ivm.getQuantityInInventory()==null) {
            inventory.setQuantity(0);
        }
            else
                inventory.setQuantity(ivm.getQuantityInInventory());
        inventory = inventoryClient.createInventory(inventory);

        return buildItemViewModel(product);
    }

    @Transactional
    public ItemViewModel createInventory(Inventory inventory){

        inventory = inventoryClient.createInventory(inventory);
        Product product = productClient.getProduct(inventory.getProductId());

        return buildItemViewModel(product);
    }

    public ItemViewModel findItemByProductId(Integer productId){

        Product product = productClient.getProduct(productId);
        if(product==null) return null;
        return buildItemViewModel(product);
    }

    public List<ItemViewModel> findAllItems(){

        List<Product> products = productClient.getAllProducts();
        List<ItemViewModel> ivmList = new ArrayList<>();
        products.stream().forEach(product ->{
            ItemViewModel ivm = buildItemViewModel(product);
            ivmList.add(ivm);
        });
        return ivmList;
    }

    public ItemViewModel findItemByInventoryId(Integer inventoryId){

        Inventory inventory = inventoryClient.getInventory(inventoryId);
        Product product = productClient.getProduct(inventory.getProductId());
        if (product==null) return null;
        return buildItemViewModel(product);
    }

    public void updateItemOrInventory(ItemViewModel ivm){

        Product product = productClient.getProduct(ivm.getProductId());
        product.setProductName(ivm.getProductName());
        product.setProductDescription(ivm.getProductDescription());
        product.setListPrice(ivm.getListPrice());
        product.setUnitCost(ivm.getUnitCost());
        productClient.updateProduct(product.getProductId(),product);
        updateInventory(ivm);
    }

    public void updateInventory(ItemViewModel ivm){

        if (ivm.getInventoryId()!=null) {
            Inventory inventory = inventoryClient.getInventory(ivm.getInventoryId());
            inventory.setQuantity(ivm.getQuantityInInventory());
            inventoryClient.updateInventory(inventory.getInventoryId(),inventory);
        }
    }

    public void deleteItem(Integer productId){

        List<Inventory> invList = inventoryClient.getInventoryByProductId(productId);
        if(invList!=null){

            invList.stream().forEach(inventory -> {
                inventoryClient.deleteInventory(inventory.getInventoryId());
            });
        }
        productClient.deleteProduct(productId);
    }

    //helper method
    public ItemViewModel buildItemViewModel(Product product)
    {
        if (product==null) return null;
        ItemViewModel ivm = new ItemViewModel();
        ivm.setProductId(product.getProductId());
        ivm.setProductName(product.getProductName());
        ivm.setProductDescription(product.getProductDescription());
        ivm.setListPrice(product.getListPrice());
        ivm.setUnitCost(product.getUnitCost());
        List<Inventory> inventoryList = inventoryClient.getInventoryByProductId(product.getProductId());
        Inventory inventory = deleteExtraInventories(inventoryList);
        ivm.setInventoryId(inventory.getInventoryId());
        ivm.setQuantityInInventory(inventory.getQuantity());
        return ivm;
    }

    //Delete extra Inventories
    public Inventory deleteExtraInventories(List<Inventory> inventoryList){

        Comparator<Inventory> maxId = Comparator.comparing(Inventory::getInventoryId);
        Inventory maxIdInv = inventoryList.stream().max(maxId).get();
        for (Inventory inventory: inventoryList){

            if(inventory.getInventoryId()!=maxIdInv.getInventoryId()){

                maxIdInv.setQuantity(maxIdInv.getQuantity()+inventory.getQuantity());
                inventoryClient.deleteInventory(inventory.getInventoryId());
            }
        }
        inventoryClient.updateInventory(maxIdInv.getInventoryId(),maxIdInv);
        return maxIdInv;
    }

//
//    @Transactional
//    public Product saveProduct(Product product){
//
//        product=productClient.createProduct(product);
//
//        return product;
//    }
//
//    public Product findProductById(int productId){
//
//        Product product= productClient.getProduct(productId);
//
//        return product;
//    }
//
//    public List<Product> getProductByProductName(String productName){
//
//        List<Product> productByName=productClient.getProductByName(productName);
//
//        return productByName;
//    }

//
//    public List<Product> getAllProducts(){
//        List<Product> products= productClient.getAllProducts();
//
//        return products;
//    }
//
//    public void updateProduct(int productId, Product product){
//
//        productClient.updateProduct(productId, product);
//    }
//
//    public void deleteProduct(int productId){
//
//        productClient.deleteProduct(productId);
//    }

}
