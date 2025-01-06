package com.example.inventorymanagementsystem.service.abstracts;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.bills.BillRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierRequestDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;

import java.util.List;

public interface ISupplierService {

    ReturnModel<List<Product>> viewProducts(Long supplierId);

    ReturnModel<SupplierResponseDto> addSupplier(SupplierRequestDto supplierRequestDto);

    ReturnModel<SupplierResponseDto> getSupplierById(Long supplierId);

    ReturnModel<SupplierResponseDto> updateSupplier(Long supplierId, SupplierRequestDto supplierRequestDto);

    ReturnModel<Void> deleteSupplier(Long supplierId);

    ReturnModel<Bill> updateBill(Long billId, Bill updatedBillDetails);

    ReturnModel<List<SupplierResponseDto>> getAllSuppliers();
}