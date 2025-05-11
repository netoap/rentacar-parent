package com.example.car.domain;

public class Car {
    private Long id;
    private String model;
    private int year;
    private boolean available;

    public Car(Long id, String model, int year, boolean available) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public Car(String model, int year, boolean available) {
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }
}
