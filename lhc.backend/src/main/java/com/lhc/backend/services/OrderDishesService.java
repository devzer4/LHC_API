package com.lhc.backend.services;

import com.lhc.backend.models.OrderDishesModel;
import com.lhc.backend.repositories.OrderDishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderDishesService {

    @Autowired
    private OrderDishesRepository orderDishesRepository;

    public List<OrderDishesModel> findAll() {
        return orderDishesRepository.findAll();
    }

    public Optional<OrderDishesModel> findById(UUID id) {
        return orderDishesRepository.findById(id);
    }

    public OrderDishesModel save(OrderDishesModel orderDishesModel) {
        return orderDishesRepository.save(orderDishesModel);
    }

    public void deleteById(UUID id) {
        orderDishesRepository.deleteById(id);
    }

    public OrderDishesModel update(UUID id, OrderDishesModel orderDishesModel) {
        if(orderDishesRepository.existsById(id)) {
            orderDishesModel.setId(id);
            return orderDishesRepository.save(orderDishesModel);
        }
        return null;
    }
}

