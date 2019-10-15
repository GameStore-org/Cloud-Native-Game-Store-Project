package com.trilogyed.customerservice.service;

import com.trilogyed.customerservice.dao.CustomerDao;
import com.trilogyed.customerservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    CustomerDao dao;

    @Autowired
    public ServiceLayer(CustomerDao dao) {
        this.dao = dao;
    }

    public Customer createCustomer(Customer customer) {
        return dao.createCustomer(customer);
    }

    public Customer getCustomer(int id) {
        return dao.getCustomer(id);
    }

    public void updateCustomer(Customer customer) {
        dao.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        dao.deleteCustomer(id);
    }

    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

}