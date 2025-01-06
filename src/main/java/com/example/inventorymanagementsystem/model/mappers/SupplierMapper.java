package com.example.inventorymanagementsystem.model.mappers;

import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierRequestDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierResponseDto;
import com.example.inventorymanagementsystem.model.entities.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierMapper {

    public static Supplier toSupplier(SupplierRequestDto supplierRequestDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierRequestDto.getName());
        supplier.setPhoneNumber(supplierRequestDto.getPhoneNumber());
        supplier.setEmailAddress(supplierRequestDto.getEmailAddress());
        supplier.setPassword(supplierRequestDto.getPassword());
        supplier.setPhoto(supplierRequestDto.getPhoto());
        return supplier;
    }

    public static SupplierResponseDto toSupplierResponseDto(Supplier supplier) {
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhoneNumber(),
                supplier.getEmailAddress(),
                supplier.getPhoto()
        );
    }
}