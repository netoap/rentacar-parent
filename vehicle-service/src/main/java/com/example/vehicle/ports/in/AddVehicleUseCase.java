package com.example.vehicle.ports.in;

import com.example.vehicle.adapters.in.rest.dto.CreateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.domain.Vehicle;

public interface AddVehicleUseCase {
    VehicleResponse addVehicle(CreateVehicleRequest request);
}
