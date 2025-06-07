package com.example.reservation.ports.in;

import com.example.reservation.domain.Reservation;

import java.time.LocalDate;

public interface CreateReservationUseCase {
    Reservation createReservation(Long customerId, Long carId, LocalDate startDate, LocalDate endDate);

}
