package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Customer;
import com.trilogyed.adminapi.model.LevelUp;
import com.trilogyed.adminapi.util.feign.CustomerClient;
import com.trilogyed.adminapi.util.feign.LevelUpClient;
import com.trilogyed.adminapi.viewModels.CustomerViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerService {

    private CustomerClient customerClient;
    private LevelUpClient levelUpClient;

    @Autowired
    public CustomerService(CustomerClient customerClient, LevelUpClient levelUpClient){

        this.customerClient=customerClient;
        this.levelUpClient=levelUpClient;
    }


    @Transactional
    public CustomerViewModel saveCustomer(Customer customer){

        customer=customerClient.createCustomer(customer);
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(customer.getCustomerId());
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.now());
        levelUp = levelUpClient.createLevelUp(levelUp);

        return buildCustomerViewModel(customer);
    }

    public CustomerViewModel findCustomerById(int customerId){

        Customer customer= customerClient.getCustomer(customerId);

        return buildCustomerViewModel(customer);
    }

//    public List<Customer> getCustomerByName(String customerName){
//
//        List<Customer> customersByName=customerClient.getCustomerByName(customerName);
//
//        return customersByName;
////    }
////

    public List<CustomerViewModel> getAllCustomers(){
        List<CustomerViewModel> cvmList= new ArrayList<>();
        List<Customer> customers= customerClient.getAllCustomers();

        for (Customer customer : customers){

            cvmList.add(buildCustomerViewModel(customer));
        }
        return cvmList;
    }

    public void updateCustomer(int customerId, Customer customer){

        customerClient.updateCustomer(customerId, customer);
        int levelUpByCustomer = levelUpClient.getLevelUpPointsByCustomerId(customer.getCustomerId());



    }

    public void deleteCustomer(int customerId){

        levelUpClient.deleteLevelUpByCustomerId(customerId);
        customerClient.deleteCustomer(customerId);
    }

    //helper method

    public CustomerViewModel buildCustomerViewModel(Customer customer)
    {
        int levelUpByCustomer = levelUpClient.getLevelUpPointsByCustomerId(customer.getCustomerId());

        LevelUp levelUp= levelUpClient.getLevelUpPointsByCustomerId(levelUpByCustomer);

        CustomerViewModel customervm = new CustomerViewModel();
        customervm.setCustomerId(customer.getCustomerId());
        customervm.setFirstName(customer.getFirstName());
        customervm.setLastName(customer.getLastName());
        customervm.setStreet(customer.getStreet());
        customervm.setCity(customer.getCity());
        customervm.setZip(customer.getZip());
        customervm.setEmail(customer.getEmail());
        customervm.setPhone(customer.getPhone());
        customervm.setLevelUpId(levelUp.getLevelUpId());
        customervm.setPoints(levelUp.getPoints());
        customervm.setMemeberDate(levelUp.getMemberDate());
        return customervm;
    }
}
