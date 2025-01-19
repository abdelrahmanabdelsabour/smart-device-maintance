package com.elcmal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "malfunctions")
public class Malfunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    // New field to store the image URL/path
    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private BigDecimal repairPrice;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

//    //change
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
}
