package com.example.inventorymanagementsystem.service.concretes;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.dataaccess.BillRepository;
import com.example.inventorymanagementsystem.dataaccess.ProductRepository;
import com.example.inventorymanagementsystem.dataaccess.RetailerRepository;
import com.example.inventorymanagementsystem.dataaccess.SupplierRepository;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.retailers.RetailerRequestDto;
import com.example.inventorymanagementsystem.model.dtos.retailers.RetailerResponseDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import com.example.inventorymanagementsystem.model.mappers.ProductMapper;
import com.example.inventorymanagementsystem.model.mappers.RetailerMapper;
import com.example.inventorymanagementsystem.model.mappers.SupplierMapper;
import com.example.inventorymanagementsystem.service.abstracts.IRetailerService;
import com.example.inventorymanagementsystem.service.rules.RetailerBusinessRules;
import com.example.inventorymanagementsystem.service.validations.RetailerValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetailerService implements IRetailerService {

    private final RetailerRepository retailerRepository;
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final SupplierRepository supplierRepository;

    private final RetailerValidator retailerValidator;
    private final RetailerBusinessRules retailerBusinessRules;

    public RetailerService(RetailerRepository retailerRepository,
                           ProductRepository productRepository,
                           BillRepository billRepository,
                           SupplierRepository supplierRepository,
                           RetailerValidator retailerValidator,
                           RetailerBusinessRules retailerBusinessRules) {
        this.retailerRepository = retailerRepository;
        this.productRepository = productRepository;
        this.billRepository = billRepository;
        this.supplierRepository = supplierRepository;
        this.retailerValidator = retailerValidator;
        this.retailerBusinessRules = retailerBusinessRules;
    }

    public ReturnModel<List<Product>> viewProducts(Long retailerId) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        List<Product> products = productRepository.findByRetailer(retailer);

        return new ReturnModel<>(products, true, "Products retrieved successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<Product> addProduct(Long retailerId, ProductRequestDto productRequest) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        Supplier supplier = supplierRepository.findById(productRequest.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (!supplier.equals(retailer.getSupplier())) {
            return new ReturnModel<>(null, false, "Product does not belong to this retailer's supplier", HttpServletResponse.SC_BAD_REQUEST);
        }

        Product product = ProductMapper.toProduct(productRequest, supplier, retailer);
        Product savedProduct = productRepository.save(product);

        return new ReturnModel<>(savedProduct, true, "Product added successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<Void> purchaseProducts(Long retailerId, Long productId, Integer amount) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getSupplier().equals(retailer.getSupplier())) {
            return new ReturnModel<>(null, false, "Product does not belong to this retailer's supplier", HttpServletResponse.SC_BAD_REQUEST);
        }

        if (product.getStockQuantity() < amount) {
            return new ReturnModel<>(null, false, "Insufficient stock", HttpServletResponse.SC_BAD_REQUEST);
        }

        Bill bill = generateBill(retailerId, productId, amount).getData();
        if (processBilling(bill)) {
            product.setStockQuantity(product.getStockQuantity() - amount);
            productRepository.save(product);
            return new ReturnModel<>(null, true, "Purchase successful", HttpServletResponse.SC_OK);
        } else {
            return new ReturnModel<>(null, false, "Billing failed, stock remains unchanged", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public ReturnModel<Bill> generateBill(Long retailerId, Long productId, Integer amount) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Double currentPrice = product.getPrice();
        Bill bill = new Bill();
        bill.setRetailer(retailer);
        bill.setProduct(product);
        bill.setAmount(amount);
        bill.setCurrentPrice(currentPrice);
        bill.setDate(LocalDate.now());

        Bill savedBill = billRepository.save(bill);
        return new ReturnModel<>(savedBill, true, "Bill generated successfully", HttpServletResponse.SC_OK);
    }

    private boolean processBilling(Bill bill) {
        return bill.getAmount() > 0;
    }

    public ReturnModel<Product> getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ReturnModel<>(product, true, "Product retrieved successfully", HttpServletResponse.SC_OK);
    }


    public ReturnModel<List<SupplierResponseDto>> getAllSuppliers() {
        // Veritabanından tüm tedarikçileri al
        List<Supplier> suppliers = supplierRepository.findAll();

        // Supplier nesnelerini SupplierResponseDto'ya dönüştür
        List<SupplierResponseDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier : suppliers) {
            SupplierResponseDto dto = SupplierMapper.toSupplierResponseDto(supplier);
            supplierDtos.add(dto);
        }

        return new ReturnModel<>(supplierDtos, true, "Suppliers retrieved successfully", HttpServletResponse.SC_OK);
    }
    public ReturnModel<Product> updateProduct(Long productId, ProductRequestDto productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Supplier supplier = supplierRepository.findById(productRequest.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setSupplier(supplier);

        Product updatedProduct = productRepository.save(product);
        return new ReturnModel<>(updatedProduct, true, "Product updated successfully", HttpServletResponse.SC_OK);
    }

    public ReturnModel<Void> deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
        return new ReturnModel<>(null, true, "Product deleted successfully", HttpServletResponse.SC_OK);
    }

    // Yeni Retailer ekleme işlemi
    public ReturnModel<RetailerResponseDto> addRetailer(RetailerRequestDto retailerRequestDto) {
        Supplier supplier = supplierRepository.findById(retailerRequestDto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Retailer retailer = RetailerMapper.toRetailer(retailerRequestDto, supplier);
        Retailer savedRetailer = retailerRepository.save(retailer);

        RetailerResponseDto retailerResponseDto = RetailerMapper.toRetailerResponseDto(savedRetailer);

        return new ReturnModel<>(retailerResponseDto, true, "Retailer added successfully", HttpServletResponse.SC_OK);
    }

    // Retailer güncelleme işlemi
    public ReturnModel<RetailerResponseDto> updateRetailer(Long retailerId, RetailerRequestDto retailerRequestDto) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));

        Supplier supplier = supplierRepository.findById(retailerRequestDto.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        retailer = RetailerMapper.toRetailer(retailerRequestDto, supplier);
        retailer.setId(retailerId); // ID'yi koru
        Retailer updatedRetailer = retailerRepository.save(retailer);

        RetailerResponseDto retailerResponseDto = RetailerMapper.toRetailerResponseDto(updatedRetailer);

        return new ReturnModel<>(retailerResponseDto, true, "Retailer updated successfully", HttpServletResponse.SC_OK);
    }
}