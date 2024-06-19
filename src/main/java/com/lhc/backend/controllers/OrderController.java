package com.lhc.backend.controllers;

import com.lhc.backend.models.OrderModel;
import com.lhc.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/specific/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable UUID id) {
        Optional<OrderModel> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    @PostMapping
    public OrderModel createOrder(@RequestBody OrderModel order) {
        return orderService.createOrder(order);
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