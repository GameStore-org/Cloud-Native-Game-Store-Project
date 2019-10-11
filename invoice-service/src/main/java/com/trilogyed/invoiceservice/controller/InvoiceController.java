package com.trilogyed.invoiceservice.controller;

import com.trilogyed.invoiceservice.exception.NotFoundException;
import com.trilogyed.invoiceservice.service.ServiceLayer;
import com.trilogyed.invoiceservice.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class InvoiceController {

    // Injection
    @Autowired
    ServiceLayer service;


    //creating invoice uri
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoice){

        return  service.createInvoice(invoice);
    }

    //getting invoice by id uri
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("id") int invoiceId){
        InvoiceViewModel invoice = service.getInvoice(invoiceId);
        if (invoice == null) {
            throw new NotFoundException("Could not get invoice with id : " + invoiceId + " try another id.");
        }
        return invoice;
    }

    //updating invoice by id
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel updateInvoice(@RequestBody @Valid InvoiceViewModel ivm, @PathVariable("id") int invoiceId){
        if (ivm.getInvoiceId() == 0)
            ivm.setInvoiceId(invoiceId);
        if (invoiceId != ivm.getInvoiceId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the Invoice object");
        }
        service.updateInvoice(ivm, invoiceId);

        //allows you to confirm invoice has been updated
        return getInvoice(invoiceId);
    }

    //delete invoice by id
    @RequestMapping(value = "invoices/{id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") int id){
        service.deleteInvoice(id);
    }

}
