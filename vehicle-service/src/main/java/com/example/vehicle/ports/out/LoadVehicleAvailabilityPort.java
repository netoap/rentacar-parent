package com.example.vehicle.ports.out;

import com.rentacar.commons.dto.VehicleAvailabilityResponse;

import java.time.LocalDate;

public interface LoadVehicleAvailabilityPort {
    VehicleAvailabilityResponse getVehicleAvailability(Long vehicleId, LocalDate fromDate, LocalDate toDate);
}
