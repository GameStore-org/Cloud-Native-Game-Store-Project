package com.trilogyed.adminapi.controller;


import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"invoices"})
public class InvoiceController {

    @Autowired
    InvoiceService service;

    @CachePut(key = "#result.getInvoiceId()")
    @PostMapping("/invoices")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice addInvoice(@RequestBody Invoice ivm){
        return service.saveInvoice(ivm);
    }

    @GetMapping("/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoices(){
        return service.getAllInvoices();
    }

    @Cacheable
    @GetMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoice(@PathVariable("id") int id){
        return service.findInvoiceById(id);
    }

    @CacheEvict(key = "#ivm.getInvoiceId()")
    @PutMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody @Valid Invoice ivm, @PathVariable("id") int id){
        if (ivm.getInvoiceId() == 0)
            ivm.setInvoiceId(id);
        if (id != ivm.getInvoiceId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the Invoice object");
        }
        service.updateInvoice(id,ivm);
    }

    @CacheEvict
    @DeleteMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") int id){
        service.deleteInvoice(id);
    }

    @GetMapping("/invoices/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoiceByCustomerId(@PathVariable("id") int id){
        return service.getInvoiceByCustomerId(id);
    }

}
