package com.example.vehicle.adapters.out.jpa.mapper;

import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.adapters.out.jpa.entity.VehicleEntity;
import com.example.vehicle.domain.Vehicle;

public class JpaVehicleMapper {

    public static VehicleEntity toEntity(Vehicle vehicle) {
        return VehicleEntity.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .available(vehicle.isAvailable())
                .licensePlate(vehicle.getLicensePlate())
                .pricePerDay(vehicle.getPricePerDay())
                .build();
    }

    public static Vehicle toDomain(VehicleEntity entity) {
        return Vehicle.builder()
                .id(entity.getId())
                .model(entity.getModel())
                .year(entity.getYear())
                .available(entity.isAvailable())
                .licensePlate(entity.getLicensePlate())
                .pricePerDay(entity.getPricePerDay())
                .build();
    }

    public static VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .available(vehicle.isAvailable())
                .licensePlate(vehicle.getLicensePlate())
                .pricePerDay(vehicle.getPricePerDay())
                .build();
    }
}
