package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Customer;
import com.trilogyed.adminapi.model.LevelUp;
import com.trilogyed.adminapi.util.feign.CustomerClient;
import com.trilogyed.adminapi.util.feign.LevelUpClient;
import com.trilogyed.adminapi.viewModels.CustomerViewModel;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;


public class CustomerServiceLayerTest {

    private CustomerClient customerClient;
    private LevelUpClient levelUpClient;
    CustomerService sl;

    @Test
    public void addGetUpdateDeleteCustomer() {
        customerClient = mock(CustomerClient.class);
        levelUpClient = mock(LevelUpClient.class);

        Customer customer = new Customer();
        customer.setFirstName("Nisa");
        customer.setLastName("Cani");
        customer.setStreet("35th street");
        customer.setCity("Astoria");
        customer.setZip("11106");
        customer.setEmail("eli@gmail.com");
        customer.setPhone("123-456-789");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Nisa");
        customer1.setLastName("Cani");
        customer1.setStreet("35th street");
        customer1.setCity("Astoria");
        customer1.setZip("11106");
        customer1.setEmail("eli@gmail.com");
        customer1.setPhone("123-456-789");

        Customer customer2 = new Customer();
        customer2.setCustomerId(2);
        customer2.setFirstName("Nisa");
        customer2.setLastName("Cani");
        customer2.setStreet("35th street");
        customer2.setCity("Astoria");
        customer2.setZip("11106");
        customer2.setEmail("eli@gmail.com");
        customer2.setPhone("123-456-789");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        //updated customer
        Customer customer3 = new Customer();
        customer3.setCustomerId(3);
        customer3.setFirstName("Joanne");
        customer3.setLastName("Cani");
        customer3.setStreet("Central 35th street");
        customer3.setCity("Astoria");
        customer3.setZip("10014");
        customer3.setEmail("eli@gmail.com");
        customer3.setPhone("123-456-789");

        //deleted customer
        Customer customer4 = new Customer();
        customer4.setCustomerId(4);
        customer4.setFirstName("Nisa");
        customer4.setLastName("Cani");
        customer4.setStreet("35th street");
        customer4.setCity("Astoria");
        customer4.setZip("11106");
        customer4.setEmail("eli@gmail.com");
        customer4.setPhone("123-456-789");



        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.now());

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setLevelUpId(1010);
        levelUp2.setCustomerId(1);
        levelUp2.setPoints(0);
        levelUp2.setMemberDate(LocalDate.now());

        LevelUp levelUp3 = new LevelUp();
        levelUp3.setLevelUpId(1020);
        levelUp3.setCustomerId(2);
        levelUp3.setPoints(0);
        levelUp3.setMemberDate(LocalDate.now());

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp2);
        levelUpList.add(levelUp3);

        List<LevelUp> levelUpListByCustomerId1 = new ArrayList<>();
        levelUpListByCustomerId1.add(levelUp2);

        List<LevelUp> levelUpListByCustomerId2 = new ArrayList<>();
        levelUpListByCustomerId2.add(levelUp3);

        //updated levelUp
        LevelUp levelUp4 = new LevelUp();
        levelUp4.setLevelUpId(1030);
        levelUp4.setCustomerId(3);
        levelUp4.setPoints(100);
        levelUp4.setMemberDate(LocalDate.now());

        List<LevelUp> levelUpListByUpdatedCustomerId = new ArrayList<>();
        levelUpListByUpdatedCustomerId.add(levelUp4);

        //deleted levelUp
        LevelUp levelUp5 = new LevelUp();
        levelUp5.setLevelUpId(1040);
        levelUp5.setCustomerId(4);
        levelUp5.setPoints(40);
        levelUp5.setMemberDate(LocalDate.now());

        doReturn(customer1).when(customerClient).createCustomer(customer);
        doReturn(customer1).when(customerClient).getCustomer(1);
        doReturn(customerList).when(customerClient).getAllCustomers();
        doReturn(customer2).when(customerClient).getCustomer(2);


        doNothing().when(customerClient).updateCustomer(customer3.getCustomerId(),customer3);
        doReturn(customer3).when(customerClient).getCustomer(3);
        doNothing().when(customerClient).deleteCustomer(40);
        doReturn(null).when(customerClient).getCustomer(4);

        doReturn(levelUp2).when(levelUpClient).createLevelUp(levelUp);
        doReturn(levelUp2).when(levelUpClient).getLevelUp(1010);
        doReturn(levelUpList).when(levelUpClient).getAllLevelUps();
        doReturn(levelUpListByCustomerId1).when(levelUpClient).getAllLevelUpsByCustomerId(1);
        doReturn(levelUpListByCustomerId2).when(levelUpClient).getAllLevelUpsByCustomerId(2);

        doNothing().when(levelUpClient).updateLevelUp(levelUp4, levelUp4.getLevelUpId());
        doReturn(levelUpListByUpdatedCustomerId).when(levelUpClient).getAllLevelUpsByCustomerId(3);

        doNothing().when(levelUpClient).deleteLevelUp(40);
        doReturn(null).when(levelUpClient).getAllLevelUpsByCustomerId(4);
        doNothing().when(levelUpClient).deleteLevelUp(40);
        doReturn(null).when(levelUpClient).getAllLevelUps();


        sl = new CustomerService(customerClient, levelUpClient);

        //create a new Customer with LevelUp
        CustomerViewModel customerViewModel = sl.createCustomer(customer);
        CustomerViewModel customerAdded = sl.getCustomer(customerViewModel.getCustomerId());
        assertEquals(customerViewModel, customerAdded);


        List<CustomerViewModel> customerViewModelList = sl.getAllCustomers();
        assertEquals(customerViewModelList.size(), 2);

        //update Customer and LevelUp
        CustomerViewModel customerViewModel1 = sl.getCustomer(3);
        sl.updateCustomer(customerViewModel1);
        CustomerViewModel updatedCustomerVm = sl.getCustomer(customerViewModel1.getCustomerId());
        assertEquals(customerViewModel1, updatedCustomerVm);

        //delete Customer
        sl.deleteCustomer(4);
        CustomerViewModel customerViewModel2 = sl.getCustomer(4);
        assertEquals(null, customerViewModel2);

        //delete LevelUp
        sl.deleteCustomer(1040);
        assertNull(levelUpClient.getLevelUp(1040));

    }
}