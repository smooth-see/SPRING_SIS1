package com.example.carrental.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Integer year;
    private Double dailyPrice;
    private Boolean available;

    protected Car(){
    }

    public Car(Long id, String brand, String model, Integer year,
               Double dailyPrice, Boolean available) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.dailyPrice = dailyPrice;
        this.available = available;
    }

    public Long getId() {
        return id;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public Integer getYear() {
        return year;
    }
    public Double getDailyPrice() {
        return dailyPrice;
    }
    public Boolean getAvailable() {
        return available;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setYear(Integer year) {this.year = year;}
    public void setDailyPrice(Double dailyPrice) {this.dailyPrice = dailyPrice;}
    public void setAvailable(Boolean available) {this.available = available;}
}