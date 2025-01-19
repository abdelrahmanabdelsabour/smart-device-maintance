package com.elcmal.controller;

import com.elcmal.model.Category;
import com.elcmal.model.Product;
import com.elcmal.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3001")
public class ProductController {

    @Autowired
    private ProductService productService;

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
            Product product = productService.getProductById(id).orElseThrow(() -> new RuntimeException("product not found"));
            product.setImageUrl(imageUrl);  // Store the full URL
            productService.updateProduct(id, product);  // Update the blog with the new image URL

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }


    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Product> getProductsByVendor(@PathVariable Long vendorId) {
        return productService.getProductsByVendor(vendorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.saveProduct(product);
//    }
@PostMapping
public ResponseEntity<Product> createProduct(@RequestBody Product product) {
//    try {
//        // Save the Base64 image to a file if `imageUrl` contains data
//        if (product.getImageUrl() != null && product.getImageUrl().startsWith("data:image")) {
//            // Extract Base64 data
//            String base64Image = product.getImageUrl().split(",")[1];
//            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Image);
//
//            // Define a unique file name
//            String fileName = System.currentTimeMillis() + ".png";
//            Path filePath = Paths.get("uploads/products/", fileName);
//
//
//            // Create directories if not exists
//            Files.createDirectories(filePath.getParent());
//
//            // Write the image bytes to a file
//            Files.write(filePath, decodedBytes);
//
//            // Set the new file URL to `imageUrl`
//            String imageUrl = "http://localhost:8080/uploads/products/" + fileName;
//            product.setImageUrl(imageUrl);
//        }
    try {
        // Save the Base64 image to a file if `imageUrl` contains data
        if (product.getImageUrl() != null && product.getImageUrl().startsWith("data:image")) {
            String base64Image = product.getImageUrl().split(",")[1]; // Extract Base64 data
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
            product.setImageUrl(imageUrl);
        }

        // Save the product to the database
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.getProductById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return ResponseEntity.ok(productService.saveProduct(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
