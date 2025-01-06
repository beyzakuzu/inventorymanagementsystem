package com.example.inventorymanagementsystem.model.entities;

import com.example.inventorymanagementsystem.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Entity
@Setter
@Table(name = "bills")
public class Bill extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "retailer_id", nullable = false)
    private Retailer retailer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "current_price", nullable = false)
    private Double currentPrice;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // Default constructor
    public Bill() {}

    // Constructor with parameters
    public Bill(Supplier supplier, Retailer retailer, Product product, Integer amount, Double currentPrice, LocalDate date) {
        this.supplier = supplier;
        this.retailer = retailer;
        this.product = product;
        this.amount = amount;
        this.currentPrice = currentPrice;
        this.date = date;
    }

    // Getter and Setter for supplier
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    // Getter and Setter for retailer
    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    // Getter and Setter for product
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter and Setter for amount
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    // Getter and Setter for currentPrice
    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    // Getter and Setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}