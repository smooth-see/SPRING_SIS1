package com.example.carrental.service;

import com.example.carrental.domain.CarDomain;
import com.example.carrental.dto.CarRequest;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.mapper.CarMapper;
import com.example.carrental.model.Car;
import com.example.carrental.exception.CarNotFoundException;
import com.example.carrental.repository.CarRepository;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Service
public class CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper =  new CarMapper();

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }


    @CacheEvict(cacheNames = "carPages", allEntries = true)
    public CarResponse create(CarRequest request) {
        CarDomain domain = carMapper.toDomain(request);
        Car car = carMapper.toEntity(domain);

        Car savedCar = carRepository.save(car);

        return carMapper.toResponce(carMapper.toDomain(savedCar));
    }


    @Cacheable("carPages")
    public Page<CarResponse> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Car> carsPage = carRepository.findAll(pageable);

        return carsPage.map(car -> carMapper.toResponce(carMapper.toDomain(car)));
    }


    public CarResponse getById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));

        return carMapper.toResponce(carMapper.toDomain(car));
    }


    @CacheEvict(cacheNames = "carPages", allEntries = true)
    public CarResponse update(Long id, CarRequest request) {
        Car car = carRepository.findById(id).orElseThrow(() -> new CarNotFoundException(id));
        CarDomain domain = carMapper.toDomain(car);

        domain.setBrand(request.getBrand());
        domain.setModel(request.getModel());
        domain.setYear(request.getYear());
        domain.setDailyPrice(request.getDailyPrice());
        domain.setAvailable(request.getAvailable());

        Car updateCar = carMapper.toEntity(domain);

        Car savedCar = carRepository.save(updateCar);

        return carMapper.toResponce(carMapper.toDomain(savedCar));
    }

    @CacheEvict(cacheNames = "carPages", allEntries = true)
    public void delete(Long id) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }

        carRepository.deleteById(id);
    }
}