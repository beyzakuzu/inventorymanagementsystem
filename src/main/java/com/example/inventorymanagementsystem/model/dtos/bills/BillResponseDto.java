package com.example.inventorymanagementsystem.model.dtos.bills;

import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


public class BillResponseDto {

    private Long id;
    private String supplierName;
    private String retailerName;
    private String productName;
    private Integer amount;
    private Double currentPrice;
    private LocalDate date;

    public BillResponseDto(Long id, String supplierName, String retailerName, String productName, Integer amount, Double currentPrice, LocalDate date) {
        this.id = id;
        this.supplierName = supplierName;
        this.retailerName = retailerName;
        this.productName = productName;
        this.amount = amount;
        this.currentPrice = currentPrice;
        this.date = date;
    }

    // Getter ve Setter metodları

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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