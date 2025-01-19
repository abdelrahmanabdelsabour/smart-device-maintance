package com.elcmal.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoucherRequest {
    private String code;
    private BigDecimal discountAmount;
    private String expiryDate; // ISO-8601 format (e.g., "2024-12-31T23:59:59")
}
