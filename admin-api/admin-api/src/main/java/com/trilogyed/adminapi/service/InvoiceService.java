package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.util.feign.InvoiceClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class InvoiceService {

    private InvoiceClient invoiceClient;

    public InvoiceService(InvoiceClient invoiceClient){

        this.invoiceClient=invoiceClient;
    }


    @Transactional
    public Invoice saveInvoice(Invoice invoice){

        invoice=invoiceClient.createInvoice(invoice);

        return invoice;
    }

    public Invoice findInvoiceById(int invoiceId){

        Invoice invoice= invoiceClient.getInvoice(invoiceId);

        return invoice;
    }

    public List<Invoice> getInvoiceByCustomerId(int customerId){

        List<Invoice> invoiceByCustomerId=invoiceClient.getInvoiceByCustomerId(customerId);

        return invoiceByCustomerId;
    }


    public List<Invoice> getAllInvoices(){
        List<Invoice> invoices= invoiceClient.getAllInvoices();

        return invoices;
    }

    public void updateInvoice(int invoiceId, Invoice invoice){

        invoiceClient.updateInvoice(invoice, invoiceId);
    }

    public void deleteInvoice(int invoiceId){

        invoiceClient.deleteInvoice(invoiceId);
    }


}
