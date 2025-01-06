package com.example.inventorymanagementsystem.model.entities;

public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token=token;
    }

    public String getToken(){
        return token;
    }

}