package com.elcmal.controller;

import com.elcmal.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "http://localhost:3001")
public class FileController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }
            
            String fileName = fileStorageService.storeFile(file, type);
            Map<String, String> response = new HashMap<>();
            response.put("fileName", fileName);
            return ResponseEntity.ok(response);
        } catch (IOException e) {

            e.printStackTrace(); // This will print the stack trace to server logs
            String[] msgpAram={};
            String msg=messageSource.getMessage("validation.couldnotuploadfile.message",msgpAram, LocaleContextHolder.getLocale());
            return ResponseEntity.badRequest().body(msg + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to server logs
            String[] msgpAram={};
            String msg=messageSource.getMessage("validation.unexpectederror.message",msgpAram, LocaleContextHolder.getLocale());
            return ResponseEntity.internalServerError().body(msg + e.getMessage());
        }
    }

    @GetMapping("/{type}/{fileName}")
    public ResponseEntity<Resource> getFile(
            @PathVariable String type,
            @PathVariable String fileName) {
        try {
            Path filePath = fileStorageService.getFilePath(fileName, type);
            
            // Log the file path being accessed
            System.out.println("Attempting to access file: " + filePath.toString());
            
            if (!Files.exists(filePath)) {
                System.out.println("File not found at path: " + filePath.toString());
                return ResponseEntity.notFound().build();
            }
            
            Resource resource = new UrlResource(filePath.toUri());
            
            // Determine content type
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            
            // Return the file with appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            
        } catch (IOException e) {
            System.out.println("Error accessing file: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list/{type}")
    public ResponseEntity<List<Map<String, String>>> listFiles(@PathVariable String type) {
        try {
            List<Map<String, String>> files = fileStorageService.listFiles(type);
            return ResponseEntity.ok(files);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{type}/{fileName}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFile(
            @PathVariable String type,
            @PathVariable String fileName) {
        try {
            fileStorageService.deleteFile(fileName, type);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            String[] msgpAram={};
            String msg=messageSource.getMessage("validation.deletefile.message",msgpAram, LocaleContextHolder.getLocale());
            return ResponseEntity.badRequest().body(msg + e.getMessage());
        }
    }
}
