package com.example.reservation.ports.in;

import com.rentacar.commons.dto.VehicleAvailabilityResponse;

import java.time.LocalDate;

public interface GetVehicleAvailabilityUseCase {
    VehicleAvailabilityResponse getVehicleAvailability(Long vehicleId, LocalDate fromDate, LocalDate toDate);
}
