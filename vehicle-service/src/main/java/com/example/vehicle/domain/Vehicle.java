package com.example.vehicle.domain;

public class Vehicle {
    private Long id;
    private String model;
    private int year;
    private boolean available;

    public Vehicle(Long id, String model, int year, boolean available) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public Vehicle(String model, int year, boolean available) {
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

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
