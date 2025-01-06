package com.example.inventorymanagementsystem.core.exceptions;

public class ValidationError {
    private String propertyName;
    private String errorMessage;

    public ValidationError(String propertyName, String errorMessage) {
        this.propertyName = propertyName;
        this.errorMessage = errorMessage;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

