package com.example.vehicle.adapters.in.rest.dto;

public class UpdateVehicleRequest {
    private String model;
    private int year;
    private boolean available;

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
