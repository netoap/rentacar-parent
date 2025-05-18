package com.example.vehicle.application;

import com.example.vehicle.adapters.outbound.client.dto.CustomerResponse;
import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.domain.exception.VehicleNotAvailableException;
import com.example.vehicle.domain.exception.VehicleNotFoundException;
import com.example.vehicle.ports.out.LoadCustomerPort;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class VehicleRentalService {

    private final LoadCustomerPort loadCustomerPort;
    private final VehicleRepositoryPort vehicleRepositoryPort;

    public VehicleRentalService(LoadCustomerPort loadCustomerPort, VehicleRepositoryPort vehicleRepositoryPort) {
        this.loadCustomerPort = loadCustomerPort;
        this.vehicleRepositoryPort = vehicleRepositoryPort;
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackLoadCustomer")
    public void rentVehicle(Long customerId, Long vehicleId) {
        CustomerResponse customer = loadCustomerPort.loadCustomer(customerId);
        Vehicle vehicle = vehicleRepositoryPort.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException(vehicleId);
        }


        // Mark as rented
        vehicle.setAvailable(false);
        vehicleRepositoryPort.save(vehicle);

        // TODO: Kafka or RabbitMQ - Log or publish rental event
        System.out.printf("Vehicle %d rented to customer %s (%s)%n",
                vehicleId, customer.getName(), customer.getEmail());
    }

    public void fallbackLoadCustomer(Long customerId, Long vehicleId, Throwable t) {
        throw new RuntimeException("Customer Service is unavailable, please try again later.", t);
    }
}