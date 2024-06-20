package com.lhc.backend.controllers;

import com.lhc.backend.common.DTO.dish.*;
import com.lhc.backend.models.OrderModel;
import com.lhc.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<List<OrderModel>> getOpenOrdersByIdClient(@PathVariable UUID idClient) {
        List<OrderModel> orders = orderService.getOrderByIdClient(idClient);
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @PostMapping("/close-orders")
    public ResponseEntity<Void> modifyAllForClosed(@RequestBody List<UUID> orderIds){
        return orderService.closeOrders(orderIds);
    }

    @PostMapping
    public ResponseEntity<OrderDishResponseDTO> createOrder(@RequestBody OrderDishesDTO orderDishesDTO) {
        OrderDishResponseDTO response = orderService.createOrder(orderDishesDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderModel> updateOrder(@PathVariable UUID id, @RequestBody OrderModel orderDetails) {
        OrderModel updatedOrder = orderService.updateOrder(id, orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}