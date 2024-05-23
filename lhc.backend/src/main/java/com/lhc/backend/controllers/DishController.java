package com.lhc.backend.controllers;

import com.lhc.backend.models.DishModel;
import com.lhc.backend.models.OrderModel;
import com.lhc.backend.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/dishes")
public class DishController {
    @Autowired
    DishService dishService;

    @GetMapping
    public List<DishModel> getAllDishes(){
        return dishService.getAllDishes();
    }

    @GetMapping("{id}")
    public ResponseEntity<DishModel> getDishById(@PathVariable UUID id){
        Optional<DishModel> order = dishService.getDishById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
