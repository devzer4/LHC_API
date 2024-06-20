package com.lhc.backend.common.DTO.dish;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderDishDetailDTO {
    private UUID dishId;
    private String name;
    private double price;
    private int amount;
}