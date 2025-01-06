package com.example.inventorymanagementsystem.model.dtos.retailers;

import lombok.Getter;
import lombok.Setter;


public class RetailerRequestDto {

    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String password;
    private String photo;
    private Long supplierId;

    public RetailerRequestDto(String name, String phoneNumber, String emailAddress, String password, String photo, Long supplierId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
        this.photo = photo;
        this.supplierId = supplierId;
    }

    // Getter ve Setter metodları

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }
}