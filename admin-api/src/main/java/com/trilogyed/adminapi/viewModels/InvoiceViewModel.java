package com.trilogyed.adminapi.viewModels;

import com.trilogyed.adminapi.model.InvoiceItem;

import java.util.List;
import java.util.Objects;

public class InvoiceViewModel {

    private int invoiceId;
    private int customerId;
    private int purchaseDate;
    List<InvoiceItemViewModel> invoiceItemList;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(int purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public List<InvoiceItemViewModel> getInvoiceItemList() {
        return invoiceItemList;
    }

    public void setInvoiceItemList(List<InvoiceItemViewModel> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceViewModel that = (InvoiceViewModel) o;
        return invoiceId == that.invoiceId &&
                customerId == that.customerId &&
                purchaseDate == that.purchaseDate &&
                invoiceItemList.equals(that.invoiceItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, purchaseDate, invoiceItemList);
    }
}
