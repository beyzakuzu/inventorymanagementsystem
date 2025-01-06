package com.example.inventorymanagementsystem.model.mappers;

import com.example.inventorymanagementsystem.dataaccess.BillRepository;
import com.example.inventorymanagementsystem.dataaccess.ProductRepository;
import com.example.inventorymanagementsystem.dataaccess.RetailerRepository;
import com.example.inventorymanagementsystem.dataaccess.SupplierRepository;
import com.example.inventorymanagementsystem.model.dtos.bills.BillRequestDto;
import com.example.inventorymanagementsystem.model.dtos.bills.BillResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import com.example.inventorymanagementsystem.service.abstracts.IBillService;
import com.example.inventorymanagementsystem.service.rules.BillBusinessRules;
import org.springframework.stereotype.Service;

public class BillMapper {

    public static Bill toBill(BillRequestDto billRequestDto, Supplier supplier, Retailer retailer, Product product) {
        Bill bill = new Bill();
        bill.setSupplier(supplier);
        bill.setRetailer(retailer);
        bill.setProduct(product);
        bill.setAmount(billRequestDto.getAmount());
        bill.setCurrentPrice(billRequestDto.getCurrentPrice());
        bill.setDate(billRequestDto.getDate());
        return bill;
    }

    public static BillResponseDto toBillResponseDto(Bill bill) {
        return new BillResponseDto(
                bill.getId(),
                bill.getSupplier().getName(),
                bill.getRetailer().getName(),
                bill.getProduct().getName(),
                bill.getAmount(),
                bill.getCurrentPrice(),
                bill.getDate()
        );
    }
}