package com.example.inventorymanagementsystem.service.abstracts;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.entities.Product;

import java.util.List;

public interface IProductService {
    ReturnModel<ProductResponseDto> addProduct(ProductRequestDto productRequest);
    ReturnModel<ProductResponseDto> getProduct(Long id);
    ReturnModel<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequest);
    ReturnModel<Void> deleteProduct(Long id);
    ReturnModel<List<ProductResponseDto>> getAllProducts();
    ReturnModel<List<ProductResponseDto>> searchProductByName(String name);
    ReturnModel<ProductResponseDto> viewProductDetail(Long id);
}