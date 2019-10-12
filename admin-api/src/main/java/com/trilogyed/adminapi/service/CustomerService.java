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
import java.util.Comparator;
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
    public CustomerViewModel createCustomer(Customer customer)
    {
        customer = customerClient.createCustomer(customer);
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(customer.getCustomerId());
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.now());
        levelUp = levelUpClient.createLevelUp(levelUp);
        return buildCustomerViewModel(customer);
    }

    public CustomerViewModel getCustomer(Integer customerId){

        Customer customer = customerClient.getCustomer(customerId);
        return buildCustomerViewModel(customer);
    }

    public List<CustomerViewModel> getAllCustomers() {

        List<CustomerViewModel> cvmList = new ArrayList<>();
        List<Customer> customers = customerClient.getAllCustomers();
        customers.stream().forEach(customer ->
        {
            CustomerViewModel cvm = buildCustomerViewModel(customer);
            cvmList.add(cvm);
        });
        return cvmList;
    }

    public void updateCustomer(CustomerViewModel cvm)
    {
        Customer customer = customerClient.getCustomer(cvm.getCustomerId());
        customer.setFirstName(cvm.getFirstName());
        customer.setLastName(cvm.getLastName());
        customer.setStreet(cvm.getStreet());
        customer.setCity(cvm.getCity());
        customer.setZip(cvm.getZip());
        customer.setEmail(cvm.getEmail());
        customer.setPhone(cvm.getPhone());
        customerClient.updateCustomer(customer.getCustomerId(),customer);
        List<LevelUp> levelUpList = levelUpClient.getAllLevelUpsByCustomerId(customer.getCustomerId());
        LevelUp levelUp = deleteExtraLevelUps(levelUpList);
        levelUp.setPoints(cvm.getPoints());
        levelUpClient.updateLevelUp(levelUp, levelUp.getLevelUpId());
    }

    public void deleteCustomer(Integer customerId)

    {
        levelUpClient.deleteLevelUp(customerId);
        customerClient.deleteCustomer(customerId);
    }

    public void deleteLevelUpByLevelUpId(Integer levelUpId)
    {
        levelUpClient.deleteLevelUp(levelUpId);
    }

    //helper method

    public CustomerViewModel buildCustomerViewModel(Customer customer)
    {
        if (customer==null) return null;
        List<LevelUp> levelUpList = levelUpClient.getAllLevelUpsByCustomerId(customer.getCustomerId());
        LevelUp levelUp = deleteExtraLevelUps(levelUpList);

        CustomerViewModel cvm = new CustomerViewModel();
        cvm.setCustomerId(customer.getCustomerId());
        cvm.setFirstName(customer.getFirstName());
        cvm.setLastName(customer.getLastName());
        cvm.setStreet(customer.getStreet());
        cvm.setCity(customer.getCity());
        cvm.setZip(customer.getZip());
        cvm.setEmail(customer.getEmail());
        cvm.setPhone(customer.getPhone());
        cvm.setLevelUpId(levelUp.getLevelUpId());
        cvm.setPoints(levelUp.getPoints());
        cvm.setMemeberDate(levelUp.getMemberDate());
        return cvm;
    }

    // One customer can have only one levelUp Id */

    public LevelUp deleteExtraLevelUps(List<LevelUp> levelUpList){

        Comparator<LevelUp> maximumId = Comparator.comparing(LevelUp::getLevelUpId);
        Comparator<LevelUp> minimumMemberDate = Comparator.comparing((LevelUp::getMemberDate));

        LevelUp maximumIdLevelUp = levelUpList.stream()
                                                .max(maximumId)
                                                .get();

        LevelUp minMemberDateLevelUp = levelUpList.stream()
                                                    .min(minimumMemberDate)
                                                    .get();

        maximumIdLevelUp.setMemberDate(minMemberDateLevelUp.getMemberDate());

        for (LevelUp level: levelUpList)
        {
            if(level.getLevelUpId()!=maximumIdLevelUp.getLevelUpId())
            {
                maximumIdLevelUp.setPoints(maximumIdLevelUp.getPoints()+level.getPoints());
                levelUpClient.deleteLevelUp(level.getLevelUpId());
            }
        }

        levelUpClient.updateLevelUp(maximumIdLevelUp, maximumIdLevelUp.getLevelUpId());
        return maximumIdLevelUp;
    }
}
