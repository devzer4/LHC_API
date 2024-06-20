package com.lhc.backend.common.DTO.dish;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDishResponseDTO {
    private UUID idOrder;
    private UUID idClient;
    private double totalPrice;
    private String status;
    private int tableNumber;
    private List<OrderDishDetailDTO> orderDishDetailDTOS;
}
