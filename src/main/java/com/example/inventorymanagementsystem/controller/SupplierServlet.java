package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierRequestDto;
import com.example.inventorymanagementsystem.model.dtos.suppliers.SupplierResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import com.example.inventorymanagementsystem.service.abstracts.ISupplierService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/suppliers/*")
public class SupplierServlet extends HttpServlet {

    private final ISupplierService supplierService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SupplierServlet(ISupplierService supplierService, ObjectMapper objectMapper) {
        this.supplierService = supplierService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<SupplierResponseDto> suppliers = (List<SupplierResponseDto>) supplierService.getAllSuppliers();
            sendResponse(resp, suppliers);
        } else {
            Long supplierId = parseIdFromPath(pathInfo);
            SupplierResponseDto supplier = supplierService.getSupplierById(supplierId).getData();
            sendResponse(resp, supplier);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.startsWith("/add")) {
            SupplierRequestDto supplierRequestDto = objectMapper.readValue(req.getReader(), SupplierRequestDto.class);
            ReturnModel<SupplierResponseDto> result = supplierService.addSupplier(supplierRequestDto);
            sendResponse(resp, result);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Geçersiz istek");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo.startsWith("/update")) {
            Long supplierId = parseIdFromPath(pathInfo);
            SupplierRequestDto supplierRequestDto = objectMapper.readValue(req.getReader(), SupplierRequestDto.class);
            ReturnModel<SupplierResponseDto> result = supplierService.updateSupplier(supplierId, supplierRequestDto);
            sendResponse(resp, result);
        } else if (pathInfo.startsWith("/updateBill")) {
            Long billId = parseIdFromPath(pathInfo);
            Bill updatedBillDetails = objectMapper.readValue(req.getReader(), Bill.class);
            ReturnModel<Bill> result = supplierService.updateBill(billId, updatedBillDetails);
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
            Long supplierId = parseIdFromPath(pathInfo);
            ReturnModel<Void> result = supplierService.deleteSupplier(supplierId);
            sendResponse(resp, result);
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
}