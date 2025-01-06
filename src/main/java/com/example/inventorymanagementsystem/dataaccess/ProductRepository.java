package com.example.inventorymanagementsystem.dataaccess;

import com.example.inventorymanagementsystem.model.entities.Product;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);


    List<Product> findByRetailer(Retailer retailer);


    List<Product> findBySupplier(Supplier supplier);
}