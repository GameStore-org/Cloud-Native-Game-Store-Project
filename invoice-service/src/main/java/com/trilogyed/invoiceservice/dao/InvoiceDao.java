package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    //method to created/get/getall/update/ and delete invoice
    Invoice createInvoice(Invoice invoice);

    Invoice getInvoice(int invoiceId);

    List<Invoice> getAllInvoices();

    List<Invoice> getInvoicesByCustomerId(int customerId);

    void updateInvoice(Invoice invoice);

    void deleteInvoice(int invoiceId);


}

