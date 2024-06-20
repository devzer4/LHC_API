package com.lhc.backend.common.DTO.dish;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDishDTO {
    private UUID idDish;

    private int amount;
}
