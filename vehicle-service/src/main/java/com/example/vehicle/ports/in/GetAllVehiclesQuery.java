package com.example.vehicle.ports.in;

import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;

import java.util.List;

public interface GetAllVehiclesQuery {
    List<VehicleResponse> getAll();
}
