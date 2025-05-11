package com.example.reservation.ports.out;

import com.example.reservation.domain.Reservation;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
}
