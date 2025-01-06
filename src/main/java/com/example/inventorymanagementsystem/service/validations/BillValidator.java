package com.example.inventorymanagementsystem.service.validations;

import com.example.inventorymanagementsystem.model.dtos.bills.BillRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BillValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BillRequestDto.class.equals(clazz); // Sadece BillRequestDto sınıfını destekler
    }

    @Override
    public void validate(Object target, Errors errors) {
        BillRequestDto billRequest = (BillRequestDto) target;

        if (billRequest.getSupplierId() == null || billRequest.getSupplierId() <= 0) {
            errors.rejectValue("supplierId", "supplierId.invalid", "Tedarikçi ID geçerli bir değer olmalıdır.");
        }

        if (billRequest.getRetailerId() == null || billRequest.getRetailerId() <= 0) {
            errors.rejectValue("retailerId", "retailerId.invalid", "Satıcı ID geçerli bir değer olmalıdır.");
        }

        if (billRequest.getProductId() == null || billRequest.getProductId() <= 0) {
            errors.rejectValue("productId", "productId.invalid", "Ürün ID geçerli bir değer olmalıdır.");
        }

        if (billRequest.getAmount() == null || billRequest.getAmount() <= 0) {
            errors.rejectValue("amount", "amount.invalid", "Miktar pozitif bir değer olmalıdır.");
        }

        if (billRequest.getCurrentPrice() == null || billRequest.getCurrentPrice() <= 0) {
            errors.rejectValue("currentPrice", "currentPrice.invalid", "Fiyat pozitif bir değer olmalıdır.");
        }




    }
}
