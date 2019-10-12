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

        Customer savedCustomer = new Customer();
        savedCustomer.setCustomerId(1);
        savedCustomer.setFirstName("Nisa");
        savedCustomer.setLastName("Cani");
        savedCustomer.setStreet("35th street");
        savedCustomer.setCity("Astoria");
        savedCustomer.setZip("11106");
        savedCustomer.setEmail("eli@gmail.com");
        savedCustomer.setPhone("123-456-789");

        Customer anotherCustomer = new Customer();
        anotherCustomer.setCustomerId(2);
        anotherCustomer.setFirstName("Nisa");
        anotherCustomer.setLastName("Cani");
        anotherCustomer.setStreet("35th street");
        anotherCustomer.setCity("Astoria");
        anotherCustomer.setZip("11106");
        anotherCustomer.setEmail("eli@gmail.com");
        anotherCustomer.setPhone("123-456-789");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(savedCustomer);
        customerList.add(anotherCustomer);

        Customer updateCustomer = new Customer();
        updateCustomer.setCustomerId(3);
        updateCustomer.setFirstName("Joanne");
        updateCustomer.setLastName("Cani");
        updateCustomer.setStreet("Central 35th street");
        updateCustomer.setCity("Astoria");
        updateCustomer.setZip("10014");
        updateCustomer.setEmail("eli@gmail.com");
        updateCustomer.setPhone("123-456-789");

        Customer deleteCustomer = new Customer();
        deleteCustomer.setCustomerId(4);
        deleteCustomer.setFirstName("Nisa");
        deleteCustomer.setLastName("Cani");
        deleteCustomer.setStreet("35th street");
        deleteCustomer.setCity("Astoria");
        deleteCustomer.setZip("11106");
        deleteCustomer.setEmail("eli@gmail.com");
        deleteCustomer.setPhone("123-456-789");

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(0);
        levelUp.setMemberDate(LocalDate.now());

        LevelUp savedLevelUp = new LevelUp();
        savedLevelUp.setLevelUpId(1010);
        savedLevelUp.setCustomerId(1);
        savedLevelUp.setPoints(0);
        savedLevelUp.setMemberDate(LocalDate.now());

        LevelUp anotherLevelUp = new LevelUp();
        anotherLevelUp.setLevelUpId(1020);
        anotherLevelUp.setCustomerId(2);
        anotherLevelUp.setPoints(0);
        anotherLevelUp.setMemberDate(LocalDate.now());

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(savedLevelUp);
        levelUpList.add(anotherLevelUp);

        List<LevelUp> levelUpListByCustomerId1 = new ArrayList<>();
        levelUpListByCustomerId1.add(savedLevelUp);

        List<LevelUp> levelUpListByCustomerId2 = new ArrayList<>();
        levelUpListByCustomerId2.add(anotherLevelUp);

        LevelUp updateLevelUp = new LevelUp();
        updateLevelUp.setLevelUpId(1030);
        updateLevelUp.setCustomerId(3);
        updateLevelUp.setPoints(100);
        updateLevelUp.setMemberDate(LocalDate.now());

        List<LevelUp> levelUpListByUpdatedCustomerId = new ArrayList<>();
        levelUpListByUpdatedCustomerId.add(updateLevelUp);

        LevelUp deleteLevelUp = new LevelUp();
        deleteLevelUp.setLevelUpId(1040);
        deleteLevelUp.setCustomerId(4);
        deleteLevelUp.setPoints(40);
        deleteLevelUp.setMemberDate(LocalDate.now());

        doReturn(savedCustomer).when(customerClient).createCustomer(customer);
        doReturn(savedCustomer).when(customerClient).getCustomer(1);
        doReturn(customerList).when(customerClient).getAllCustomers();
        doReturn(anotherCustomer).when(customerClient).getCustomer(2);
        doNothing().when(customerClient).updateCustomer(updateCustomer.getCustomerId(),updateCustomer);
        doReturn(updateCustomer).when(customerClient).getCustomer(3);
        doNothing().when(customerClient).deleteCustomer(40);
        doReturn(null).when(customerClient).getCustomer(4);

        doReturn(savedLevelUp).when(levelUpClient).createLevelUp(levelUp);
        doReturn(savedLevelUp).when(levelUpClient).getLevelUp(1010);
        doReturn(levelUpList).when(levelUpClient).getAllLevelUps();
        doReturn(levelUpListByCustomerId1).when(levelUpClient).getAllLevelUpsByCustomerId(1);
        doReturn(levelUpListByCustomerId2).when(levelUpClient).getAllLevelUpsByCustomerId(2);
        doNothing().when(levelUpClient).updateLevelUp(updateLevelUp, updateLevelUp.getLevelUpId());
        doReturn(levelUpListByUpdatedCustomerId).when(levelUpClient).getAllLevelUpsByCustomerId(3);
        doNothing().when(levelUpClient).deleteLevelUp(40);
        doReturn(null).when(levelUpClient).getAllLevelUpsByCustomerId(4);
        doNothing().when(levelUpClient).deleteLevelUp(40);
        doReturn(null).when(levelUpClient).getAllLevelUps();

        sl = new CustomerService(customerClient, levelUpClient);

        //create a new Customer with LevelUp
        CustomerViewModel addingCustomer = sl.createCustomer(customer);
        CustomerViewModel fetchCustomerAdded = sl.getCustomer(addingCustomer.getCustomerId());
        assertEquals(addingCustomer, fetchCustomerAdded);


        List<CustomerViewModel> cvmList = sl.getAllCustomers();
        assertEquals(cvmList.size(), 2);

        //update Customer and LevelUp
        CustomerViewModel getCustomerVM = sl.getCustomer(3);
        sl.updateCustomer(getCustomerVM);
        CustomerViewModel fetchUpdatedCustomer = sl.getCustomer(getCustomerVM.getCustomerId());
        assertEquals(getCustomerVM, fetchUpdatedCustomer);

        //delete Customer
        sl.deleteCustomer(4);
        CustomerViewModel cvmDeleted = sl.getCustomer(4);
        assertNull(cvmDeleted);

        //delete LevelUp
        sl.deleteCustomer(1040);
        assertNull(levelUpClient.getLevelUp(1040));

    }
}