package com.elcmal.controller;

import com.elcmal.model.OrderItem;
import com.elcmal.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderItems")
@CrossOrigin(origins = "http://localhost:3001")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
   @GetMapping("/{orderId}")
    public List<OrderItem> findByOrderId(@PathVariable Long orderId){
        return orderItemService.findByOrderId(orderId);
    }
    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok().build();
    }



}
