package com.example.inventorymanagementsystem.service.validations;

import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SupplierValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SupplierRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SupplierRequestDto supplierRequest = (SupplierRequestDto) target;

        // Tedarikçi adı boş olamaz
        if (supplierRequest.getName() == null || supplierRequest.getName().isEmpty()) {
            errors.rejectValue("name", "name.empty", "Tedarikçi adı boş olamaz");
        }

        // Email formatı geçerli olmalı
        if (supplierRequest.getEmailAddress() == null || !supplierRequest.getEmailAddress().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("email", "email.invalid", "Geçersiz e-posta adresi");
        }

        // Şifre boş olamaz
        if (supplierRequest.getPassword() == null || supplierRequest.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.empty", "Şifre boş olamaz");
        }

        // Şifre en az 6 karakter olmalı
        if (supplierRequest.getPassword() != null && supplierRequest.getPassword().length() < 6) {
            errors.rejectValue("password", "password.short", "Şifre en az 6 karakter uzunluğunda olmalıdır");
        }

        // Telefon numarası geçerli olmalı (isteğe bağlı)
        if (supplierRequest.getPhoneNumber() != null && !supplierRequest.getPhoneNumber().matches("^[0-9]{10}$")) {
            errors.rejectValue("phoneNumber", "phoneNumber.invalid", "Geçersiz telefon numarası");
        }
    }
}
