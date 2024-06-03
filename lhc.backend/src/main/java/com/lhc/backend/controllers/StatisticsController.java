package com.lhc.backend.controllers;

import com.lhc.backend.services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/biggestsales")
    public ResponseEntity<List<Map<String, Object>>> getBiggestSales(@RequestParam int year, @RequestParam int month) {
        List<Map<String, Object>> result = statisticsService.getBiggestSales(year, month);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/income")
    public ResponseEntity<List<Map<String, Object>>> getIncome(@RequestParam int year) {
        List<Map<String, Object>> result = statisticsService.getIncome(year);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/comparison")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getComparison(@RequestParam UUID dish, @RequestParam int firstYear, @RequestParam int secondYear) {
        Map<String, List<Map<String, Object>>> result = statisticsService.getComparison(dish, firstYear, secondYear);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> getHistory(@RequestParam UUID dish, @RequestParam int year) {
        List<Map<String, Object>> result = statisticsService.getHistory(dish, year);
        return ResponseEntity.ok(result);
    }
}
