package com.example.carrental.dto;

public class CarResponse {
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private Double dailyPrice;
    private Boolean available;

    public CarResponse(Long id, String brand, String model, Integer year, Double dailyPrice, Boolean available){
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
    public String getModel(){
        return model;
    }
    public Integer getYear() {
        return year;
    }
    public Double getDailyPrice() {return dailyPrice;}
    public Boolean getAvailable() {return available;}
}
