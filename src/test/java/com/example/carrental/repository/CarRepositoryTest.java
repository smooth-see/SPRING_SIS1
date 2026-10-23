package com.example.carrental.repository;

import com.example.carrental.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
@Transactional
public class CarRepositoryTest {
    @Autowired
    private CarRepository carRepository;

    @Test
    void saveAndFindCar_shouldWork(){

        Car car = new Car(
                null,
                "Toyota", "Camry",
                2022, 15000.0, true
        );

        Car savedCar = carRepository.save(car);

        assertNotNull(savedCar.getId());

        Car foundCar = carRepository.findById(savedCar.getId()).orElseThrow();

        assertEquals("Toyota", foundCar.getBrand());
        assertEquals("Camry", foundCar.getModel());

    }
}
