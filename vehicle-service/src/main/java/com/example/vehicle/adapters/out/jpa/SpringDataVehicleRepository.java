package com.example.vehicle.adapters.out.jpa;


import com.example.vehicle.adapters.out.jpa.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataVehicleRepository extends JpaRepository<VehicleEntity, Long> {
    List<VehicleEntity> findByAvailableTrue();
}
