package com.example.vehicle.domain.event;


public class VehicleRentedEvent {
    private final Long vehicleId;
    private final Long customerId;

    public VehicleRentedEvent(Long vehicleId, Long customerId) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Long getCustomerId() {
        return customerId;
    }
}

