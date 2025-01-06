package com.example.inventorymanagementsystem.model.dtos.suppliers;

import lombok.Getter;
import lombok.Setter;


public class SupplierResponseDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String photo;

    public SupplierResponseDto(Long id, String name, String phoneNumber, String emailAddress, String photo) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.photo = photo;
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
}