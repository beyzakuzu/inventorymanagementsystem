package com.example.inventorymanagementsystem.model.dtos.products;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {

    private Long id;

    @JsonProperty("product_name")
    private String name;

    private Integer stockQuantity;
    private Double price;
    private Double discount;

    @JsonProperty("supplier")
    private String supplierName;

    @JsonProperty("retailer")
    private String retailerName;

    public ProductResponseDto(Long id, String name, Integer stockQuantity, Double price, Double discount, String supplierName, String retailerName) {
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.discount = discount;
        this.supplierName = supplierName;
        this.retailerName = retailerName;
    }
}