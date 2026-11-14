package com.example.carrental.controller;

import com.example.carrental.dto.CarPriceResponse;
import com.example.carrental.service.CarPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarPriceController {
    private final CarPriceService carPriceService;

    public CarPriceController(CarPriceService carPriceService) {
        this.carPriceService = carPriceService;
    }

    @GetMapping("/{id}/price")
    public CarPriceResponse getPrice(@PathVariable("id") Long id) {
        return carPriceService.getPrice(id);
    }
}
