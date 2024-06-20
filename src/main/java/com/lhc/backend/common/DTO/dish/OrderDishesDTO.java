package com.lhc.backend.common.DTO.dish;

import com.lhc.backend.models.enums.Status;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDishesDTO {
    private UUID idClient;
    private double totalPrice;
    private Status status;
    private int tableNumber;
    private List<OrderDishDTO> orderDishes;
}
