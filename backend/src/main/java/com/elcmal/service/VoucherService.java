package com.elcmal.service;

import com.elcmal.model.Voucher;
import com.elcmal.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher createVoucher(String code, BigDecimal discountAmount, LocalDateTime expiryDate) {
        if (voucherRepository.findByCode(code).isPresent()) {
            throw new RuntimeException("Voucher with this code already exists");
        }

        Voucher voucher = new Voucher();
        voucher.setCode(code);
        voucher.setDiscountAmount(discountAmount);
        voucher.setActive(true);
        voucher.setExpiryDate(expiryDate);

        return voucherRepository.save(voucher);
    }

    // Retrieve a voucher by code
    public Optional<Voucher> getVoucherByCode(String code) {
        return voucherRepository.findByCode(code);
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }


    // Delete a voucher by code
    public void deleteVoucherByCode(String code) {
        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Voucher not found with code: " + code));
        voucherRepository.delete(voucher);
    }

}
