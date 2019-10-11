package com.trilogyed.invoiceservice.service;


import com.trilogyed.invoiceservice.dao.InvoiceDao;
import com.trilogyed.invoiceservice.dao.InvoiceItemDao;
import com.trilogyed.invoiceservice.model.Invoice;
import com.trilogyed.invoiceservice.model.InvoiceItem;
import com.trilogyed.invoiceservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {
    InvoiceDao invoiceDao;
    InvoiceItemDao invoiceItemDao;

    //dependency injection
    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao){this.invoiceDao=invoiceDao; this.invoiceItemDao=invoiceItemDao;}


    //creating invoice
    @Transactional
    public InvoiceViewModel createInvoice(InvoiceViewModel ivm) {
        Invoice inv = new Invoice();
        inv.setCustomerId(ivm.getCustomerId());
        inv.setPurchaseDate(ivm.getPurchaseDate());
        inv = invoiceDao.createInvoice(inv);

        for (InvoiceItem item : ivm.getInvoiceItems()) {
            InvoiceItem invoiceItem = new InvoiceItem();

            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setInvoiceId(inv.getInvoiceId());
            invoiceItem.setUnitPrice(item.getUnitPrice());
            invoiceItem.setInventoryId(item.getInventoryId());
            invoiceItemDao.createInvoiceItem(invoiceItem);
        }
        return buildInvoiceViewModel(inv);
    }

    //get invoice
    public InvoiceViewModel getInvoice(int invoiceId) {
        Invoice inv = invoiceDao.getInvoice(invoiceId);
            return buildInvoiceViewModel(inv);
    }

    // get all invoices
    public List<InvoiceViewModel> getAllInvoices() {
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        for (Invoice inv : invoiceDao.getAllInvoices()) {
            ivmList.add(buildInvoiceViewModel(inv));
        }
        return ivmList;
    }

    // get invoices by customer id
    public List<InvoiceViewModel> getInvoicesByCustomerId(int customerId) {
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        for (Invoice inv : invoiceDao.getInvoicesByCustomerId(customerId)) {
            ivmList.add(buildInvoiceViewModel(inv));
        }
        return ivmList;
    }

    // updating invoice
    public void updateInvoice(InvoiceViewModel ivm, int invoiceId) {

        Invoice inv = new Invoice();
        inv.setInvoiceId(invoiceId);
        inv.setCustomerId(ivm.getCustomerId());
        inv.setPurchaseDate(ivm.getPurchaseDate());
        invoiceDao.updateInvoice(inv);

        for(InvoiceItem item: ivm.getInvoiceItems()){
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setInvoiceId(inv.getInvoiceId());
            invoiceItem.setUnitPrice(item.getUnitPrice());
            invoiceItem.setInventoryId(item.getInventoryId());
            invoiceItemDao.createInvoiceItem(invoiceItem);
        }
    }

    //delete invoice
    public void deleteInvoice( int invoiceId){
        invoiceDao.deleteInvoice(invoiceId);
    }


    //helper method
    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setInvoiceItems(invoiceItemDao.getInvoiceItemsByInvoiceId(invoice.getInvoiceId()));
        return ivm;
    }
}
