package com.example.inventorymanagementsystem.core.responses;

import com.example.inventorymanagementsystem.model.dtos.bills.BillResponseDto;

import java.util.List;

public class ReturnModel<T> {

    private T data;
    private boolean success;
    private String message;
    private int status;

    public ReturnModel() {
    }

    public ReturnModel(T data, boolean success, String message, int status) {
        this.data = data;
        this.success = success;
        this.message = message;
        this.status = status;
    }



    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
