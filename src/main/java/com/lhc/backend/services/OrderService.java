package com.lhc.backend.services;

import com.lhc.backend.models.OrderModel;
import com.lhc.backend.models.enums.Status;
import com.lhc.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderModel> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<OrderModel> getOrderByIdClient(UUID id) {
        return orderRepository.getOrderByIdClient(id);
    }

    public OrderModel createOrder(OrderModel order) {
        order.setId(UUID.randomUUID());
        return orderRepository.save(order);
    }

    public ResponseEntity<Void> closeOrders(List<UUID> orderIds){
        for(UUID orderId: orderIds){
            Optional<OrderModel> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent()){
                OrderModel order = orderOptional.get();
                order.setStatus(Status.FECHADO);
                orderRepository.save(order);
            }
        }
        return ResponseEntity.ok().build();
    }

    public OrderModel updateOrder(UUID id, OrderModel orderDetails) {
        OrderModel order = orderRepository.findById(id).orElseThrow();
        order.setIdClient(orderDetails.getIdClient());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setStatus(orderDetails.getStatus());
        order.setUpdatedAt(orderDetails.getUpdatedAt());
        order.setTableNumber(orderDetails.getTableNumber());
        order.setObservation(orderDetails.getObservation());
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}