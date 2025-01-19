package com.elcmal.repository;

import com.elcmal.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    List<Vendor> findByCategoryId(Long categoryId);
}
