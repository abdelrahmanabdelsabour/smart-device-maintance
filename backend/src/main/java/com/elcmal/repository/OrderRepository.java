package com.elcmal.repository;

import com.elcmal.model.Order;
import com.elcmal.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerName(String customerName);
    List<Order> findByPhone(String phone);

}
