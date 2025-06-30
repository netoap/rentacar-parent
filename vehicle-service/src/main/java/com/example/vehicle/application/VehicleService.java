package com.example.vehicle.application;

import com.example.vehicle.adapters.in.rest.dto.CreateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.UpdateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.domain.exception.VehicleNotFoundException;
import com.example.vehicle.ports.in.*;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService implements AddVehicleUseCase, GetVehicleByIdQuery,GetAllVehiclesQuery,GetAvailableVehiclesQuery, UpdateVehicleUseCase, DeleteVehicleUseCase  {

    private final VehicleRepositoryPort vehicleRepositoryPort;

    public VehicleService(VehicleRepositoryPort vehicleRepositoryPort) {
        this.vehicleRepositoryPort = vehicleRepositoryPort;
    }

    @Override
    public VehicleResponse addVehicle(CreateVehicleRequest request) {
        Vehicle vehicle = new Vehicle(request.getModel(), request.getYear(), true); // default: available = true
        return VehicleResponse.fromDomain(vehicleRepositoryPort.save(vehicle));
    }

    @Override
    public List<VehicleResponse> getAvailableVehicles() {
        return vehicleRepositoryPort.findAvailableVehicles().stream()
                .map(VehicleResponse::fromDomain)
                .toList();
    }


    @Override
    public VehicleResponse getById(Long id) {
        return vehicleRepositoryPort.findById(id)
                .map(VehicleResponse::fromDomain)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }
    @Override
    public List<VehicleResponse> getAll() {
        return vehicleRepositoryPort.findAll().stream()
                .map(VehicleResponse::fromDomain)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleResponse update(Long id, UpdateVehicleRequest request) {
        Vehicle existing = vehicleRepositoryPort.findById(id)
                .orElseThrow(()-> new VehicleNotFoundException(id));
        existing.setModel(request.getModel());
        existing.setYear(request.getYear());
        existing.setAvailable(request.isAvailable());
        return VehicleResponse.fromDomain(vehicleRepositoryPort.save(existing));
    }

    @Override
    public void delete(Long id) {
        vehicleRepositoryPort.deleteById(id);
    }
    @Override
    public void updateAvailability(Long id, boolean available) {
        Vehicle vehicle = vehicleRepositoryPort.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        vehicle.setAvailable(available);
        vehicleRepositoryPort.save(vehicle);
    }

}
