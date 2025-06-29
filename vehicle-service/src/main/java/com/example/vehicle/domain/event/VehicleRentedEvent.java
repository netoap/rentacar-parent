package com.example.vehicle.domain.event;


public class VehicleRentedEvent {
    private final Long vehicleId;
    private final String customerId;

    public VehicleRentedEvent(Long vehicleId, String customerId) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getCustomerId() {
        return customerId;
    }
}

