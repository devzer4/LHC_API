package com.lhc.backend.services;

import com.lhc.backend.models.OrderDishesModel;
import com.lhc.backend.models.OrderModel;
import com.lhc.backend.repositories.OrderDishesRepository;
import com.lhc.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDishesRepository orderDishesRepository;

    public List<Map<String, Object>> getBiggestSales(int year, int month) {
        List<OrderDishesModel> orderDishes = orderDishesRepository.findAll();
        Map<UUID, Integer> dishSales = new HashMap<>();

        for (OrderDishesModel orderDish : orderDishes) {
            OrderModel order = orderRepository.findById(orderDish.getIdOrder()).orElseThrow();
            if (order.getCreatedAt().getYear() == year && order.getCreatedAt().getMonthValue() == month) {
                dishSales.merge(orderDish.getIdDish(), orderDish.getAmount(), Integer::sum);
            }
        }

        return dishSales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", e.getKey());
                    map.put("quantity", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getIncome(int year) {
        List<OrderModel> orders = orderRepository.findAll();
        Map<Integer, Double> monthlyIncome = new HashMap<>();

        for (OrderModel order : orders) {
            if (order.getCreatedAt().getYear() == year) {
                int month = order.getCreatedAt().getMonthValue();
                monthlyIncome.merge(month, order.getTotalPrice(), Double::sum);
            }
        }

        List<Map<String, Object>> result = new ArrayList<>(12);
        for (int i = 1; i <= 12; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", i);
            map.put("income", monthlyIncome.getOrDefault(i, 0.0));
            result.add(map);
        }

        return result;
    }

    public Map<String, List<Map<String, Object>>> getComparison(UUID dish, int firstYear, int secondYear) {
        List<OrderDishesModel> firstYearOrders = orderDishesRepository.findAllByIdDish(dish);
        List<OrderDishesModel> secondYearOrders = orderDishesRepository.findAllByIdDish(dish);

        Map<Integer, Double> firstYearIncome = calculateMonthlyIncome(firstYearOrders, firstYear);
        Map<Integer, Double> secondYearIncome = calculateMonthlyIncome(secondYearOrders, secondYear);

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("firstYear", convertToMonthlyList(firstYearIncome));
        result.put("secondYear", convertToMonthlyList(secondYearIncome));

        return result;
    }

    public List<Map<String, Object>> getHistory(UUID dish, int year) {
        List<OrderDishesModel> orders = orderDishesRepository.findAllByIdDish(dish);
        Map<Integer, Double> monthlyIncome = calculateMonthlyIncome(orders, year);

        return convertToMonthlyList(monthlyIncome);
    }

    private Map<Integer, Double> calculateMonthlyIncome(List<OrderDishesModel> orderDishes, int year) {
        Map<Integer, Double> monthlyIncome = new HashMap<>();

        for (OrderDishesModel orderDish : orderDishes) {
            OrderModel order = orderRepository.findById(orderDish.getIdOrder()).orElseThrow();
            if (order.getCreatedAt().getYear() == year) {
                int month = order.getCreatedAt().getMonthValue();
                double price = order.getTotalPrice() * (orderDish.getAmount() / (double) getTotalAmount(order.getId()));
                monthlyIncome.merge(month, price, Double::sum);
            }
        }

        return monthlyIncome;
    }

    private double getTotalAmount(UUID orderId) {
        return orderDishesRepository.findAllByIdOrder(orderId).stream()
                .mapToInt(OrderDishesModel::getAmount)
                .sum();
    }

    private List<Map<String, Object>> convertToMonthlyList(Map<Integer, Double> monthlyIncome) {
        List<Map<String, Object>> result = new ArrayList<>(12);
        for (int i = 1; i <= 12; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("month", i);
            map.put("income", monthlyIncome.getOrDefault(i, 0.0));
            result.add(map);
        }

        return result;
    }
}