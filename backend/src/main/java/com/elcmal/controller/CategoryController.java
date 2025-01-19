package com.elcmal.controller;

import com.elcmal.model.Blog;
import com.elcmal.model.Category;
import com.elcmal.service.CategoryService;
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
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3001")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

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
            Category category = categoryService.getCategoryById(id).orElseThrow(() -> new RuntimeException("Category not found"));
            category.setImageUrl(imageUrl);  // Store the full URL
            categoryService.updateCategory(id, category);  // Update the blog with the new image URL

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public Category createCategory(@RequestBody Category category) {
//        return categoryService.saveCategory(category);
//    }
//
//    @PostMapping("/bulk")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<Category>> bulkCreateCategories(@RequestBody List<Category> categories) {
//        List<Category> savedCategories = categoryService.bulkSaveCategories(categories);
//        return ResponseEntity.ok(savedCategories);
//    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            // Save the Base64 image to a file if `imageUrl` contains data
            if (category.getImageUrl() != null && category.getImageUrl().startsWith("data:image")) {
                String base64Image = category.getImageUrl().split(",")[1]; // Extract Base64 data
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
                category.setImageUrl(imageUrl);
            }

            // Save the category to the database
            Category savedCategory = categoryService.saveCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.getCategoryById(id)
                .map(existingCategory -> {
                    category.setId(id);
                    return ResponseEntity.ok(categoryService.saveCategory(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
