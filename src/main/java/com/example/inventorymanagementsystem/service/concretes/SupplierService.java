package com.example.inventorymanagementsystem.service.concretes;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.dataaccess.BillRepository;
import com.example.inventorymanagementsystem.dataaccess.ProductRepository;
import com.example.inventorymanagementsystem.dataaccess.SupplierRepository;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierRequestDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import com.example.inventorymanagementsystem.model.mappers.ProductMapper;
import com.example.inventorymanagementsystem.model.mappers.SupplierMapper;
import com.example.inventorymanagementsystem.service.abstracts.ISupplierService;
import com.example.inventorymanagementsystem.service.rules.ProductBusinessRules;
import com.example.inventorymanagementsystem.service.rules.SupplierBusinessRules;
import com.example.inventorymanagementsystem.service.validations.ProductValidator;
import com.example.inventorymanagementsystem.service.validations.SupplierValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService implements ISupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;

    private final SupplierValidator supplierValidator;
    private final SupplierBusinessRules supplierBusinessRules;

    public SupplierService(SupplierRepository supplierRepository,
                           ProductRepository productRepository,
                           BillRepository billRepository,
                           SupplierValidator supplierValidator,
                           SupplierBusinessRules supplierBusinessRules) {
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.supplierValidator = supplierValidator;
        this.supplierBusinessRules = supplierBusinessRules;
    }

    public ReturnModel<List<Product>> viewProducts(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        List<Product> products = productRepository.findBySupplier(supplier);

        return new ReturnModel<>(products, true, "Products retrieved successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<SupplierResponseDto> addSupplier(SupplierRequestDto supplierRequestDto) {
        Supplier supplier = SupplierMapper.toSupplier(supplierRequestDto);

        Supplier savedSupplier = supplierRepository.save(supplier);


        SupplierResponseDto supplierResponseDto = SupplierMapper.toSupplierResponseDto(savedSupplier);
        return new ReturnModel<>(supplierResponseDto, true, "Supplier added successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<SupplierResponseDto> getSupplierById(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        SupplierResponseDto supplierResponseDto = SupplierMapper.toSupplierResponseDto(supplier);
        return new ReturnModel<>(supplierResponseDto, true, "Supplier retrieved successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<SupplierResponseDto> updateSupplier(Long supplierId, SupplierRequestDto supplierRequestDto) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setName(supplierRequestDto.getName());
        supplier.setPhoneNumber(supplierRequestDto.getPhoneNumber());
        supplier.setEmailAddress(supplierRequestDto.getEmailAddress());
        supplier.setPassword(supplierRequestDto.getPassword());
        supplier.setPhoto(supplierRequestDto.getPhoto());

        Supplier updatedSupplier = supplierRepository.save(supplier);

        SupplierResponseDto supplierResponseDto = SupplierMapper.toSupplierResponseDto(updatedSupplier);
        return new ReturnModel<>(supplierResponseDto, true, "Supplier updated successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<Void> deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplierRepository.delete(supplier);
        return new ReturnModel<>(null, true, "Supplier deleted successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<Bill> updateBill(Long billId, Bill updatedBillDetails) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));


        if (updatedBillDetails.getAmount() != null) {
            bill.setAmount(updatedBillDetails.getAmount());
        }
        if (updatedBillDetails.getCurrentPrice() != null) {
            bill.setCurrentPrice(updatedBillDetails.getCurrentPrice());
        }
        if (updatedBillDetails.getDate() != null) {
            bill.setDate(updatedBillDetails.getDate());
        }
        if (updatedBillDetails.getProduct() != null) {
            Product product = productRepository.findById(updatedBillDetails.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            bill.setProduct(product);
        }

        Bill updatedBill = billRepository.save(bill);
        return new ReturnModel<>(updatedBill, true, "Bill updated successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<List<SupplierResponseDto>> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();

        List<SupplierResponseDto> supplierResponseDtos = suppliers.stream()
                .map(SupplierMapper::toSupplierResponseDto)
                .collect(Collectors.toList());

        return new ReturnModel<>(supplierResponseDtos, true, "Suppliers retrieved successfully", HttpServletResponse.SC_OK);
    }
}