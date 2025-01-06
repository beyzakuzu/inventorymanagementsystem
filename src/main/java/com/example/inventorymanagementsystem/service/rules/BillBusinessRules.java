package com.example.inventorymanagementsystem.service.rules;

import com.example.inventorymanagementsystem.core.exceptions.NotFoundException;
import com.example.inventorymanagementsystem.model.entities.Bill;

public class BillBusinessRules {
    public void checkIfBillIsNull(Bill bill) {
        if (bill == null) {
            throw new NotFoundException("The requested bill was not found.");
        }
    }
}