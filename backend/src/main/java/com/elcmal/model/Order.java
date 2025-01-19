package com.elcmal.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
//    private static final long serialVersionUID = 1L; //assign a long value
    //implements Serializable
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    @Email
    private String email;
    @Column
    private String additionalNotes;
    @Column
    private String device ;

    @Column
    private String amount;


    @Transient
    private List<OrderItem>  items;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    private String deliveryTrackingNumber;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }


}
