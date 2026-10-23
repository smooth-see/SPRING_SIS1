package com.example.carrental.service;
import com.example.carrental.dto.CarRequest;
import com.example.carrental.exception.CarNotFoundException;
import com.example.carrental.dto.CarResponse;
import com.example.carrental.model.Car;
import com.example.carrental.repository.CarRepository;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceTest {
    @Test
    void getById_shouldReturnCar(){
        //fake repo
        CarRepository repository = mock(CarRepository.class);

        //create service
        CarService service = new CarService(repository);

        //create test car
        Car car = new Car(
                1L,
                "Toyota", "Camry",
                2022, 15000.0, true
        );

        when(repository.findById(1L)).thenReturn(Optional.of(car));

        CarResponse response = service.getById(1L);

        assertEquals("Toyota", response.getBrand());
        assertEquals("Camry", response.getModel());
    }

    @Test
    void getById_shouldThrowException(){
        CarRepository repository = mock(CarRepository.class);

        CarService service = new CarService(repository);

        when(repository.findById(999l)).thenReturn(Optional.empty());

        assertThrows(
                CarNotFoundException.class, () -> service.getById(999L)
        );
    }

    @Test
    void create_shouldSaveCar(){
        CarRepository repository = mock(CarRepository.class);

        CarService service = new CarService(repository);

        CarRequest request = new CarRequest();
        request.setBrand("Toyota");
        request.setModel("Camry");
        request.setYear(2022);
        request.setDailyPrice(15000.0);
        request.setAvailable(true);


        Car savedCar = new Car(
                1L, "Toyota",
                "Camry", 2022,
                15000.0, true
        );

        when(repository.save(any(Car.class))).thenReturn(savedCar);

        CarResponse response = service.create(request);

        assertEquals("Toyota", response.getBrand());
        assertEquals("Camry", response.getModel());

        verify(repository).save(any(Car.class));
    }

    @Test
    void delete_shouldDeleteCar(){
        CarRepository repository = mock(CarRepository.class);

        CarService service = new CarService(repository);

        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void update_shouldUpdateCar(){
        CarRepository repository = mock(CarRepository.class);

        CarService service = new CarService(repository);

        Car oldCar = new Car(
                1L, "Toyota", "Camry",
                2022, 15000.0, true
        );

        CarRequest request = new CarRequest();

        request.setBrand("BMW");
        request.setModel("X5");
        request.setYear(2024);
        request.setDailyPrice(30000.0);
        request.setAvailable(true);

        when(repository.findById(1L)).thenReturn(Optional.of(oldCar));

        when(repository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CarResponse response = service.update(1L, request);

        assertEquals("BMW", response.getBrand());
        assertEquals("X5", response.getModel());
        assertEquals(2024, response.getYear());

        verify(repository).save(any(Car.class));
    }
}
