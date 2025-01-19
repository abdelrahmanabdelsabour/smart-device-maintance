package com.elcmal.controller;

import com.elcmal.model.*;
import com.elcmal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3001")
public class AdminController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private VendorService vendorService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private MalfunctionService malfunctionService;
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalCategories", categoryService.getAllCategories().size());
        stats.put("totalVendors", vendorService.getAllVendors().size());
        stats.put("totalProducts", productService.getAllProducts().size());
        stats.put("totalOrders", orderService.getAllOrders().size());
        
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/categories/bulk")
    public ResponseEntity<?> bulkCreateCategories(@RequestBody List<Category> categories) {
        List<Category> savedCategories = new ArrayList<>();
        for (Category category : categories) {
            savedCategories.add(categoryService.saveCategory(category));
        }
        return ResponseEntity.ok(savedCategories);
    }

    @PostMapping("/vendors/bulk")
    public ResponseEntity<?> bulkCreateVendors(@RequestBody List<Vendor> vendors) {
        List<Vendor> savedVendors = new ArrayList<>();
        for (Vendor vendor : vendors) {
            savedVendors.add(vendorService.saveVendor(vendor));
        }
        return ResponseEntity.ok(savedVendors);
    }

    @PostMapping("/products/bulk")
    public ResponseEntity<?> bulkCreateProducts(@RequestBody List<Product> products) {
        List<Product> savedProducts = new ArrayList<>();
        for (Product product : products) {
            savedProducts.add(productService.saveProduct(product));
        }
        return ResponseEntity.ok(savedProducts);
    }

    @PostMapping("/malfunctions/bulk")
    public ResponseEntity<?> bulkCreateMalfunctions(@RequestBody List<Malfunction> malfunctions) {
        List<Malfunction> savedMalfunctions = new ArrayList<>();
        for (Malfunction malfunction : malfunctions) {
            savedMalfunctions.add(malfunctionService.saveMalfunction(malfunction));
        }
        return ResponseEntity.ok(savedMalfunctions);
    }
}
