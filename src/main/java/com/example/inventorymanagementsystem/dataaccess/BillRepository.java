package com.example.inventorymanagementsystem.dataaccess;

import com.example.inventorymanagementsystem.model.entities.Bill;
import com.example.inventorymanagementsystem.model.entities.Retailer;
import com.example.inventorymanagementsystem.model.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findBySupplier(Supplier supplier);
    List<Bill> findByRetailer(Retailer retailer);
}
