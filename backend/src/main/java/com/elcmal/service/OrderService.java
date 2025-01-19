package com.elcmal.service;

import com.elcmal.model.Malfunction;
import com.elcmal.model.Order;
import com.elcmal.model.OrderItem;
import com.elcmal.repository.MalfunctionRepository;
import com.elcmal.repository.OrderItemRepository;
import com.elcmal.repository.OrderRepository;
import com.elcmal.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
//    private Malfunction[] incomingMalfunctions;



//    @Autowired
//    private MalfunctionRepository malfunctionRepository;

//    @Autowired
//    private VoucherRepository voucherRepository;

//    @Autowired
//    private DeliveryService deliveryService;
//    private DHLService  dhlService ;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    public List<Order> getOrdersByPhone(String phone) {
        return orderRepository.findByPhone(phone);
    }

    public Order createOrder(Order order) {
        //return orderRepository.save(order);
//        doShipmentOrder(order) ;
        Order savedOrder = null;
        savedOrder = orderRepository.save(order);
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(savedOrder.getId());
//                    orderItem.setVendorId(item.getVendor().getId());
//                    orderItem.setProductId(item.getProduct().getId());
//                    orderItem.setCategoryId(item.getCategory().getId());
                orderItem.setMalFunctionId(item.getMalfunction().getId());
                orderItemRepository.save(orderItem);
            }
        }

        return savedOrder;
    }

//    private void doShipmentOrder(Order order) {
//        // Create delivery order and get tracking number
//
//       ShipmentResponse shipment = null ;
//        String trackingNumber = null ;
//        try {
//             shipment = dhlService.createShipment(order);
//            trackingNumber = shipment.getTrackingNumber() ;
//        }catch (Exception e){
//            e.printStackTrace();
//            trackingNumber =null ;
//        }
//
//        // Set tracking number and initial status
//        order.setDeliveryTrackingNumber(trackingNumber);
//        order.setStatus(Order.OrderStatus.PENDING);
//    }





//    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
//        Optional<Order> orderOpt = orderRepository.findById(id);
//        if (orderOpt.isPresent()) {
//            Order order = orderOpt.get();
//            order.setStatus(status);
//
//            // Check delivery status if order is completed
//            if (status == Order.OrderStatus.COMPLETED && order.getDeliveryTrackingNumber() != null) {
//                String deliveryStatus = dhlService.getShipmentStatus(order.getDeliveryTrackingNumber());
//                // You can handle the delivery status as needed
//            }
//
//            return orderRepository.save(order);
//        }
//        return null;
//    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }



}

