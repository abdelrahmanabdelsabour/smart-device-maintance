package com.elcmal.controller;

import com.elcmal.model.Category;
import com.elcmal.model.Vendor;
import com.elcmal.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "http://localhost:3001")
public class VendorController {

    @Autowired
    private VendorService vendorService;


    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";

    // Base URL for the server (can be configured in application.properties or application.yml)
    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // Method to upload an image for a blog
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        // Validate that image file is present
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file is missing");
        }

        try {
            // Create the upload directory if it doesn't exist
            File directory = new File(IMAGE_UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Get the file's original name
            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
            }

            // Set a new file name (you can use a unique identifier or timestamp)
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);

            // Write the image to the server's disk
            Files.copy(image.getInputStream(), filePath);

            // Construct the full URL for the image
            String imageUrl = baseUrl + "/" + IMAGE_UPLOAD_DIR + fileName;

            // Save the full URL in the database (link it to the blog)
            Vendor vendor = vendorService.getVendorById(id).orElseThrow(() -> new RuntimeException("Category not found"));
            vendor.setImageUrl(imageUrl);  // Store the full URL
            vendorService.updateVendor(id, vendor);  // Update the blog with the new image URL

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }


    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/category/{categoryId}")
    public List<Vendor> getVendorsByCategory(@PathVariable Long categoryId) {
        return vendorService.getVendorsByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public Vendor createVendor(@RequestBody Vendor vendor) {
//        return vendorService.saveVendor(vendor);
//    }
@PostMapping
public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
//    try {
//        // Save the Base64 image to a file if `imageUrl` contains data
//        if (vendor.getImageUrl() != null && vendor.getImageUrl().startsWith("data:image")) {
//            // Extract Base64 data
//            String base64Image = vendor.getImageUrl().split(",")[1];
//            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Image);
//
//            // Define a unique file name
//            String fileName = System.currentTimeMillis() + ".png";
//            Path filePath = Paths.get("uploads/vendors/", fileName);
//
//            // Create directories if they do not exist
//            Files.createDirectories(filePath.getParent());
//
//            // Write the image bytes to a file
//            Files.write(filePath, decodedBytes);
//
//            // Set the new file URL to `imageUrl`
//            String imageUrl = "http://localhost:8080/uploads/vendors/" + fileName;
//            vendor.setImageUrl(imageUrl);
//        }
    try {
        // Save the Base64 image to a file if `imageUrl` contains data
        if (vendor.getImageUrl() != null && vendor.getImageUrl().startsWith("data:image")) {
            String base64Image = vendor.getImageUrl().split(",")[1]; // Extract Base64 data
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Image);

            // Define a unique file name
            String fileName = System.currentTimeMillis() + ".png";
            Path filePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);

            // Create directories if not exists
            Files.createDirectories(filePath.getParent());

            // Write the image bytes to a file
            Files.write(filePath, decodedBytes);

            // Set the new file URL to `imageUrl`
            String imageUrl = baseUrl + "/" + IMAGE_UPLOAD_DIR + fileName;
            vendor.setImageUrl(imageUrl);
        }

        // Save the vendor to the database
        Vendor savedVendor = vendorService.saveVendor(vendor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVendor);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Vendor>> bulkCreateVendors(@RequestBody List<Vendor> vendors) {
        List<Vendor> savedVendors = vendorService.bulkSaveVendors(vendors);
        return ResponseEntity.ok(savedVendors);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        try {
            Vendor updatedVendor = vendorService.updateVendor(id, vendor);
            return ResponseEntity.ok(updatedVendor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok().build();
    }
}
