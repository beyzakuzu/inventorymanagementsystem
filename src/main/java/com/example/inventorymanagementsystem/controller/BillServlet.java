package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.core.exceptions.NotFoundException;
import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.dataaccess.BillRepository;
import com.example.inventorymanagementsystem.dataaccess.ProductRepository;
import com.example.inventorymanagementsystem.dataaccess.RetailerRepository;
import com.example.inventorymanagementsystem.dataaccess.SupplierRepository;
import com.example.inventorymanagementsystem.model.dtos.bills.BillRequestDto;
import com.example.inventorymanagementsystem.model.dtos.bills.BillResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import com.example.inventorymanagementsystem.service.abstracts.IBillService;
import com.example.inventorymanagementsystem.service.concretes.BillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/api/bills/*")
public class BillServlet extends HttpServlet {

    private final IBillService billService;
    private final ObjectMapper objectMapper;

    @Autowired
    public BillServlet(IBillService billService, ObjectMapper objectMapper) {
        this.billService = billService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            Long supplierId = getSupplierIdFromRequest(req);
            List<Bill> bills = billService.getBillsBySupplier(supplierId);
            sendResponse(resp, bills);
        } else if (pathInfo.startsWith("/retailer")) {
            Long retailerId = parseIdFromPath(pathInfo);
            List<Bill> bills = billService.getBillsByRetailer(retailerId);
            sendResponse(resp, bills);
        } else if (pathInfo.startsWith("/approve")) {
            Long billId = parseIdFromPath(pathInfo);
            boolean isApproved = Boolean.parseBoolean(req.getParameter("approved"));
            billService.approveBill(billId, isApproved);
            sendResponse(resp, "Fatura onaylandı");
        } else {
            Long billId = parseIdFromPath(pathInfo);
            Bill bill = billService.getBillDetails(billId);
            sendResponse(resp, bill);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            BillRequestDto billRequestDto = objectMapper.readValue(req.getInputStream(), BillRequestDto.class);
            Bill createdBill = billService.createBill(billRequestDto.getRetailerId(), billRequestDto.getProductId(), billRequestDto.getAmount());
            sendResponse(resp, createdBill);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Geçersiz istek");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.startsWith("/update")) {
            Long billId = parseIdFromPath(pathInfo);
            Bill updatedBill = objectMapper.readValue(req.getInputStream(), Bill.class);
            Bill result = billService.updateBill(billId, updatedBill);
            sendResponse(resp, result);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Geçersiz istek");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.startsWith("/delete")) {
            Long billId = parseIdFromPath(pathInfo);
            billService.deleteBill(billId);
            sendResponse(resp, "Fatura silindi");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Geçersiz istek");
        }
    }

    private void sendResponse(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        String jsonResponse = objectMapper.writeValueAsString(data);
        resp.getWriter().write(jsonResponse);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Long parseIdFromPath(String pathInfo) {
        String[] parts = pathInfo.split("/");
        if (parts.length < 2) {
            throw new NumberFormatException("Geçersiz ID");
        }
        return Long.parseLong(parts[1]);
    }

    private Long getSupplierIdFromRequest(HttpServletRequest req) {
        String supplierIdParam = req.getParameter("supplierId");
        if (supplierIdParam != null) {
            return Long.parseLong(supplierIdParam);
        }
        throw new IllegalArgumentException("Supplier ID parametre olarak verilmelidir");
    }
}