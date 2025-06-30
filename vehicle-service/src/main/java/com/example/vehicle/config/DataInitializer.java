package com.example.vehicle.config;

import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initVehicles(VehicleRepositoryPort vehicleRepository) {
        return args -> {
            if (vehicleRepository.findAll().isEmpty()) {
                vehicleRepository.save(Vehicle.builder()
                        .model("Toyota Corolla")
                        .year(2021)
                        .available(true)
                        .licensePlate("1234-ABC")
                        .pricePerDay(new BigDecimal("50.00"))
                        .build());

                vehicleRepository.save(Vehicle.builder()
                        .model("Ford Focus")
                        .year(2022)
                        .available(true)
                        .licensePlate("5678-DEF")
                        .pricePerDay(new BigDecimal("45.00"))
                        .build());

                vehicleRepository.save(Vehicle.builder()
                        .model("Volkswagen Golf")
                        .year(2020)
                        .available(true)
                        .licensePlate("9012-GHI")
                        .pricePerDay(new BigDecimal("55.00"))
                        .build());

                System.out.println("Vehículos de prueba insertados");
            } else {
                System.out.println("Vehículos ya existentes, no se insertan duplicados");
            }
        };
    }
}
