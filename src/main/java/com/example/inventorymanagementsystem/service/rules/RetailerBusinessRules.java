package com.example.inventorymanagementsystem.service.rules;

import com.example.inventorymanagementsystem.core.exceptions.NotFoundException;
import com.example.inventorymanagementsystem.model.entities.Retailer;

public class RetailerBusinessRules {
    public void checkIfRetailerIsNull(Retailer retailer) {
        if (retailer == null) {
            throw new NotFoundException("The requested retailer was not found.");
        }
    }
}
