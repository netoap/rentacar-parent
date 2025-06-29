package com.example.vehicle.ports.in;

import java.time.LocalDate;

public interface RentVehicleUseCase {
    void rentVehicle(String customerEmail, Long vehicleId, LocalDate startDate, LocalDate endDate);

}
