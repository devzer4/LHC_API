package com.lhc.backend.models;

import com.lhc.backend.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderModel {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID idClient;

    private double totalPrice;

    @Column(columnDefinition = "ENUM('PENDENTE', 'EM_PREPARO', 'PRONTO', 'ENTREGUE')")
    @Enumerated(EnumType.STRING)
    private Status status;

    private int tableNumber;

    private String observation;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
