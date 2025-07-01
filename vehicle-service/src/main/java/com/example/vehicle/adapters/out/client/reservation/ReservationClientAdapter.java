package com.example.vehicle.adapters.out.client.reservation;
import com.example.vehicle.ports.out.CreateReservationPort;
import com.example.vehicle.ports.out.LoadVehicleAvailabilityPort;
import com.rentacar.commons.dto.CreateReservationRequest;
import com.rentacar.commons.dto.ReservationResponse;
import com.rentacar.commons.dto.VehicleAvailabilityResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationClientAdapter implements CreateReservationPort, LoadVehicleAvailabilityPort {

    private final ReservationClient reservationClient;

    public ReservationClientAdapter(ReservationClient reservationClient) {
        this.reservationClient = reservationClient;
    }

    @Override
    public ReservationResponse createReservation(String email, String customerId, Long vehicleId,LocalDate startDate, LocalDate endDate) {
        CreateReservationRequest request = new CreateReservationRequest(email, customerId, vehicleId, startDate, endDate);
        return reservationClient.createReservation(request);
    }

    @Override
    public VehicleAvailabilityResponse getVehicleAvailability(Long vehicleId, LocalDate fromDate, LocalDate toDate) {
        return reservationClient.getVehicleAvailability(vehicleId, fromDate, toDate);
    }
}

