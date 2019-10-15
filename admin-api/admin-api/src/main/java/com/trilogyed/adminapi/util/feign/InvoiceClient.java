package com.trilogyed.adminapi.util.feign;

import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.viewModels.InvoiceViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceClient {

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    InvoiceViewModel createInvoice(@RequestBody @Valid InvoiceViewModel ivm);

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    List<InvoiceViewModel> getAllInvoices();

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    InvoiceViewModel getInvoice(@PathVariable("id") int id);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.PUT)
    void updateInvoice(@RequestBody @Valid InvoiceViewModel ivm, @PathVariable("id") int id);

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.DELETE)
    void deleteInvoice(@PathVariable("id") int id);

    @RequestMapping(value = "/invoices//customer/{id}", method = RequestMethod.GET)
    List<InvoiceViewModel> getInvoiceByCustomerId(@PathVariable("id") int id);
}
