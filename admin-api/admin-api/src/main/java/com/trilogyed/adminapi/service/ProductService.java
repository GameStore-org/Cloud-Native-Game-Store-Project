package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Product;
import com.trilogyed.adminapi.util.feign.ProductClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ProductService {

    private ProductClient productClient;

    public ProductService (ProductClient productClient){
        this.productClient=productClient;
    }


    @Transactional
    public Product saveProduct(Product product){

        product=productClient.createProduct(product);

        return product;
    }

    public Product findProductById(int productId){

        Product product= productClient.getProduct(productId);

        return product;
    }
//
//    public List<Product> getProductByProductName(String productName){
//
//        List<Product> productByName=productClient.getProductByName(productName);
//
//        return productByName;
//    }


    public List<Product> getAllProducts(){
        List<Product> products= productClient.getAllProducts();

        return products;
    }

    public void updateProduct(int productId, Product product){

        productClient.updateProduct(productId, product);
    }

    public void deleteProduct(int productId){

        productClient.deleteProduct(productId);
    }

}
