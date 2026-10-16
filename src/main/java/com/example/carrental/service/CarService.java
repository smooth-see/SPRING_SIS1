package com.example.carrental.service;

import com.example.carrental.dto.CarRequest;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.model.Car;
import org.springframework.stereotype.Service;
import com.example.carrental.exception.CarNotFoundException;
import com.example.carrental.repository.CarRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public CarResponse create(CarRequest request) {

        Car car = new Car(
                null,
                request.getBrand(),
                request.getModel(),
                request.getYear(),
                request.getDailyPrice(),
                request.getAvailable()
        );

        Car savedCar = carRepository.save(car);

        return toResponse(savedCar);
    }

    public Page<CarResponse> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Page<Car> carsPage = carRepository.findAll(pageable);

        return carsPage.map(this::toResponse);
    }

    public CarResponse getById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));

        return toResponse(car);
    }

    public CarResponse update(Long id, CarRequest request) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));

        car.setBrand(request.getBrand());
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setDailyPrice(request.getDailyPrice());
        car.setAvailable(request.getAvailable());

        Car updateCar = carRepository.save(car);

        return toResponse(updateCar);
    }

    public void delete(Long id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }

        carRepository.deleteById(id);
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