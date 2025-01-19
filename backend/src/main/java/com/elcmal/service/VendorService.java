package com.elcmal.service;

import com.elcmal.model.Vendor;
import com.elcmal.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    
    @Autowired
    private VendorRepository vendorRepository;
    
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
    
    public List<Vendor> getVendorsByCategory(Long categoryId) {
        return vendorRepository.findByCategoryId(categoryId);
    }
    
    public Optional<Vendor> getVendorById(Long id) {
        return vendorRepository.findById(id);
    }
    
    public Vendor saveVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
    
    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }
    
    public List<Vendor> bulkSaveVendors(List<Vendor> vendors) {
        return vendorRepository.saveAll(vendors);
    }
    
    public Vendor updateVendor(Long id, Vendor vendor) {
        return vendorRepository.findById(id)
                .map(existingVendor -> {
                    existingVendor.setName(vendor.getName());
                    existingVendor.setContactInfo(vendor.getContactInfo());
                    if (vendor.getImageUrl() != null) {
                        vendor.setImageUrl(vendor.getImageUrl());  // Update image URL if provided
                    }

                    if (vendor.getCategory() != null && vendor.getCategory().getId() != null) {
                        existingVendor.setCategory(vendor.getCategory());
                    }
                    return vendorRepository.save(existingVendor);
                })
                .orElseThrow(() -> new RuntimeException("Vendor not found with id: " + id));
    }
}
