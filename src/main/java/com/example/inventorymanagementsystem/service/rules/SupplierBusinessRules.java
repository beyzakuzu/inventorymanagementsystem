package com.example.inventorymanagementsystem.service.rules;

import com.example.inventorymanagementsystem.core.exceptions.NotFoundException;
import com.example.inventorymanagementsystem.model.entities.Supplier;

public class SupplierBusinessRules {
    public void checkIfSupplierIsNull(Supplier supplier) {
        if (supplier == null) {
            throw new NotFoundException("The requested supplier was not found.");
        }
    }
}
