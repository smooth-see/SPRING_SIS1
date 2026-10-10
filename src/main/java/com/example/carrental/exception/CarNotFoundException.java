package com.example.carrental.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Long id) {
        super("Car with id " + id + " not found");
    }
}