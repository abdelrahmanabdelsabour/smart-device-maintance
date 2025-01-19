package com.elcmal.service;

import com.elcmal.model.Category;
import com.elcmal.model.OrderItem;
import com.elcmal.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
   public  List<OrderItem> findByOrderId(Long orderId){

       return orderItemRepository.findByOrderId(orderId);
   }
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }




}
