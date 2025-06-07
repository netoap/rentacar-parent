package com.example.vehicle.ports.in;

import java.time.LocalDate;

public interface RentVehicleUseCase {
    void rentVehicle(Long customerId, Long vehicleId, LocalDate startDate, LocalDate endDate);

}
