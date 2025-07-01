package com.example.vehicle.adapters.out.jpa;

import com.example.vehicle.adapters.out.jpa.mapper.JpaVehicleMapper;
import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaVehicleRepositoryAdapter implements VehicleRepositoryPort {

    private final SpringDataVehicleRepository repository;

    public JpaVehicleRepositoryAdapter(SpringDataVehicleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return JpaVehicleMapper.toDomain(repository.save(JpaVehicleMapper.toEntity(vehicle)));
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return repository.findById(id).map(JpaVehicleMapper::toDomain);
    }

    @Override
    public List<Vehicle> findAll() {
        return repository.findAll().stream().map(JpaVehicleMapper::toDomain).toList();
    }

    @Override
    public List<Vehicle> findAvailableVehicles() {
        return repository.findByAvailableTrue().stream().map(JpaVehicleMapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Vehicle> getAllAvailableVehicles() {
        return repository.findByAvailableTrue().stream().map(JpaVehicleMapper::toDomain).collect(Collectors.toList());
    }
}
