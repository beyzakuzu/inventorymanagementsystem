package com.example.inventorymanagementsystem.service.concretes;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.dataaccess.ProductRepository;
import com.example.inventorymanagementsystem.dataaccess.RetailerRepository;
import com.example.inventorymanagementsystem.dataaccess.SupplierRepository;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import com.example.inventorymanagementsystem.model.mappers.ProductMapper;
import com.example.inventorymanagementsystem.service.abstracts.IProductService;
import com.example.inventorymanagementsystem.service.rules.ProductBusinessRules;
import com.example.inventorymanagementsystem.service.validations.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final RetailerRepository retailerRepository;
    private final ProductValidator productValidator;

    @Autowired
    public ProductService(ProductRepository productRepository, SupplierRepository supplierRepository,
                          RetailerRepository retailerRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.retailerRepository = retailerRepository;
        this.productValidator = productValidator;
    }

    public ReturnModel<ProductResponseDto> addProduct(ProductRequestDto productRequest) {
        Errors errors = new BeanPropertyBindingResult(productRequest, "productRequest");
        productValidator.validate(productRequest, errors);
        if (errors.hasErrors()) {
            return createErrorReturnModel(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        Supplier supplier = supplierRepository.findById(productRequest.getSupplierId())
                .orElse(null);
        Retailer retailer = retailerRepository.findById(productRequest.getRetailerId())
                .orElse(null);

        if (supplier == null || retailer == null) {
            return new ReturnModel<>(null, false, "Tedarikçi veya Satıcı bulunamadı", HttpStatus.BAD_REQUEST.value());
        }

        Product product = ProductMapper.toProduct(productRequest, supplier, retailer);
        Product savedProduct = productRepository.save(product);

        return createSuccessReturnModel(ProductMapper.toResponse(savedProduct), "Ürün başarıyla eklendi", HttpStatus.CREATED);
    }

    public ReturnModel<ProductResponseDto> getProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return createErrorReturnModel("Ürün bulunamadı", HttpStatus.NOT_FOUND);
        }

        return createSuccessReturnModel(ProductMapper.toResponse(product), "Ürün başarıyla getirildi", HttpStatus.OK);
    }

    public ReturnModel<ProductResponseDto> updateProduct(Long id, ProductRequestDto productRequest) {
        Errors errors = new BeanPropertyBindingResult(productRequest, "productRequest");
        productValidator.validate(productRequest, errors);
        if (errors.hasErrors()) {
            return createErrorReturnModel(errors.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return createErrorReturnModel("Ürün bulunamadı", HttpStatus.NOT_FOUND);
        }

        product.setName(productRequest.getName());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setPrice(productRequest.getPrice());
        product.setDiscount(productRequest.getDiscount());

        Product updatedProduct = productRepository.save(product);
        return createSuccessReturnModel(ProductMapper.toResponse(updatedProduct), "Ürün başarıyla güncellendi", HttpStatus.OK);
    }

    public ReturnModel<Void> deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return createErrorReturnModel("Ürün bulunamadı", HttpStatus.NOT_FOUND);
        }

        productRepository.deleteById(id);
        return new ReturnModel<>(null, true, "Ürün başarıyla silindi", HttpStatus.OK.value());
    }

    public ReturnModel<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> responseDtos = products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());

        return createSuccessReturnModel(responseDtos, "Ürünler başarıyla listelendi", HttpStatus.OK);
    }

    public ReturnModel<List<ProductResponseDto>> searchProductByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        if (products.isEmpty()) {
            return createErrorReturnModel("Aranan ürün bulunamadı", HttpStatus.NOT_FOUND);
        }

        List<ProductResponseDto> responseDtos = products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());

        return createSuccessReturnModel(responseDtos, "Ürünler başarıyla getirildi", HttpStatus.OK);
    }

    public ReturnModel<ProductResponseDto> viewProductDetail(Long id) {
        return getProduct(id);
    }

    private <T> ReturnModel<T> createSuccessReturnModel(T data, String message, HttpStatus status) {
        return new ReturnModel<>(data, true, message, status.value());
    }

    private <T> ReturnModel<T> createErrorReturnModel(String message, HttpStatus status) {
        return new ReturnModel<>(null, false, message, status.value());
    }
}