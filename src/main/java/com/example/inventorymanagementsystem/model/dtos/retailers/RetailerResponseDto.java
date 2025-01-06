package com.example.inventorymanagementsystem.model.dtos.retailers;

import lombok.Getter;
import lombok.Setter;


public class RetailerResponseDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String photo;
    private String supplierName;

    public RetailerResponseDto(Long id, String name, String phoneNumber, String emailAddress, String photo, String supplierName) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.photo = photo;
        this.supplierName = supplierName;
    }

    // Getter ve Setter metodları

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}