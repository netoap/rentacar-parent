package com.example.reservation.ports.out;

import com.example.reservation.domain.Reservation;

import java.util.List;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    List<Reservation> findAll();
}
