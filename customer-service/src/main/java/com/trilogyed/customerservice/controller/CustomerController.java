package com.trilogyed.customerservice.controller;

import com.trilogyed.customerservice.exception.NotFoundException;
import com.trilogyed.customerservice.model.Customer;
import com.trilogyed.customerservice.service.ServiceLayer;
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
    ServiceLayer service;


    //creating a customer then caching the customer
    @CachePut(key = "#result.getCustomerId()")
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
        return service.createCustomer(customer);
    }

    //retrieving a customer; cacheable
    @Cacheable
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id) {
        Customer customer = service.getCustomer(id);
        if (customer == null)
            throw new NotFoundException("Customer could not be retrieved for id " + id);
        return customer;
    }

    //updating a customer by id; not caching
    @CacheEvict(key = "#customer.getCustomerId()")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String updateCustomer(@PathVariable int id, @RequestBody @Valid Customer customer) {
        if (customer.getCustomerId() == 0)
            customer.setCustomerId(id);
        if (id != customer.getCustomerId()) {
            throw new IllegalArgumentException("Please enter a valid id.");
        }
        service.updateCustomer(customer);
        return "Customer ID has been updated.";
    }


    //deleting a customer; not caching
    @CacheEvict
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id) {
        service.deleteCustomer(id);
    }

    //getting all customers
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        List<Customer> customers = service.getAllCustomers();
        return customers;
    }

}

