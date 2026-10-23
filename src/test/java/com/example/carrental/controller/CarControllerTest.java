package com.example.carrental.controller;

import com.example.carrental.dto.CarRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.verify;
import org.springframework.context.annotation.Import;
import com.example.carrental.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.carrental.dto.CarResponse;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(
        value = CarControllerTest.class,
        properties = "spring.profiles.active=test"
)
@Import(CarController.class)

public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @Test
    void setAll_shouldReturnOk() throws Exception{
        mockMvc.perform(get("/api/cars")).andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnCar() throws Exception{
        CarResponse car = new CarResponse(
                1L,"Toyota",
                "Camry", 2022,
                15000.0, true
        );

        when(carService.getById(1L)).thenReturn(car);

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));

    }

    @Test
    void delete_shouldReturnNoContent() throws Exception{
        mockMvc.perform(delete("/api/cars/1")).andExpect(status().isNoContent());

        verify(carService).delete(1L);
    }

    @Test
    void create_shouldReturnCreated() throws Exception{
        CarResponse car = new CarResponse(
                1L, "Toyota", "Camry", 2022, 15000.0, true
        );

        when(carService.create(any(CarRequest.class))).thenReturn(car);

        String json = """
                {
                    "brand": "Toyota",
                    "model": "Camry",
                    "year": 2022,
                    "dailyPrice": 15000.0,
                    "available": true
                }
                """;

        mockMvc.perform(post("/api/cars")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));

    }
}
