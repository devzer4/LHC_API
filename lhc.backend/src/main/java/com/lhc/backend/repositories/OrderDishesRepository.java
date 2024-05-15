package com.lhc.backend.repositories;

import com.lhc.backend.models.OrderDishesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDishesRepository extends JpaRepository<OrderDishesModel, UUID> {
}
