package com.example.vehicle.domain.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long vehicleId) {
        super("Vehicle with ID " + vehicleId + " was not found.");
    }
}
