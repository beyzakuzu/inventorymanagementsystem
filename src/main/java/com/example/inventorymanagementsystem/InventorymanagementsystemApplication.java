package com.example.inventorymanagementsystem;

import com.example.inventorymanagementsystem.controller.*;
import com.example.inventorymanagementsystem.service.abstracts.IBillService;
import com.example.inventorymanagementsystem.service.abstracts.IProductService;
import com.example.inventorymanagementsystem.service.abstracts.IRetailerService;
import com.example.inventorymanagementsystem.service.abstracts.ISupplierService;
import com.example.inventorymanagementsystem.service.concretes.AuthenticationService;
import com.example.inventorymanagementsystem.service.concretes.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
@ServletComponentScan
@SpringBootApplication
public class InventorymanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorymanagementsystemApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean<ProductServlet> productServlet(IProductService productService, ObjectMapper objectMapper) {
		return new ServletRegistrationBean<>(new ProductServlet(productService, objectMapper), "/api/products/*");
	}

	// RetailerServlet Bean
	@Bean
	public ServletRegistrationBean<RetailerServlet> retailerServlet(IRetailerService retailerService, ObjectMapper objectMapper) {
		return new ServletRegistrationBean<>(new RetailerServlet(retailerService, objectMapper), "/api/retailers/*");
	}

	// SupplierServlet Bean
	@Bean
	public ServletRegistrationBean<SupplierServlet> supplierServlet(ISupplierService supplierService, ObjectMapper objectMapper) {
		return new ServletRegistrationBean<>(new SupplierServlet(supplierService, objectMapper), "/api/suppliers/*");
	}

	// BillServlet Bean
	@Bean
	public ServletRegistrationBean<BillServlet> billServlet(IBillService billService, ObjectMapper objectMapper) {
		return new ServletRegistrationBean<>(new BillServlet(billService, objectMapper), "/api/bills/*");
	}


	// AuthenticationServlet Bean
	@Bean
	public ServletRegistrationBean<AuthenticationServlet> authenticationServlet(AuthenticationService authService) {
		return new ServletRegistrationBean<>(new AuthenticationServlet(authService), "/api/auth/*");
	}
}

