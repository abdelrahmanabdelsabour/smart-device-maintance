//package com.elcmal.controller;
//
//import com.elcmal.model.Blog;
//import com.elcmal.service.BlogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//@RestController
//@CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
//
//@RequestMapping("/api/v1/blogs")
//public class BlogController {
//
//    @Autowired
//    private BlogService blogService;
//
//    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";
//
//    // Base URL for the server (can be configured in application.properties or application.yml)
//    @Value("${app.base-url:http://localhost:8080}")
//    private String baseUrl;
//
//    // Method to upload an image for a blog
//    @PostMapping("/{id}/upload-image")
//    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
//        // Validate that image file is present
//        if (image.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file is missing");
//        }
//
//        try {
//            // Create the upload directory if it doesn't exist
//            File directory = new File(IMAGE_UPLOAD_DIR);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            // Get the file's original name
//            String originalFilename = image.getOriginalFilename();
//            if (originalFilename == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
//            }
//
//            // Set a new file name (you can use a unique identifier or timestamp)
//            String fileName = System.currentTimeMillis() + "_" + originalFilename;
//            Path filePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
//
//            // Write the image to the server's disk
//            Files.copy(image.getInputStream(), filePath);
//
//            // Construct the full URL for the image
//            String imageUrl = baseUrl + "/" + IMAGE_UPLOAD_DIR + fileName;
//
//            // Save the full URL in the database (link it to the blog)
//            Blog blog = blogService.getBlogById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
//            blog.setImageUrl(imageUrl);  // Store the full URL
//            blogService.updateBlog(id, blog);  // Update the blog with the new image URL
//
//            return ResponseEntity.ok("Image uploaded successfully");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
//        }
//    }
//
//    // Create a new blog
//    @PostMapping
//    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
//        Blog createdBlog = blogService.createBlog(blog);
//        return ResponseEntity.ok(createdBlog);
//    }
//
//    // Get all blogs
//    @GetMapping
//    public ResponseEntity<List<Blog>> getAllBlogs() {
//        return ResponseEntity.ok(blogService.getAllBlogs());
//    }
//
//    // Get a blog by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
//        return blogService.getBlogById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Get blogs by author
//    @GetMapping("/author/{author}")
//    public ResponseEntity<List<Blog>> getBlogsByAuthor(@PathVariable String author) {
//        List<Blog> blogs = blogService.getBlogsByAuthor(author);
//        if (blogs.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(blogs);
//    }
//
//    // Delete a blog by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
//        blogService.deleteBlog(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Other methods (e.g., update, delete)...
//}
package com.elcmal.controller;

import com.elcmal.model.Blog;
import com.elcmal.model.Category;
import com.elcmal.service.BlogService;
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
//@RequestMapping("/api/blogs")
@RequestMapping("/api/v1/blogs")

@CrossOrigin(origins = "http://localhost:3001")
public class BlogController {

    @Autowired
    private BlogService blogService;

    private static final String IMAGE_UPLOAD_DIR = "uploads/images/";

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    // Get all blogs
    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    // Get a single blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new blog (with Base64 image handling)
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
//        try {
//            // Save the Base64 image to a file if `imageUrl` contains data
//            if (blog.getImageUrl() != null && blog.getImageUrl().startsWith("data:image")) {
//                String base64Image = blog.getImageUrl().split(",")[1]; // Extract Base64 data
//                byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Image);
//
//                // Define a unique file name
//                String fileName = System.currentTimeMillis() + ".png";
//                Path filePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
//
//                // Create directories if not exists
//                Files.createDirectories(filePath.getParent());
//
//                // Write the image bytes to a file
//                Files.write(filePath, decodedBytes);
//
//                // Set the new file URL to `imageUrl`
//                String imageUrl = baseUrl + "/" + IMAGE_UPLOAD_DIR + fileName;
//                blog.setImageUrl(imageUrl);
//            }
//
//            // Save the blog to the database
//            Blog savedBlog = blogService.createBlog(blog);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedBlog);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
        try {
            // Save the Base64 image to a file if `imageUrl` contains data
            if (blog.getImageUrl() != null && blog.getImageUrl().startsWith("data:image")) {
                String base64Image = blog.getImageUrl().split(",")[1]; // Extract Base64 data
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
                blog.setImageUrl(imageUrl);
            }

            // Save the category to the database
            Blog savedBlog = blogService.createBlog(blog);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBlog);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Blog> updateBlog(@PathVariable Long id, @RequestBody Blog blog) {
//        return blogService.getBlogById(id)
//                .map(existingBlog -> {
//                    blog.setId(id);
//
//                    // Update image if provided
//                    if (blog.getImageUrl() != null && blog.getImageUrl().startsWith("data:image")) {
//                        try {
//                            String base64Image = blog.getImageUrl().split(",")[1];
//                            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Image);
//
//                            String fileName = System.currentTimeMillis() + ".png";
//                            Path filePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
//
//                            Files.createDirectories(filePath.getParent());
//                            Files.write(filePath, decodedBytes);
//
//                            String imageUrl = baseUrl + "/" + IMAGE_UPLOAD_DIR + fileName;
//                            blog.setImageUrl(imageUrl);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    Blog updatedBlog = blogService.updateBlog(blog);
//                    return ResponseEntity.ok(updatedBlog);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    // Delete a blog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
        return ResponseEntity.ok().build();
    }

    // Upload an image for an existing blog
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file is missing");
        }

        try {
            File directory = new File(IMAGE_UPLOAD_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = image.getOriginalFilename();
            if (originalFilename == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file");
            }

            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path filePath = Paths.get(IMAGE_UPLOAD_DIR, fileName);
            Files.copy(image.getInputStream(), filePath);

            String imageUrl = baseUrl + "/" + IMAGE_UPLOAD_DIR + fileName;
            Blog blog = blogService.getBlogById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
            blog.setImageUrl(imageUrl);
            blogService.updateBlog(id, blog);

            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }
}
