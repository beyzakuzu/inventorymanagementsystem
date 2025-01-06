package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.products.ProductRequestDto;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.service.abstracts.IProductService;
import com.example.inventorymanagementsystem.service.abstracts.IRetailerService;
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

@WebServlet("/api/retailers/*")
public class RetailerServlet extends HttpServlet {

    private final IRetailerService retailerService;
    private final ObjectMapper objectMapper;

    @Autowired
    public RetailerServlet(IRetailerService retailerService, ObjectMapper objectMapper) {
        this.retailerService = retailerService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                Long retailerId = Long.parseLong(req.getParameter("retailerId"));
                ReturnModel<List<Product>> result = retailerService.viewProducts(retailerId);
                sendResponse(resp, result);
            } else {
                Long id = parseIdFromPath(pathInfo);
                ReturnModel<Product> result = retailerService.getProductById(id);
                sendResponse(resp, result);
            }
        } catch (Exception e) {
            sendErrorResponse(resp, "An error occurred while processing the request: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        try {
            if ("/purchase".equals(pathInfo)) {
                Long retailerId = Long.parseLong(req.getParameter("retailerId"));
                Long productId = Long.parseLong(req.getParameter("productId"));
                Integer amount = Integer.parseInt(req.getParameter("amount"));

                ReturnModel<Void> result = retailerService.purchaseProducts(retailerId, productId, amount);
                sendResponse(resp, result);
            } else if ("/bill".equals(pathInfo)) {
                Long retailerId = Long.parseLong(req.getParameter("retailerId"));
                Long productId = Long.parseLong(req.getParameter("productId"));
                Integer amount = Integer.parseInt(req.getParameter("amount"));

                ReturnModel<Bill> result = retailerService.generateBill(retailerId, productId, amount);
                sendResponse(resp, result);
            } else {
                ProductRequestDto productRequest = objectMapper.readValue(req.getInputStream(), ProductRequestDto.class);
                Long retailerId = Long.parseLong(req.getParameter("retailerId"));
                ReturnModel<Product> result = retailerService.addProduct(retailerId, productRequest);
                sendResponse(resp, result);
            }
        } catch (Exception e) {
            sendErrorResponse(resp, "An error occurred while processing the request: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            Long id = parseIdFromPath(pathInfo);
            ProductRequestDto productRequest = objectMapper.readValue(req.getInputStream(), ProductRequestDto.class);
            ReturnModel<Product> result = retailerService.updateProduct(id, productRequest);
            sendResponse(resp, result);
        } catch (Exception e) {
            sendErrorResponse(resp, "An error occurred while processing the request: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pathInfo = req.getPathInfo();
            Long id = parseIdFromPath(pathInfo);
            ReturnModel<Void> result = retailerService.deleteProduct(id);
            sendResponse(resp, result);
        } catch (Exception e) {
            sendErrorResponse(resp, "An error occurred while processing the request: " + e.getMessage());
        }
    }

    private void sendResponse(HttpServletResponse resp, Object data) throws IOException {
        resp.setContentType("application/json");
        String jsonResponse = objectMapper.writeValueAsString(data);
        resp.getWriter().write(jsonResponse);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void sendErrorResponse(HttpServletResponse resp, String message) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"error\": \"" + message + "\"}");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private Long parseIdFromPath(String pathInfo) {
        String[] parts = pathInfo.split("/");
        if (parts.length < 2) {
            throw new NumberFormatException("Invalid ID");
        }
        return Long.parseLong(parts[1]);
    }
}