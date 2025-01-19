package com.elcmal.service;

import com.elcmal.model.Product;
import com.elcmal.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public List<Product> getProductsByVendor(Long vendorId) {
        return productRepository.findByVendorId(vendorId);
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

        product.setName(productDetails.getName());
        product.setVendor(productDetails.getVendor());
        product.setMalfunctions(productDetails.getMalfunctions());
        product.setModelNumber(productDetails.getModelNumber());

        if (productDetails.getImageUrl() != null) {
            product.setImageUrl(productDetails.getImageUrl());  // Update image URL if provided
        }

        return productRepository.save(product);
    }
}
