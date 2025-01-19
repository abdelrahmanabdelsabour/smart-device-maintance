package com.elcmal.service;

import com.elcmal.model.Blog;
import com.elcmal.model.Category;
import com.elcmal.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());
        category.setVendors(categoryDetails.getVendors());
        category.setDescription(categoryDetails.getDescription());
       category.setVendors(categoryDetails.getVendors());

        if (categoryDetails.getImageUrl() != null) {
            category.setImageUrl(categoryDetails.getImageUrl());  // Update image URL if provided
        }

        return categoryRepository.save(category);
    }
    
    public List<Category> bulkSaveCategories(List<Category> categories) {
        return categoryRepository.saveAll(categories);
    }
}
