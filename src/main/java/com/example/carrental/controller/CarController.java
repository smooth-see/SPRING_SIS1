package com.example.carrental.controller;

import com.example.carrental.dto.CarRequest;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public List<CarResponse> getAll(){
        return carService.getAll();
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
