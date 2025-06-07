package com.example.vehicle.application;

import com.example.vehicle.ports.in.GetVehicleAvailabilityUseCase;
import com.example.vehicle.ports.out.LoadVehicleAvailabilityPort;
import com.rentacar.commons.dto.VehicleAvailabilityResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GetVehicleAvailabilityService implements GetVehicleAvailabilityUseCase {

    private final LoadVehicleAvailabilityPort loadAvailabilityPort;

    public GetVehicleAvailabilityService(LoadVehicleAvailabilityPort loadAvailabilityPort) {
        this.loadAvailabilityPort = loadAvailabilityPort;
    }

    @Override
    public VehicleAvailabilityResponse getVehicleAvailability(Long vehicleId, LocalDate fromDate, LocalDate toDate) {
        return loadAvailabilityPort.getVehicleAvailability(vehicleId, fromDate, toDate);
    }
}

