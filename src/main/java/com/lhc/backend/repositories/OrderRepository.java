package com.lhc.backend.repositories;

import com.lhc.backend.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    @Query("SELECT o FROM OrderModel o WHERE o.idClient = :idClient AND o.status <> 'FECHADO'")
    List<OrderModel> getOrderByIdClient(@Param("idClient") UUID idClient);
}
