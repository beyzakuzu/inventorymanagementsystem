package com.example.inventorymanagementsystem.core.middleware;

import com.example.inventorymanagementsystem.core.exceptions.BusinessException;
import com.example.inventorymanagementsystem.core.exceptions.NotFoundException;
import com.example.inventorymanagementsystem.core.exceptions.ValidationException;
import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ReturnModel<List<String>>> handleNotFoundException(NotFoundException ex) {
        ReturnModel<List<String>> errors = new ReturnModel<>();
        errors.setSuccess(false);
        errors.setMessage(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ReturnModel<List<String>>> handleValidationException(ValidationException ex) {
        ReturnModel<List<String>> errors = new ReturnModel<>();
        errors.setSuccess(false);
        errors.setMessage(ex.getMessage());
        errors.setData(ex.getErrors().stream()
                .map(e -> e.getPropertyName())
                .collect(Collectors.toList()));
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ReturnModel<List<String>>> handleBusinessException(BusinessException ex) {
        ReturnModel<List<String>> errors = new ReturnModel<>();
        errors.setSuccess(false);
        errors.setMessage(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReturnModel<List<String>>> handleGeneralException(Exception ex) {
        ReturnModel<List<String>> errors = new ReturnModel<>();
        errors.setSuccess(false);
        errors.setMessage(ex.getMessage());
        errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}