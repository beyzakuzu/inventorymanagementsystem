package com.example.inventorymanagementsystem.service.abstracts;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;

import java.util.List;

public interface IRetailerService {

    ReturnModel<List<Product>> viewProducts(Long retailerId);

    ReturnModel<Product> addProduct(Long retailerId, ProductRequestDto productRequest);

    ReturnModel<Void> purchaseProducts(Long retailerId, Long productId, Integer amount);

    ReturnModel<Product> getProductById(Long productId);

    ReturnModel<Product> updateProduct(Long productId, ProductRequestDto productRequest);

    ReturnModel<Void> deleteProduct(Long productId);

    ReturnModel<Bill> generateBill(Long retailerId, Long productId, Integer amount);
}