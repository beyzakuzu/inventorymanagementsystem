package com.example.inventorymanagementsystem.controller;

import com.example.inventorymanagementsystem.model.entities.AuthenticationResponse;
import com.example.inventorymanagementsystem.model.entities.User;
import com.example.inventorymanagementsystem.service.concretes.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/api/auth/*")
public class AuthenticationServlet extends HttpServlet {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationServlet(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/register")) {
            handleRegister(req, resp);
        } else if (pathInfo != null && pathInfo.equals("/login")) {
            handleLogin(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Invalid endpoint");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User request = objectMapper.readValue(req.getInputStream(), User.class);

        AuthenticationResponse response = authService.register(request);

        resp.setContentType("application/json");
        String jsonResponse = objectMapper.writeValueAsString(response);
        resp.getWriter().write(jsonResponse);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User request = objectMapper.readValue(req.getInputStream(), User.class);

        AuthenticationResponse response = authService.authenticate(request);

        resp.setContentType("application/json");
        String jsonResponse = objectMapper.writeValueAsString(response);
        resp.getWriter().write(jsonResponse);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
