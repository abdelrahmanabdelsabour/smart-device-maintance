package com.elcmal.repository;

import com.elcmal.model.Category;
import com.elcmal.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
//    List<OrderItem> findAllOrderItems();




}
