package com.example.vehicle.domain.exception;

public class VehicleNotAvailableException extends RuntimeException {
    public VehicleNotAvailableException(Long vehicleId) {
        super("Vehicle with ID " + vehicleId + " is not available for rental.");
    }
}
