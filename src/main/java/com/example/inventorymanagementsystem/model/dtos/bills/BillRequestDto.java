package com.example.inventorymanagementsystem.model.dtos.bills;

import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public class BillRequestDto {

    private Long supplierId;
    private Long retailerId;
    private Long productId;
    private Integer amount;
    private Double currentPrice;
    private LocalDate date;

    public BillRequestDto(Long supplierId, Long retailerId, Long productId, Integer amount, Double currentPrice, LocalDate date) {
        this.supplierId = supplierId;
        this.retailerId = retailerId;
        this.productId = productId;
        this.amount = amount;
        this.currentPrice = currentPrice;
        this.date = date;
    }

    // Getter ve Setter metodları

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}