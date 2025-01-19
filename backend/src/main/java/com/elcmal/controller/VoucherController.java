package com.elcmal.controller;
import com.elcmal.model.Voucher;
import com.elcmal.model.VoucherRequest;
import com.elcmal.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3001")

@RequestMapping("/api/v1/vouchers")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @PostMapping
    public ResponseEntity<Voucher> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        try {
            LocalDateTime expiryDate = LocalDateTime.parse(voucherRequest.getExpiryDate());
            Voucher voucher = voucherService.createVoucher(
                    voucherRequest.getCode(),
                    voucherRequest.getDiscountAmount(),
                    expiryDate
            );
            return ResponseEntity.ok(voucher);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

@GetMapping
    public List<Voucher> getAllVoucher() {
        return voucherService.getAllVoucher();
    }


    @GetMapping("/{code}")
    public ResponseEntity<Voucher> getVoucherByCode(@PathVariable String code) {
        Optional<Voucher> voucher = voucherService.getVoucherByCode(code);
        return voucher.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete voucher by code
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteVoucherByCode(@PathVariable String code) {
        try {
            voucherService.deleteVoucherByCode(code);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
