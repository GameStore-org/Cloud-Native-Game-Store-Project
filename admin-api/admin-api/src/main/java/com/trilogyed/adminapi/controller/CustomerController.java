package com.trilogyed.adminapi.controller;

import com.trilogyed.adminapi.model.Customer;
import com.trilogyed.adminapi.service.CustomerService;
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
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @Cacheable
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id) {
        Customer customer = customerService.findCustomerById(id);
        return customer;
    }

    @CacheEvict(key = "#customer.getCustomerId()")
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id, @RequestBody @Valid Customer customer) {
        if (customer.getCustomerId() == 0)
            customer.setCustomerId(id);
        if (id != customer.getCustomerId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the Customer object");
        }
        customerService.updateCustomer(id, customer);
    }

    @CacheEvict
    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    // didn't cache b/c result would change frequently as customers are added
    // handles requests to retrieve all customers
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customers;
    }

}

