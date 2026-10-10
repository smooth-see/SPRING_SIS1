package com.example.carrental.service;

import com.example.carrental.dto.CarRequest;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.model.Car;
import org.springframework.stereotype.Service;
import com.example.carrental.exception.CarNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    private final Map<Long, Car> cars = new HashMap<>();
    private Long nextId = 1L;

    public CarResponse create(CarRequest request) {
        Long id = nextId++;

        Car car = new Car(
                id,
                request.getBrand(),
                request.getModel(),
                request.getYear(),
                request.getDailyPrice(),
                request.getAvailable()
        );

        cars.put(id, car);

        return toResponse(car);
    }

    public List<CarResponse> getAll() {
        List<CarResponse> responses = new ArrayList<>();

        for (Car car : cars.values()) {
            responses.add(toResponse(car));
        }

        return responses;
    }

    public CarResponse getById(Long id) {
        Car car = cars.get(id);

        if (car == null) {
            throw new CarNotFoundException(id);
        }

        return toResponse(car);
    }

    public CarResponse update(Long id, CarRequest request) {
        Car car = cars.get(id);

        if (car == null) {
            throw new RuntimeException("Car not found");
        }

        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setDailyPrice(request.getDailyPrice());
        car.setAvailable(request.getAvailable());

        return toResponse(car);
    }

    public void delete(Long id) {
        if (!cars.containsKey(id)) {
            throw new CarNotFoundException(id);
        }

        cars.remove(id);
    }

    private CarResponse toResponse(Car car) {
        return new CarResponse(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getYear(),
                car.getDailyPrice(),
                car.getAvailable()
        );
    }
}