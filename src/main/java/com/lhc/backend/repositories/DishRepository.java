package com.lhc.backend.repositories;

import com.lhc.backend.models.DishModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DishRepository extends JpaRepository<DishModel, UUID> {
}
