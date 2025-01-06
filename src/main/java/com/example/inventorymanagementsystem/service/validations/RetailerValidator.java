package com.example.inventorymanagementsystem.service.validations;

import com.example.inventorymanagementsystem.model.dtos.retailers.RetailerRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RetailerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return RetailerRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RetailerRequestDto retailerRequest = (RetailerRequestDto) target;

        if (retailerRequest.getName() == null || retailerRequest.getName().isEmpty()) {
            errors.rejectValue("name", "name.empty", "Satıcı adı boş olamaz");
        }

        if (retailerRequest.getEmailAddress() == null || !retailerRequest.getEmailAddress().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("emailAddress", "emailAddress.invalid", "Geçersiz e-posta adresi");
        }

        if (retailerRequest.getPassword() == null || retailerRequest.getPassword().isEmpty()) {
            errors.rejectValue("password", "password.empty", "Şifre boş olamaz");
        }

        if (retailerRequest.getPassword() != null && retailerRequest.getPassword().length() < 6) {
            errors.rejectValue("password", "password.short", "Şifre en az 6 karakter uzunluğunda olmalıdır");
        }

        if (retailerRequest.getPhoneNumber() != null && !retailerRequest.getPhoneNumber().matches("^[0-9]{10}$")) {
            errors.rejectValue("phoneNumber", "phoneNumber.invalid", "Geçersiz telefon numarası");
        }

        if (retailerRequest.getPhoto() != null && !retailerRequest.getPhoto().matches("^(http(s)?://.*\\.(?:png|jpg|jpeg|gif|bmp))$")) {
            errors.rejectValue("photo", "photo.invalid", "Geçersiz fotoğraf URL'si");
        }
    }
}