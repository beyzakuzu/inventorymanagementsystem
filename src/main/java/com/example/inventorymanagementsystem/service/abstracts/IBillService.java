package com.example.inventorymanagementsystem.service.abstracts;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.bills.BillRequestDto;
import com.example.inventorymanagementsystem.model.dtos.bills.BillResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;

import java.time.LocalDate;
import java.util.List;

public interface IBillService {


    Bill createBill(Long retailerId, Long productId, Integer amount);


    void approveBill(Long billId, boolean isApproved);


    List<Bill> getBillsBySupplier(Long supplierId);


    List<Bill> getBillsByRetailer(Long retailerId);


    Bill updateBill(Long billId, Bill updatedBill);


    Bill getBillDetails(Long billId);

    void deleteBill(Long billId);
}