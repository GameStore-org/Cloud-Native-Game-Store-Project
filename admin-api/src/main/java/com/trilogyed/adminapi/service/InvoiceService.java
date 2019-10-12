package com.trilogyed.adminapi.service;

import com.trilogyed.adminapi.model.Customer;
import com.trilogyed.adminapi.model.Inventory;
import com.trilogyed.adminapi.model.Invoice;
import com.trilogyed.adminapi.model.InvoiceItem;
import com.trilogyed.adminapi.util.feign.CustomerClient;
import com.trilogyed.adminapi.util.feign.InventoryClient;
import com.trilogyed.adminapi.util.feign.InvoiceClient;
import com.trilogyed.adminapi.util.feign.ProductClient;
import com.trilogyed.adminapi.viewModels.InvoiceItemViewModel;
import com.trilogyed.adminapi.viewModels.InvoiceViewModel;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {

    private InvoiceClient invoiceClient;
    private ProductClient productClient;
    private InventoryClient inventoryClient;
    private CustomerClient customerClient;

    public InvoiceService(InvoiceClient invoiceClient,ProductClient productClient, InventoryClient inventoryClient, CustomerClient customerClient){

        this.invoiceClient=invoiceClient;
        this.productClient=productClient;
        this.inventoryClient=inventoryClient;
        this.customerClient= customerClient;
    }

    @Transactional
    public InvoiceViewModel createInvoice(InvoiceViewModel ivm){
        List<InvoiceItemViewModel> itList = ivm.getInvoiceItemList();
        List<InvoiceItem> iiList = new ArrayList<>();

        Invoice invoice=new Invoice();
        invoice.setInvoiceId(ivm.getInvoiceId());

            for (InvoiceItemViewModel it : itList) {

                Inventory inventory = inventoryClient.getInventory(it.getInventoryId());
                if (it.getQuantity() <= inventory.getQuantity()) {
                    ivm = invoiceClient.createInvoice(invoice);
                }
            }
                return buildInvoiceViewModel(ivm);
            }

    public InvoiceViewModel getInvoice(int invoiceId){


        InvoiceViewModel ivm = invoiceClient.getInvoice(invoiceId);

        return buildInvoiceViewModel(ivm);
    }

    public List<InvoiceViewModel> getAllInvoices(){
        List<InvoiceViewModel> ivmList = invoiceClient.getAllInvoices();
        List<InvoiceViewModel> bivmList = new ArrayList<>();

        for (InvoiceViewModel ivm : ivmList){

            ivm=buildInvoiceViewModel(ivm);
                 bivmList.add(ivm);
        };
        return bivmList;
    }

    public List<InvoiceViewModel> getInvoicesByCustomerId(Integer customerId) {
        List<InvoiceViewModel> ivmList = invoiceClient.getInvoiceByCustomerId(customerId);
        List<InvoiceViewModel> tivmList = new ArrayList<>();
        ivmList.stream().forEach(invoiceViewModel ->{
            InvoiceViewModel tvm = buildInvoiceViewModel(invoiceViewModel);
            tivmList.add(tvm);
        });
        return tivmList;
    }

    public void updateInvoiceIncludingInvoiceItems(InvoiceViewModel invoiceViewModel)
    {
        InvoiceViewModel ivm = invoiceClient.getInvoice(invoiceViewModel.getInvoiceId());
        ivm.setCustomerId(invoiceViewModel.getCustomerId());
        ivm.setPurchaseDate(invoiceViewModel.getPurchaseDate());
        List<InvoiceItemViewModel> iivmList = invoiceViewModel.getInvoiceItemList();

        List<InvoiceItem> iiList = new ArrayList<>();

        invoiceClient.updateInvoice(ivm, ivm.getInvoiceId());
        ivm = buildInvoiceViewModel(ivm);
    }

    public void deleteInvoice(Integer invoiceId)    {
        invoiceClient.deleteInvoice(invoiceId);
    }

    // Helper Method - Building the InvoiceViewModel

    public InvoiceViewModel buildInvoiceViewModel(InvoiceViewModel ivm)
    {
        if (ivm==null) return null;
        InvoiceViewModel iViewModel = new InvoiceViewModel();
        iViewModel.setInvoiceId(ivm.getInvoiceId());
        iViewModel.setPurchaseDate(ivm.getPurchaseDate());
        List<InvoiceItemViewModel>  invoiceItemList = ivm.getInvoiceItemList();
        List<InvoiceItemViewModel> iivmList = new ArrayList<>();

        final BigDecimal[] total = {new BigDecimal("0.00")};
        invoiceItemList.stream().forEach(invoiceItem -> {
            InvoiceItem invItem= new InvoiceItem();
            invItem.setInvoiceItemId(ivm.getInvoiceId());
            InvoiceItemViewModel iivm = buildInvoiceItemViewModel(invItem);
            total[0] = iivm.getSubtotal().add(total[0]);
            iivmList.add(iivm);
        });

        iViewModel.setInvoiceItemList(iivmList);
        Customer customer = customerClient.getCustomer(ivm.getCustomerId());
        iViewModel.setCustomerId(customer.getCustomerId());
        return iViewModel;
    }


    //helper methods

    public InvoiceItemViewModel buildInvoiceItemViewModel(InvoiceItem invoiceItem){

        InvoiceItemViewModel itvm=new InvoiceItemViewModel();
        itvm.setInvoiceItemId(invoiceItem.getInvoiceItemId());
        itvm.setInvoiceId(invoiceItem.getInvoiceId());
        itvm.setInventoryId(invoiceItem.getInventoryId());
        //connect it with the inventory
        Inventory inventory= inventoryClient.getInventory(invoiceItem.getInventoryId());
        itvm.setQuantity(invoiceItem.getQuantity());
        itvm.setUnitPrice(invoiceItem.getUnitPrice());
        itvm.setSubtotal(invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuantity())));

        return itvm;
    }

}
