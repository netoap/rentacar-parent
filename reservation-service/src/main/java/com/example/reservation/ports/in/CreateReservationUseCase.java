package com.example.reservation.ports.in;

import com.example.reservation.domain.Reservation;

import java.time.LocalDate;

public interface CreateReservationUseCase {
    Reservation createReservation(String vehicleModel, String email, String customerName, Long carId, LocalDate startDate, LocalDate endDate);
}
