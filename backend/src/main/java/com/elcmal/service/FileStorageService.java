package com.elcmal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir:D:/PROJECT/Images}") String uploadDir) {
        try {
            this.fileStorageLocation = Paths.get(uploadDir)
                    .toAbsolutePath().normalize();

            // Check if directory exists, if not create it
            if (!Files.exists(this.fileStorageLocation)) {
                Files.createDirectories(this.fileStorageLocation);
            }

            // Test if directory is writable
            if (!Files.isWritable(this.fileStorageLocation)) {
                throw new RuntimeException("Upload directory is not writable: " + this.fileStorageLocation);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Could not create or access the upload directory: " + uploadDir, ex);
        }
    }

    public String storeFile(MultipartFile file, String type) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }

        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Original filename cannot be null or empty");
        }

        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = UUID.randomUUID().toString() + "." + extension;

        // Create type-specific directory
        Path targetLocation = this.fileStorageLocation.resolve(type);
        try {
            Files.createDirectories(targetLocation);
        } catch (IOException e) {
            throw new IOException("Could not create directory: " + targetLocation, e);
        }

        // Store the file
        Path filePath = targetLocation.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), filePath);
            return fileName;
        } catch (IOException e) {
            throw new IOException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public void deleteFile(String fileName, String type) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(type).resolve(fileName);
        Files.deleteIfExists(filePath);
    }

    public Path getFilePath(String fileName, String type) {
        System.out.println("Getting file path for: " + fileName + " of type: " + type);
        System.out.println("Base storage location: " + this.fileStorageLocation.toString());
        
        Path typeDir = this.fileStorageLocation.resolve(type);
        System.out.println("Type directory: " + typeDir.toString());
        
        Path filePath = typeDir.resolve(fileName);
        System.out.println("Full file path: " + filePath.toString());
        
        return filePath;
    }

    public List<Map<String, String>> listFiles(String type) throws IOException {
        Path typeDir = this.fileStorageLocation.resolve(type);
        List<Map<String, String>> files = new ArrayList<>();

        if (Files.exists(typeDir)) {
            try (Stream<Path> paths = Files.walk(typeDir, 1)) {
                paths.filter(path -> !path.equals(typeDir))
                        .forEach(path -> {
                            Map<String, String> fileInfo = new HashMap<>();
                            fileInfo.put("fileName", path.getFileName().toString());
                            fileInfo.put("type", type);
                            files.add(fileInfo);
                        });
            }
        }
        return files;
    }
}
