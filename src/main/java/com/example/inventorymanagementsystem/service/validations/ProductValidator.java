package com.example.inventorymanagementsystem.service.validations;

import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductRequestDto productRequest = (ProductRequestDto) target;

        if (productRequest.getName() == null || productRequest.getName().isEmpty()) {
            errors.rejectValue("name", "name.empty", "Ürün adı boş olamaz");
        }

        if (productRequest.getStockQuantity() == null || productRequest.getStockQuantity() <= 0) {
            errors.rejectValue("stockQuantity", "stockQuantity.negative", "Stok miktarı pozitif olmalıdır");
        }

        if (productRequest.getPrice() == null || productRequest.getPrice() <= 0) {
            errors.rejectValue("price", "price.negative", "Fiyat pozitif olmalıdır");
        }

        if (productRequest.getDiscount() == null || productRequest.getDiscount() < 0) {
            errors.rejectValue("discount", "discount.negative", "İndirim negatif olamaz");
        }
    }
}
