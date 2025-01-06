package com.example.inventorymanagementsystem.core.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<ValidationError> errors;

    public ValidationException(String message, List<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
