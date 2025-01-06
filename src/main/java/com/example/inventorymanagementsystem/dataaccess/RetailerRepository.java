package com.example.inventorymanagementsystem.dataaccess;

import com.example.inventorymanagementsystem.model.entities.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {
}