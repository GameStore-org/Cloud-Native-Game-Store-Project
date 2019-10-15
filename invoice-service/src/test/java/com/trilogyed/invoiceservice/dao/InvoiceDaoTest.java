package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    InvoiceDao invoiceDao;


    @Before
    public void setUp() throws Exception {
        List<Invoice> invList = invoiceDao.getAllInvoices();
        for (Invoice inv : invList) {
            invoiceDao.deleteInvoice(inv.getInvoiceId());
        }
    }

    @Test
    public void createGetDelete() {
        //create-
        Invoice inv = new Invoice();
        inv.setCustomerId(1);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 12));

        inv = invoiceDao.createInvoice(inv);

        Invoice inv2 = invoiceDao.getInvoice(inv.getInvoiceId());

        //checking if the two items are equal
        assertEquals(inv, inv2);

        //delete
        invoiceDao.deleteInvoice(inv.getInvoiceId());
        inv2 = invoiceDao.getInvoice(inv.getInvoiceId());
        //checking if inv2 is deleted
        assertNull(inv2);

    }

    @Test
    public void getAllInvoices() {
        Invoice inv = new Invoice();
        inv.setCustomerId(1);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 12));

        inv = invoiceDao.createInvoice(inv);

         inv= new Invoice();
        inv.setCustomerId(2);
        inv.setPurchaseDate(LocalDate.of(2019, 11, 12));
        inv = invoiceDao.createInvoice(inv);

        List<Invoice> listOfInvoices = invoiceDao.getAllInvoices();
        assertEquals(listOfInvoices.size(), 2);

    }


    @Test
    public void updateInvoice() {
        Invoice inv = new Invoice();
        inv.setCustomerId(1);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 12));

        inv= invoiceDao.createInvoice(inv);

        inv.setCustomerId(4);
        inv.setPurchaseDate(LocalDate.of(2019, 12, 8));

        invoiceDao.updateInvoice(inv);

        Invoice inv2 = invoiceDao.getInvoice(inv.getInvoiceId());

        assertEquals(inv, inv2);

    }
}
