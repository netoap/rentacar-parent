package com.example.vehicle.domain.event;

public class VehicleReturnedEvent {
    private final Long vehicleId;

    public VehicleReturnedEvent(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
}
