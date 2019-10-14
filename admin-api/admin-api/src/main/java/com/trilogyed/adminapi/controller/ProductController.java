package com.trilogyed.adminapi.controller;

import com.trilogyed.adminapi.model.Product;
import com.trilogyed.adminapi.service.ProductService;
import com.trilogyed.adminapi.viewModels.ItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"products"})
public class ProductController {

    @Autowired
    ProductService productService;


    public ProductController() {
    }

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @CachePut(key = "#result.getProductId()")
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemViewModel createProduct(@RequestBody @Valid ItemViewModel ivm){
        return productService.createItem(ivm);
    }

    @Cacheable
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ItemViewModel findProductByProductId(@PathVariable(name = "productId") Integer productId){
        ItemViewModel product = productService.findItemByProductId(productId);
        return product;
    }

    @CacheEvict(key = "#product.getProductId()")
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody @Valid ItemViewModel ivm){
        if (ivm.getProductId() == 0)
            ivm.setProductId(ivm.getProductId());
        if (ivm.getProductId() != ivm.getProductId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the Product object");
        }
        productService.updateItemOrInventory(ivm);
    }

    @CacheEvict
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(name = "productId") Integer productId){

        productService.deleteItem(productId);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ItemViewModel> getAllProducts() {

        return productService.findAllItems();
    }

}
