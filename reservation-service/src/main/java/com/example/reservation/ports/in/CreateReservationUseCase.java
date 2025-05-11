package com.example.reservation.ports.in;

import com.example.reservation.domain.Reservation;

public interface CreateReservationUseCase {
    Reservation createReservation(Long customerId, Long carId);
}
