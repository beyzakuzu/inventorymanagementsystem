package com.example.inventorymanagementsystem.model.mappers;

import com.example.inventorymanagementsystem.model.dtos.retailers.RetailerRequestDto;
import com.example.inventorymanagementsystem.model.dtos.retailers.RetailerResponseDto;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class RetailerMapper {

    public static Retailer toRetailer(RetailerRequestDto retailerRequestDto, Supplier supplier) {
        Retailer retailer = new Retailer();
        retailer.setName(retailerRequestDto.getName());
        retailer.setPhoneNumber(retailerRequestDto.getPhoneNumber());
        retailer.setEmailAddress(retailerRequestDto.getEmailAddress());
        retailer.setPassword(retailerRequestDto.getPassword());
        retailer.setPhoto(retailerRequestDto.getPhoto());
        retailer.setSupplier(supplier);
        return retailer;
    }

    public static RetailerResponseDto toRetailerResponseDto(Retailer retailer) {
        return new RetailerResponseDto(
                retailer.getId(),
                retailer.getName(),
                retailer.getPhoneNumber(),
                retailer.getEmailAddress(),
                retailer.getPhoto(),
                retailer.getSupplier().getName()
        );
    }
}