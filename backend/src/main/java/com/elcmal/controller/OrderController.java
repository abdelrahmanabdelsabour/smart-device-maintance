package com.elcmal.controller;

import com.elcmal.model.Order;
import com.elcmal.payload.response.ShipmentResponse;
import com.elcmal.service.DHLService;
import com.elcmal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3001")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private DHLService dhlService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);

        // Trigger shipment creation through DHL
//        ShipmentResponse shipmentResponse = dhlService.createShipment(order);
//        createdOrder.setDeliveryTrackingNumber(shipmentResponse.getTrackingNumber());
//        orderService.createOrder(createdOrder);
//
//        return ResponseEntity.ok(createdOrder);
    }

//    @PutMapping("/{id}/status")
//    public ResponseEntity<Order> updateOrderStatus(
//            @PathVariable Long id,
//            @RequestParam Order.OrderStatus status) {
//        Order updatedOrder = orderService.updateOrderStatus(id, status);
//        return updatedOrder != null
//                ? ResponseEntity.ok(updatedOrder)
//                : ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }
}
