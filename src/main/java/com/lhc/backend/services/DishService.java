package com.lhc.backend.services;

import com.lhc.backend.models.DishModel;
import com.lhc.backend.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DishService {
    @Autowired
    DishRepository dishRepository;

    public List<DishModel> getAllDishes(){
        return dishRepository.findAll();
    }

    public Optional<DishModel> getDishById(UUID id){
        return dishRepository.findById(id);
    }

    public DishModel createDish(DishModel order) {
        order.setId(UUID.randomUUID());
        return dishRepository.save(order);
    }

    public DishModel updateDish(UUID id, DishModel dishDetails) {
       DishModel dish = dishRepository.findById(id).orElseThrow();
        dish.setName(dishDetails.getName());
        dish.setPrice(dishDetails.getPrice());
        dish.setUpdatedAt(dishDetails.getUpdatedAt());
        dish.setDescription(dishDetails.getDescription());
        dish.setImageUrl(dishDetails.getImageUrl());
        return dishRepository.save(dish);
    }

    public void deleteOrder(UUID id) {
        dishRepository.deleteById(id);
    }
}
