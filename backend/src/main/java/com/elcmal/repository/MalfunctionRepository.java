package com.elcmal.repository;

import com.elcmal.model.Malfunction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MalfunctionRepository extends JpaRepository<Malfunction, Long> {
    List<Malfunction> findByProductId(Long productId);
//    List<Malfunction> findByOrderId(Long OrderId);

}
