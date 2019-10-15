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

    @Autowired
    ServiceLayer service;


    //creating invoice
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceViewModel createInvoice(@RequestBody InvoiceViewModel invoice){

        return  service.createInvoice(invoice);
    }

    //getting invoice by id
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
            throw new IllegalArgumentException("Please enter a valid invoice Id.");
        }
        service.updateInvoice(ivm, invoiceId);

        //confirms invoice has been updated
        return getInvoice(invoiceId);
    }

    //delete invoice by id
    @RequestMapping(value = "invoices/{id")
    @ResponseStatus(HttpStatus.OK)
    public String deleteInvoice(@PathVariable("id") int id){
        service.deleteInvoice(id);
        return "Invoice deleted.";
    }

}
