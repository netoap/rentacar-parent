package com.example.vehicle.application;

import com.example.vehicle.adapters.in.rest.dto.CreateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.UpdateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.domain.exception.VehicleNotFoundException;
import com.example.vehicle.ports.in.*;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.example.vehicle.adapters.out.client.ReservationClient;
@Service
public class VehicleService implements AddVehicleUseCase, GetVehicleByIdQuery,GetAllVehiclesQuery,GetAvailableVehiclesQuery, UpdateVehicleUseCase, DeleteVehicleUseCase  {

    private final VehicleRepositoryPort vehicleRepositoryPort;
    private final ReservationClient reservationClient;

    public VehicleService(VehicleRepositoryPort vehicleRepositoryPort, ReservationClient reservationClient) {
        this.vehicleRepositoryPort = vehicleRepositoryPort;
        this.reservationClient = reservationClient;
    }

    @Override
    public List<VehicleResponse> getAvailableVehicles() {
        return vehicleRepositoryPort.findAvailableVehicles().stream()
                .map(VehicleResponse::fromDomain)
                .toList();
    }

    @Override
    public void updateAvailability(Long id, boolean available) {
        Vehicle vehicle = vehicleRepositoryPort.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        vehicle.setAvailable(available);
        vehicleRepositoryPort.save(vehicle);
    }

    @Override
    public VehicleResponse addVehicle(CreateVehicleRequest request) {
        Vehicle vehicle = Vehicle.builder()
                .model(request.getModel())
                .year(request.getYear())
                .available(true)
                .licensePlate(request.getLicensePlate())
                .pricePerDay(request.getPricePerDay())
                .build();
        vehicle = vehicleRepositoryPort.save(vehicle);
        return VehicleResponse.fromDomain(vehicle);
    }



    @Override
    public List<VehicleResponse> getAvailableVehicles(LocalDate start, LocalDate end) {
        return vehicleRepositoryPort.findAll().stream()
                .filter(Vehicle::isAvailable)
                .filter(vehicle -> reservationClient.isVehicleAvailable(vehicle.getId(), start, end))
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
    public List<Vehicle> getAllAvailableVehicles() {
        return vehicleRepositoryPort.getAllAvailableVehicles();
    }

    @Override
    public VehicleResponse update(Long id, UpdateVehicleRequest request) {
        Vehicle existing = vehicleRepositoryPort.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
        existing.setModel(request.getModel());
        existing.setYear(request.getYear());
        existing.setAvailable(request.isAvailable());
        existing.setLicensePlate(request.getLicensePlate());
        existing.setPricePerDay(request.getPricePerDay());
        Vehicle vehicle = vehicleRepositoryPort.save(existing);
        return VehicleResponse.fromDomain(vehicle);
    }


    @Override
    public void delete(Long id) {
        vehicleRepositoryPort.deleteById(id);
    }


}
