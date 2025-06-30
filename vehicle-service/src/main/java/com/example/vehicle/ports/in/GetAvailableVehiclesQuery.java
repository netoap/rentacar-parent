package com.example.vehicle.ports.in;

import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.domain.Vehicle;

import java.time.LocalDate;
import java.util.List;

public interface GetAvailableVehiclesQuery {
    List<VehicleResponse> getAvailableVehicles();
    List<VehicleResponse> getAvailableVehicles(LocalDate start, LocalDate end);
}
