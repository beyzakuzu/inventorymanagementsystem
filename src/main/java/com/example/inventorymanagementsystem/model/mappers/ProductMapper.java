package com.example.inventorymanagementsystem.model.mappers;

import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;

public class ProductMapper {

    public static Product toProduct(ProductRequestDto productRequestDto, Supplier supplier, Retailer retailer) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setStockQuantity(productRequestDto.getStockQuantity());
        product.setPrice(productRequestDto.getPrice());
        product.setDiscount(productRequestDto.getDiscount());
        product.setSupplier(supplier);
        product.setRetailer(retailer);
        return product;
    }

    public static ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getStockQuantity(),
                product.getPrice(),
                product.getDiscount(),
                product.getSupplier() != null ? product.getSupplier().getName() : null,
                product.getRetailer() != null ? product.getRetailer().getName() : null
        );
    }



}
