package com.example.vehicle.adapters.outbound.client.reservation;
import com.example.vehicle.ports.out.CreateReservationPort;
import com.rentacar.commons.dto.CreateReservationRequest;
import com.rentacar.commons.dto.ReservationResponse;
import org.springframework.stereotype.Component;

@Component
public class ReservationClientAdapter implements CreateReservationPort {

    private final ReservationClient reservationClient;

    public ReservationClientAdapter(ReservationClient reservationClient) {
        this.reservationClient = reservationClient;
    }

    @Override
    public ReservationResponse createReservation(Long customerId, Long vehicleId) {
       return reservationClient.createReservation(new CreateReservationRequest(customerId, vehicleId));
    }
}

