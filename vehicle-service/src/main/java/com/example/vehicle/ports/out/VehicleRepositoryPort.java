package com.example.vehicle.ports.out;

import com.example.vehicle.domain.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepositoryPort {
    Vehicle save(Vehicle vehicle);
    List<Vehicle> findAvailableVehicles();
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findAll();
    void deleteById(Long id);
}
