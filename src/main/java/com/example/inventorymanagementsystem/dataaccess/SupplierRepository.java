package com.example.inventorymanagementsystem.dataaccess;

import com.example.inventorymanagementsystem.model.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}