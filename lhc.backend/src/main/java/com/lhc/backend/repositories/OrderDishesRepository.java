package com.lhc.backend.repositories;

import com.lhc.backend.models.OrderDishesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDishesRepository extends JpaRepository<OrderDishesModel, UUID> {
    List<OrderDishesModel> findAllByIdDish(UUID idDish);
    List<OrderDishesModel> findAllByIdOrder(UUID idOrder);
}
