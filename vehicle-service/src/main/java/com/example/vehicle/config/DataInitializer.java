package com.example.vehicle.config;

import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initVehicles(VehicleRepositoryPort vehicleRepository) {
        return args -> {
            if (vehicleRepository.findAll().isEmpty()) {
                vehicleRepository.save(new Vehicle("Toyota Corolla", 2021, true));
                vehicleRepository.save(new Vehicle("Ford Focus", 2022, true));
                vehicleRepository.save(new Vehicle("Volkswagen Golf", 2020, true));
                System.out.println("Vehículos de prueba insertados");
            } else {
                System.out.println("Vehículos ya existentes, no se insertan duplicados");
            }
        };
    }
}
