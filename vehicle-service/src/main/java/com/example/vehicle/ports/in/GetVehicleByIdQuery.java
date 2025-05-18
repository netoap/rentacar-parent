package com.example.vehicle.ports.in;

import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;

public interface GetVehicleByIdQuery {
    VehicleResponse getById(Long id);
}
