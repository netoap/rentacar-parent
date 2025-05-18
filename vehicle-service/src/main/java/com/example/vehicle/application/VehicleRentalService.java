package com.example.vehicle.application;

import com.example.vehicle.adapters.outbound.client.dto.CustomerResponse;
import com.example.vehicle.domain.Vehicle;
import com.example.vehicle.domain.event.VehicleRentedEvent;
import com.example.vehicle.domain.event.VehicleReturnedEvent;
import com.example.vehicle.domain.exception.VehicleNotAvailableException;
import com.example.vehicle.domain.exception.VehicleNotFoundException;
import com.example.vehicle.ports.in.RentVehicleUseCase;
import com.example.vehicle.ports.in.ReturnVehicleUseCase;
import com.example.vehicle.ports.out.CreateReservationPort;
import com.example.vehicle.ports.out.LoadCustomerPort;
import com.example.vehicle.ports.out.VehicleRepositoryPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VehicleRentalService implements RentVehicleUseCase, ReturnVehicleUseCase {

    private final LoadCustomerPort loadCustomerPort;
    private final VehicleRepositoryPort vehicleRepositoryPort;
    private final ApplicationEventPublisher eventPublisher;
    private final CreateReservationPort createReservationPort;

    public VehicleRentalService(LoadCustomerPort loadCustomerPort, VehicleRepositoryPort vehicleRepositoryPort, ApplicationEventPublisher eventPublisher, CreateReservationPort createReservationPort) {
        this.loadCustomerPort = loadCustomerPort;
        this.vehicleRepositoryPort = vehicleRepositoryPort;
        this.eventPublisher = eventPublisher;
        this.createReservationPort = createReservationPort;
    }

    @Override
    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackLoadCustomer")
    public void rentVehicle(Long customerId, Long vehicleId) {
        CustomerResponse customer = loadCustomerPort.loadCustomer(customerId);
        Vehicle vehicle = vehicleRepositoryPort.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException(vehicleId);
        }

        vehicle.setAvailable(false);// Mark as rented
        vehicleRepositoryPort.save(vehicle);
        createReservationPort.createReservation(customerId, vehicleId);

        eventPublisher.publishEvent(new VehicleRentedEvent(vehicleId, customerId));
    }

    @Override
    public void returnVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepositoryPort.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        if (vehicle.isAvailable()) {
            throw new IllegalStateException("Vehicle is already available");
        }

        vehicle.setAvailable(true);
        vehicleRepositoryPort.save(vehicle);

        eventPublisher.publishEvent(new VehicleReturnedEvent(vehicleId));
    }

    public void fallbackLoadCustomer(Long customerId, Long vehicleId, Throwable t) {
        throw new RuntimeException("Customer Service is unavailable, please try again later.", t);
    }
}