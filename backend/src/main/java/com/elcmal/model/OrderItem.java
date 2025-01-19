package com.elcmal.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_Item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//@Column(name = "category_id")
//private Long categoryId ;
////vendor_id
//@Column(name = "vendor_id")
//private Long vendorId ;
//
//    @Column(name = "product_id")
//    private Long productId ;

    @Column(name = "malfunction_id")
    private Long malFunctionId ;

    @Column(name = "order_id")
    private Long orderId ;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "category_id", referencedColumnName = "id" ,  insertable = false, updatable = false )
//    private Category category;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "vendor_id" ,  insertable = false, updatable = false)
//    private Vendor vendor;
//
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id" ,  insertable = false, updatable = false)
//    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "malfunction_id",  insertable = false, updatable = false)
    private Malfunction malfunction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id",  insertable = false, updatable = false)
    private Order order;


}
