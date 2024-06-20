package com.lhc.backend.services;

import com.lhc.backend.common.DTO.dish.OrderDishDetailDTO;
import com.lhc.backend.common.DTO.dish.OrderDishResponseDTO;
import com.lhc.backend.common.DTO.dish.OrderDishesDTO;
import com.lhc.backend.models.DishModel;
import com.lhc.backend.models.OrderDishesModel;
import com.lhc.backend.models.OrderModel;
import com.lhc.backend.models.enums.Status;
import com.lhc.backend.repositories.DishRepository;
import com.lhc.backend.repositories.OrderDishesRepository;
import com.lhc.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDishesRepository orderDishesRepository;

    @Autowired
    private DishRepository dishRepository;

    public List<OrderModel> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderModel> getOrderById(UUID id) {
        return orderRepository.findById(id);
    }

    public List<OrderDishResponseDTO> getOrderByIdClient(UUID id) {
        var orders = orderRepository.getOrderByIdClient(id);

        return orders.stream().map(order -> {
            var orderDishesList = orderDishesRepository.findAllByIdOrder(order.getId());

            List<OrderDishDetailDTO> orderDishDetails = orderDishesList.stream().map(orderDishes -> {
                DishModel dish = dishRepository.findById(orderDishes.getIdDish()).orElse(null);
                if (dish != null) {
                    return new OrderDishDetailDTO(dish.getId(), dish.getName(), dish.getPrice(), orderDishes.getAmount());
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());

            OrderDishResponseDTO responseDTO = new OrderDishResponseDTO();
            responseDTO.setIdOrder(order.getId());
            responseDTO.setIdClient(order.getIdClient());
            responseDTO.setTotalPrice(order.getTotalPrice());
            responseDTO.setStatus(order.getStatus().toString());
            responseDTO.setTableNumber(order.getTableNumber());
            responseDTO.setOrderDishDetailDTOS(orderDishDetails);

            return responseDTO;
        }).toList();
    }

    public OrderDishResponseDTO createOrder(OrderDishesDTO orderDishesDTO) {
        // Criar o objeto OrderModel a partir de OrderDishesDTO
        OrderModel order = new OrderModel();
        order.setId(UUID.randomUUID());
        order.setIdClient(orderDishesDTO.getIdClient());
        order.setTotalPrice(orderDishesDTO.getTotalPrice());
        order.setStatus(orderDishesDTO.getStatus());
        order.setTableNumber(orderDishesDTO.getTableNumber());

        // Salvar o pedido
        OrderModel savedOrder = orderRepository.save(order);

        // Criar uma lista de OrderDishesModel a partir dos OrderDishDTO
        List<OrderDishesModel> orderDishesList = orderDishesDTO.getOrderDishes().stream().map(orderDishDTO -> {
            OrderDishesModel orderDishes = new OrderDishesModel();
            orderDishes.setId(UUID.randomUUID());
            orderDishes.setIdOrder(savedOrder.getId());
            orderDishes.setIdDish(orderDishDTO.getIdDish());
            orderDishes.setAmount(orderDishDTO.getAmount());
            return orderDishes;
        }).toList();

        // Salvar cada OrderDishesModel associado ao pedido
        orderDishesList.forEach(orderDishes -> {
            orderDishesRepository.save(orderDishes);
        });

        // Buscar detalhes completos das refeições para a resposta
        List<OrderDishDetailDTO> orderDishDetails = orderDishesList.stream().map(orderDishes -> {
            DishModel dish = dishRepository.findById(orderDishes.getIdDish()).orElse(null);
            if (dish != null) {
                return new OrderDishDetailDTO(dish.getId(), dish.getName(), dish.getPrice(), orderDishes.getAmount());
            } else {
                return null;
            }
        }).filter(detail -> detail != null).collect(Collectors.toList());

        // Criar o objeto de resposta OrderDishResponseDTO
        OrderDishResponseDTO responseDTO = new OrderDishResponseDTO();
        responseDTO.setIdOrder(savedOrder.getId());
        responseDTO.setIdClient(savedOrder.getIdClient());
        responseDTO.setTotalPrice(savedOrder.getTotalPrice());
        responseDTO.setStatus(savedOrder.getStatus().toString());
        responseDTO.setTableNumber(savedOrder.getTableNumber());
        responseDTO.setOrderDishDetailDTOS(orderDishDetails);

        return responseDTO;
    }

    public ResponseEntity<Void> closeOrders(List<UUID> orderIds){
        for(UUID orderId: orderIds){
            Optional<OrderModel> orderOptional = orderRepository.findById(orderId);
            if(orderOptional.isPresent()){
                OrderModel order = orderOptional.get();
                order.setStatus(Status.FECHADO);
                orderRepository.save(order);
            }
        }
        return ResponseEntity.ok().build();
    }

    public OrderModel updateOrder(UUID id, OrderModel orderDetails) {
        OrderModel order = orderRepository.findById(id).orElseThrow();
        order.setIdClient(orderDetails.getIdClient());
        order.setTotalPrice(orderDetails.getTotalPrice());
        order.setStatus(orderDetails.getStatus());
        order.setUpdatedAt(orderDetails.getUpdatedAt());
        order.setTableNumber(orderDetails.getTableNumber());
        order.setObservation(orderDetails.getObservation());
        return orderRepository.save(order);
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}