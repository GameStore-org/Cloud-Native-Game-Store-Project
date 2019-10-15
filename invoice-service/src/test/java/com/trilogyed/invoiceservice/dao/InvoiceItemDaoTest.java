package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoTest {


    @Autowired
    InvoiceItemDao invoiceItemDao;
    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {

        List<InvoiceItem> itemList = invoiceItemDao.getAllInvoiceItem();
        for (InvoiceItem invT : itemList) {
            invoiceItemDao.deleteInvoiceItem(invT.getInvoiceItemId());
        }
        List<Invoice> invList = invoiceDao.getAllInvoices();
        for (Invoice inv : invList) {
            invoiceDao.deleteInvoice(inv.getInvoiceId());
        }
    }

    @Test
    public void createGetDelete(){

        //create
        //need to create the parent table properties before child table

        Invoice inv = new Invoice();
        inv.setCustomerId(1);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 12));

        inv = invoiceDao.createInvoice(inv);


        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(inv.getInvoiceId());
        item.setInventoryId(1);
        item.setQuantity(25);
        item.setUnitPrice(new BigDecimal("14.00"));

        item = invoiceItemDao.createInvoiceItem(item);
        //get
        InvoiceItem item2 = invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        //checking if the two are equal
        assertEquals(item, item2);

        //delete
        invoiceItemDao.deleteInvoiceItem(item.getInvoiceItemId());
        item2 =invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        //conforms that item2 is null
        assertNull(item2);
    }

    @Test
    public void getAllInvoiceItem() {
        //need to create the parent table properties before child table
        Invoice inv = new Invoice();
        inv.setCustomerId(1);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 12));

        inv = invoiceDao.createInvoice(inv);


        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(inv.getInvoiceId());
        item.setInventoryId(1);
        item.setQuantity(25);
        item.setUnitPrice(new BigDecimal("14.00"));

        item = invoiceItemDao.createInvoiceItem(item);

        InvoiceItem item2 = new InvoiceItem();
        item2.setInvoiceId(inv.getInvoiceId());
        item2.setInventoryId(2);
        item2.setQuantity(50);
        item2.setUnitPrice(new BigDecimal("32.00"));

        item2 = invoiceItemDao.createInvoiceItem(item2);

        List<InvoiceItem> listOfItems = invoiceItemDao.getAllInvoiceItem();

        assertEquals(listOfItems.size(),2);
    }


    @Test
    public void updateInvoiceItem() {
        //need to create the parent table properties before child table

        Invoice inv = new Invoice();
        inv.setCustomerId(1);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 12));

        inv = invoiceDao.createInvoice(inv);

        InvoiceItem item = new InvoiceItem();
        item.setInvoiceId(inv.getInvoiceId());
        item.setInventoryId(1);
        item.setQuantity(25);
        item.setUnitPrice(new BigDecimal("14.00"));

        item = invoiceItemDao.createInvoiceItem(item);


        item.setQuantity(20);
        item.setUnitPrice(new BigDecimal("18.00"));

        invoiceItemDao.updateInvoiceItem(item);

        InvoiceItem item2 = invoiceItemDao.getInvoiceItem(item.getInvoiceItemId());

        assertEquals(item, item2);

    }
}