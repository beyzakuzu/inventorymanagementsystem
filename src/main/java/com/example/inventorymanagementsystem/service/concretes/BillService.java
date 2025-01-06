package com.example.inventorymanagementsystem.service.concretes;

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
import com.example.inventorymanagementsystem.model.mappers.BillMapper;
import com.example.inventorymanagementsystem.service.abstracts.IBillService;
import com.example.inventorymanagementsystem.service.rules.BillBusinessRules;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService implements IBillService {

    private final BillRepository billRepository;
    private final RetailerRepository retailerRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    private final BillBusinessRules billBusinessRules;

    public BillService(BillRepository billRepository, RetailerRepository retailerRepository,
                       SupplierRepository supplierRepository, ProductRepository productRepository,
                       BillBusinessRules billBusinessRules) {
        this.billRepository = billRepository;
        this.retailerRepository = retailerRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.billBusinessRules = billBusinessRules;
    }

    public Bill createBill(Long retailerId, Long productId, Integer amount) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < amount) {
            throw new RuntimeException("Insufficient stock for the requested product");
        }

        Double currentPrice = product.getPrice();
        Bill bill = new Bill();
        bill.setRetailer(retailer);
        bill.setProduct(product);
        bill.setAmount(amount);
        bill.setCurrentPrice(currentPrice);
        bill.setDate(LocalDate.now());

        return billRepository.save(bill);
    }

    public void deleteBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        billRepository.deleteById(billId);
    }

    public void approveBill(Long billId, boolean isApproved) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if (isApproved) {
            Product product = bill.getProduct();
            product.setStockQuantity(product.getStockQuantity() - bill.getAmount());
            productRepository.save(product);
        }

        bill.setDate(LocalDate.now());
        billRepository.save(bill);
    }

    public List<Bill> getBillsBySupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return billRepository.findBySupplier(supplier);
    }

    public List<Bill> getBillsByRetailer(Long retailerId) {
        Retailer retailer = retailerRepository.findById(retailerId)
                .orElseThrow(() -> new RuntimeException("Retailer not found"));
        return billRepository.findByRetailer(retailer);
    }

    public Bill updateBill(Long billId, Bill updatedBill) {
        Bill existingBill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        existingBill.setAmount(updatedBill.getAmount());
        existingBill.setCurrentPrice(updatedBill.getCurrentPrice());
        existingBill.setDate(updatedBill.getDate());

        return billRepository.save(existingBill);
    }

    public Bill getBillDetails(Long billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        return bill;
    }

    public ReturnModel<Bill> generateBill(Long supplierId, Long productId, Integer amount) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStockQuantity() < amount) {
            throw new RuntimeException("Insufficient stock for the requested product");
        }

        Double currentPrice = product.getPrice();
        Bill bill = new Bill();
        bill.setSupplier(supplier);
        bill.setProduct(product);
        bill.setAmount(amount);
        bill.setCurrentPrice(currentPrice);
        bill.setDate(LocalDate.now());

        Bill savedBill = billRepository.save(bill);
        return new ReturnModel<>(savedBill, true, "Bill generated successfully", HttpServletResponse.SC_OK);
    }
}