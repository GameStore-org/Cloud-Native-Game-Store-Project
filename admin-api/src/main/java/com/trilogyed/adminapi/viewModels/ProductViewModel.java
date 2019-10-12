package com.trilogyed.adminapi.viewModels;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductViewModel {

    private int productId;
    private String productName;
    private String productDescription;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private int inventoryId;
    private int quantityInInvetory;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getQuantityInInvetory() {
        return quantityInInvetory;
    }

    public void setQuantityInInvetory(int quantityInInvetory) {
        this.quantityInInvetory = quantityInInvetory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductViewModel that = (ProductViewModel) o;
        return productId == that.productId &&
                inventoryId == that.inventoryId &&
                quantityInInvetory == that.quantityInInvetory &&
                productName.equals(that.productName) &&
                productDescription.equals(that.productDescription) &&
                listPrice.equals(that.listPrice) &&
                unitCost.equals(that.unitCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productDescription, listPrice, unitCost, inventoryId, quantityInInvetory);
    }
}
