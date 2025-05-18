package com.example.vehicle.ports.out;


import com.rentacar.commons.dto.ReservationResponse;

public interface CreateReservationPort {
    ReservationResponse createReservation(Long customerId, Long vehicleId);
}
