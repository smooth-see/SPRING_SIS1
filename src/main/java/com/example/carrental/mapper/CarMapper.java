package com.example.carrental.mapper;

import com.example.carrental.domain.CarDomain;
import com.example.carrental.dto.CarRequest;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.model.Car;

public class CarMapper {
    public CarDomain toDomain(CarRequest request){
        return new CarDomain(
                null,
                request.getBrand(), request.getModel(),
                request.getYear(), request.getDailyPrice(),
                request.getAvailable()
        );
    }

    public CarDomain toDomain(Car car){
        return new CarDomain(
                car.getId(), car.getBrand(),
                car.getModel(), car.getYear(),
                car.getDailyPrice(), car.getAvailable()
        );
    }

    public Car toEntity(CarDomain domain){
        return new Car(
                domain.getId(), domain.getBrand(),
                domain.getModel(), domain.getYear(),
                domain.getDailyPrice(), domain.getAvailable()
        );
    }

    public CarResponse toResponce(CarDomain domain){
        return new CarResponse(
                domain.getId(), domain.getBrand(),
                domain.getModel(), domain.getYear(),
                domain.getDailyPrice(), domain.getAvailable()
        );
    }
}



