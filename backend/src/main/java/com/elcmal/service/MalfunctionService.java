package com.elcmal.service;

import com.elcmal.model.Malfunction;
import com.elcmal.repository.MalfunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MalfunctionService {
    
    @Autowired
    private MalfunctionRepository malfunctionRepository;
    
    public List<Malfunction> getAllMalfunctions() {
        return malfunctionRepository.findAll();
    }
    
    public List<Malfunction> getMalfunctionsByProduct(Long productId) {
        return malfunctionRepository.findByProductId(productId);
    }
//    public List<Malfunction> getMalfunctionsByOrder(Long OrderId) {
//        return malfunctionRepository.findByOrderId(OrderId);
//    }
    
    public Optional<Malfunction> getMalfunctionById(Long id) {
        return malfunctionRepository.findById(id);
    }
    
    public Malfunction saveMalfunction(Malfunction malfunction) {
        return malfunctionRepository.save(malfunction);
    }
    
    public void deleteMalfunction(Long id) {
        malfunctionRepository.deleteById(id);
    }
}
