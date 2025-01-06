package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.core.responses.ReturnModel;
import com.example.inventorymanagementsystem.model.dtos.products.ProductResponseDto;
import com.example.inventorymanagementsystem.service.abstracts.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/products/*")
public class ProductServlet extends HttpServlet {

    private final IProductService productService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductServlet(IProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            ReturnModel<List<ProductResponseDto>> result = productService.getAllProducts();
            sendResponse(resp, result);
        } else if (pathInfo.startsWith("/search")) {
            String name = req.getParameter("name");
            ReturnModel<List<ProductResponseDto>> result = productService.searchProductByName(name);
            sendResponse(resp, result);
        } else {
            Long id = parseIdFromPath(pathInfo);
            ReturnModel<ProductResponseDto> result = productService.viewProductDetail(id);
            sendResponse(resp, result);
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