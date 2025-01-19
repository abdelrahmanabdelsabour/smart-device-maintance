package com.elcmal.controller;

import com.elcmal.model.Malfunction;
import com.elcmal.service.MalfunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/malfunctions")
@CrossOrigin(origins = "http://localhost:3001")
public class MalfunctionController {

    @Autowired
    private MalfunctionService malfunctionService;

    @GetMapping
    public List<Malfunction> getAllMalfunctions() {
        return malfunctionService.getAllMalfunctions();
    }

    @GetMapping("/product/{productId}")
    public List<Malfunction> getMalfunctionsByProduct(@PathVariable Long productId) {
        return malfunctionService.getMalfunctionsByProduct(productId);
    }
//    @GetMapping("/oredr/{oredrId}")
//    public List<Malfunction> getMalfunctionsByOrder(@PathVariable Long orderId) {
//        return malfunctionService.getMalfunctionsByOrder(orderId);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Malfunction> getMalfunctionById(@PathVariable Long id) {
        return malfunctionService.getMalfunctionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Malfunction createMalfunction(@RequestBody Malfunction malfunction) {
        return malfunctionService.saveMalfunction(malfunction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Malfunction> updateMalfunction(@PathVariable Long id, @RequestBody Malfunction malfunction) {
        return malfunctionService.getMalfunctionById(id)
                .map(existingMalfunction -> {
                    malfunction.setId(id);
                    return ResponseEntity.ok(malfunctionService.saveMalfunction(malfunction));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMalfunction(@PathVariable Long id) {
        malfunctionService.deleteMalfunction(id);
        return ResponseEntity.ok().build();
    }
}
