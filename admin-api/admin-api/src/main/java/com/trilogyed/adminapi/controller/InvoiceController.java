package com.trilogyed.adminapi.controller;


import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.service.InvoiceService;
import com.trilogyed.adminapi.viewModels.InvoiceViewModel;
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
    public InvoiceViewModel addInvoice(@RequestBody InvoiceViewModel ivm){

        return service.createInvoice(ivm);
    }

    @GetMapping("/invoices")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getAllInvoices(){

        return service.getAllInvoices();
    }

    @Cacheable
    @GetMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel getInvoice(@PathVariable("id") int id){

        return service.getInvoice(id);
    }

    @CacheEvict(key = "#ivm.getInvoiceId()")
    @PutMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody @Valid InvoiceViewModel ivm){
        if (ivm.getInvoiceId() == 0)
            ivm.setInvoiceId(ivm.getInvoiceId());
        if (ivm.getInvoiceId() != ivm.getInvoiceId()) {
            throw new IllegalArgumentException("ID on path must match the ID in the Invoice object");
        }
        service.updateInvoiceIncludingInvoiceItems(ivm);
    }

    @CacheEvict
    @DeleteMapping("/invoices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable("id") int id){

        service.deleteInvoice(id);
    }

    @GetMapping("/invoices/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable("id") int id){
        return service.getInvoicesByCustomerId(id);
    }

}
