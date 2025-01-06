package com.example.inventorymanagementsystem.service.rules;

import com.example.inventorymanagementsystem.core.exceptions.NotFoundException;
import com.example.inventorymanagementsystem.model.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductBusinessRules {

    public void checkIfProductIsNull(Product product) {
        if (product == null) {
            throw new NotFoundException("The requested product was not found.");
        }
    }
}