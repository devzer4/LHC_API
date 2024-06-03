package com.lhc.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lhc.backend.models.OrderDishesModel;
import com.lhc.backend.services.OrderDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-dishes")
public class OrderDishesController {

    @Autowired
    private OrderDishesService orderDishesService;

    @GetMapping
    public ResponseEntity<List<OrderDishesModel>> getAll() {
        return ResponseEntity.ok(orderDishesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDishesModel> getById(@PathVariable UUID id) {
        Optional<OrderDishesModel> orderDishesModel = orderDishesService.findById(id);
        return orderDishesModel.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDishesModel> create(@RequestBody OrderDishesModel orderDishesModel) {
        return ResponseEntity.ok(orderDishesService.save(orderDishesModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDishesModel> update(@PathVariable UUID id, @RequestBody OrderDishesModel orderDishesModel) {
        OrderDishesModel updatedOrderDishesModel = orderDishesService.update(id, orderDishesModel);
        if (updatedOrderDishesModel != null) {
            return ResponseEntity.ok(updatedOrderDishesModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        orderDishesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}