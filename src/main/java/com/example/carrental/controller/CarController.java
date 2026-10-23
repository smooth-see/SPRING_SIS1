package com.example.carrental.controller;

import com.example.carrental.dto.CarRequest;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService){
        this.carService = carService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse create(@Valid @RequestBody CarRequest request){
        return carService.create(request);
    }


    @GetMapping
    public Page<CarResponse> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return carService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public CarResponse getById(@PathVariable Long id){
        return carService.getById(id);
    }

    @PutMapping("/{id}")
    public CarResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CarRequest request
    ) {
        return carService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        carService.delete(id);
    }
}
