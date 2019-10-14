package com.trilogyed.adminapi.controller;

import com.trilogyed.adminapi.model.Customer;
import com.trilogyed.adminapi.service.CustomerService;
import com.trilogyed.adminapi.viewModels.CustomerViewModel;
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
@CacheConfig(cacheNames = {"customers"})
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @CachePut(key = "#result.getCustomerId()")
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerViewModel addCustomer(@RequestBody @Valid Customer customer) {
        return customerService.createCustomer(customer);
    }

    @Cacheable
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CustomerViewModel getCustomer(@PathVariable int id) {
        CustomerViewModel customer = customerService.getCustomer(id);
        return customer;
    }

    @CacheEvict(key = "#customer.getCustomerId()")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id, @RequestBody @Valid CustomerViewModel customer) {
        if (customer.getCustomerId() == 0)
            customer.setCustomerId(id);
        if (id != customer.getCustomerId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the Customer object");
        }
        customerService.updateCustomer(customer);
    }

    @CacheEvict
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

}

