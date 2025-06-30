package com.example.vehicle.ports.in;

import com.example.vehicle.adapters.in.rest.dto.UpdateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;

public interface UpdateVehicleUseCase {
    VehicleResponse update(Long id, UpdateVehicleRequest request);
    void updateAvailability(Long id, boolean available);
}
