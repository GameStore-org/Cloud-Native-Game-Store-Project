package com.trilogyed.invoiceservice.dao;

import com.trilogyed.invoiceservice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {

    //method to created/get/getall/update/ and delete invoice items

    InvoiceItem createInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem getInvoiceItem(int invoiceItemId);

    List<InvoiceItem> getAllInvoiceItem();

    List<InvoiceItem> getInvoiceItemsByInvoiceId(int invoiceId);

    void updateInvoiceItem(InvoiceItem invoiceItem);

    void deleteInvoiceItem(int invoiceItemId);
}
