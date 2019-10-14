package com.trilogyed.adminapi.service;


import com.trilogyed.adminapi.model.*;
import com.trilogyed.adminapi.util.feign.*;
import com.trilogyed.adminapi.viewModels.CustomerViewModel;
import com.trilogyed.adminapi.viewModels.InvoiceItemViewModel;
import com.trilogyed.adminapi.viewModels.InvoiceViewModel;
import org.apache.tomcat.jni.Local;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class InvoiceServiceLayer {

    private InvoiceClient invoiceClient;
    private InventoryClient inventoryClient;
    private ProductClient productClient;
    private CustomerService csl;
    private InvoiceService sl;

    @Test
    public void addGetUpdateDeleteInvoice() {
        invoiceClient = mock(InvoiceClient.class);
        inventoryClient = mock(InventoryClient.class);
        productClient = mock(ProductClient.class);
        csl = mock(CustomerService.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1100);
        invoiceItem.setQuantity(250);
        invoiceItem.setUnitPrice(new BigDecimal("1.49"));

        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(10001);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(1100);
        invoiceItem2.setQuantity(250);
        invoiceItem2.setUnitPrice(new BigDecimal("1.49"));

        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInvoiceId(1);
        invoiceItem3.setInventoryId(2200);
        invoiceItem3.setQuantity(300);
        invoiceItem3.setUnitPrice(new BigDecimal("1.49"));

        InvoiceItem anotherInvoiceItem1 = new InvoiceItem();
        anotherInvoiceItem1.setInvoiceItemId(10002);
        anotherInvoiceItem1.setInvoiceId(1);
        anotherInvoiceItem1.setInventoryId(2200);
        anotherInvoiceItem1.setQuantity(300);
        anotherInvoiceItem1.setUnitPrice(new BigDecimal("1.49"));

        List<InvoiceItem> invoiceItemList1 = new ArrayList<>();
        invoiceItemList1.add(invoiceItem);
        invoiceItemList1.add(invoiceItem3);

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem2);
        invoiceItemList.add(anotherInvoiceItem1);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(10);
        ivm.setPurchaseDate( LocalDate.now());
        ivm.setInvoiceItemList(invoiceItemList);

        InvoiceViewModel savedInvoice = new InvoiceViewModel();
        savedInvoice.setInvoiceId(1);
        savedInvoice.setCustomerId(10);
        savedInvoice.setPurchaseDate(LocalDate.now());
        savedInvoice.setInvoiceItemList(invoiceItemList);

        InvoiceViewModel anotherInvoice = new InvoiceViewModel();
        anotherInvoice.setInvoiceId(2);
        anotherInvoice.setCustomerId(20);
        anotherInvoice.setPurchaseDate(LocalDate.now());
        anotherInvoice.setInvoiceItemList(invoiceItemList);

        List<InvoiceViewModel> invoiceList = new ArrayList<>();
        invoiceList.add(savedInvoice);
        invoiceList.add(anotherInvoice);

        List<InvoiceViewModel> invoiceList1 = new ArrayList<>();
        invoiceList1.add(savedInvoice);

        List<InvoiceViewModel> invoiceList2 = new ArrayList<>();
        invoiceList2.add(anotherInvoice);

        InvoiceViewModel updateInvoice = new InvoiceViewModel();
        updateInvoice.setInvoiceId(3);
        updateInvoice.setCustomerId(30);
        updateInvoice.setPurchaseDate(LocalDate.now());
        updateInvoice.setInvoiceItemList(invoiceItemList);

        InvoiceViewModel deleteInvoice = new InvoiceViewModel();
        deleteInvoice.setInvoiceId(4);
        deleteInvoice.setCustomerId(30);
        deleteInvoice.setPurchaseDate(LocalDate.now());
        deleteInvoice.setInvoiceItemList(invoiceItemList);

        //customers

        CustomerViewModel customer1 = new CustomerViewModel();
        customer1.setCustomerId(1);
        customer1.setFirstName("Nisa");
        customer1.setLastName("Cani");
        customer1.setStreet("35th street");
        customer1.setCity("Astoria");
        customer1.setZip("11106");
        customer1.setEmail("eli@gmail.com");
        customer1.setPhone("123-456-789");
        customer1.setLevelUpId(1010);
        customer1.setPoints(0);
        customer1.setMemeberDate(LocalDate.now());


        CustomerViewModel customer2 = new CustomerViewModel();
        customer2.setCustomerId(2);
        customer2.setFirstName("Nisa");
        customer2.setLastName("Cani");
        customer2.setStreet("35th street");
        customer2.setCity("Astoria");
        customer2.setZip("11106");
        customer2.setEmail("eli@gmail.com");
        customer2.setPhone("123-456-789");
        customer1.setLevelUpId(1010);
        customer1.setPoints(0);
        customer1.setMemeberDate(LocalDate.now());

        List<CustomerViewModel> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);

        //updated customer
        CustomerViewModel customer3 = new CustomerViewModel();
        customer3.setCustomerId(3);
        customer3.setFirstName("Joanne");
        customer3.setLastName("Cani");
        customer3.setStreet("Central 35th street");
        customer3.setCity("Astoria");
        customer3.setZip("10014");
        customer3.setEmail("eli@gmail.com");
        customer3.setPhone("123-456-789");
        customer3.setLevelUpId(1030);
        customer3.setPoints(100);
        customer3.setMemeberDate(LocalDate.now());


        //deleted customer
        CustomerViewModel customer4 = new CustomerViewModel();
        customer4.setCustomerId(4);
        customer4.setFirstName("Nisa");
        customer4.setLastName("Cani");
        customer4.setStreet("35th street");
        customer4.setCity("Astoria");
        customer4.setZip("11106");
        customer4.setEmail("eli@gmail.com");
        customer4.setPhone("123-456-789");
        customer4.setLevelUpId(1030);
        customer4.setPoints(100);
        customer4.setMemeberDate(LocalDate.now());


        Product product1 = new Product();
        product1.setProductId(100);
        product1.setProductName("Anca");
        product1.setProductDescription("beautiful");
        product1.setListPrice(new BigDecimal("21.21"));
        product1.setUnitCost(new BigDecimal("10.10"));

        Product product2 = new Product();
        product2.setProductId(200);
        product2.setProductName("Anca Cani");
        product2.setProductDescription("beautiful");
        product2.setListPrice(new BigDecimal("21.21"));
        product2.setUnitCost(new BigDecimal("10.10"));

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        //updated product
        Product product3 = new Product();
        product3.setProductId(300);
        product3.setProductName("Anca");
        product3.setProductDescription("beautiful");
        product3.setListPrice(new BigDecimal("21.21"));
        product3.setUnitCost(new BigDecimal("10.10"));


        //deleted product
        Product product4 = new Product();
        product4.setProductId(400);
        product4.setProductName("Anca");
        product4.setProductDescription("beautiful");
        product4.setListPrice(new BigDecimal("21.21"));
        product4.setUnitCost(new BigDecimal("10.10"));

        //inventories

        Inventory savedInventory = new Inventory();
        savedInventory.setInventoryId(1100);
        savedInventory.setProductId(100);
        savedInventory.setQuantity(250);

        Inventory anotherInventory = new Inventory();
        anotherInventory.setInventoryId(2200);
        anotherInventory.setProductId(200);
        anotherInventory.setQuantity(350);

        doReturn(savedInvoice).when(invoiceClient).createInvoice(ivm);
        doReturn(savedInvoice).when(invoiceClient).getInvoice(1);
        doReturn(anotherInvoice).when(invoiceClient).getInvoice(2);


        doReturn(savedInventory).when(inventoryClient).getInventory(1100);
        doReturn(anotherInventory).when(inventoryClient).getInventory(2200);


        doReturn(product1).when(productClient).getProduct(100);
        doReturn(product2).when(productClient).getProduct(200);


        doReturn(invoiceList2).when(invoiceClient).getInvoiceByCustomerId(20);
        doReturn(invoiceList).when(invoiceClient).getAllInvoices();


        doReturn(customer1).when(csl).getCustomer(10);
        doNothing().when(csl).updateCustomer(customer1);
        doReturn(customer2).when(csl).getCustomer(20);
        doNothing().when(csl).updateCustomer(customer2);
        doReturn(customer2).when(csl).getCustomer(30);
        doNothing().when(csl).updateCustomer(customer3);


        doNothing().when(invoiceClient).updateInvoice(updateInvoice, updateInvoice.getInvoiceId());
        doReturn(updateInvoice).when(invoiceClient).getInvoice(3);
        doNothing().when(invoiceClient).deleteInvoice(4);
        doReturn(null).when(invoiceClient).getInvoice(4);

        //add
        sl = new InvoiceService(invoiceClient,productClient, inventoryClient, csl);
        InvoiceViewModel tivm = sl.createInvoice(ivm);
        InvoiceViewModel tivm1 = sl.getInvoice(tivm.getInvoiceId());
        assertEquals(tivm,tivm1);

        //all inv

        List<InvoiceViewModel> tivmList = sl.getAllInvoices();
        assertEquals(tivmList.size(),2);

        //by customer id

        List<InvoiceViewModel> tivmListByCustomerId = sl.getInvoicesByCustomerId(20);
        assertEquals(tivmListByCustomerId.size(),1);
//
//        /** Testing get InvoiceItems by invoiceId */
//        List<InvoiceItemViewModel> iivmList = sl.getItemsByInvoiceId(1);
//        assertEquals(iivmList.size(),2);

        //update
        InvoiceViewModel toUpdate = sl.getInvoice(3);
        sl.updateInvoiceIncludingInvoiceItems(toUpdate);

        InvoiceViewModel afterUpdate = sl.getInvoice(3);
        assertEquals(toUpdate,afterUpdate);

        //delete
        sl.deleteInvoice(4);
        assertNull(sl.getInvoice(4));
    }
    }


