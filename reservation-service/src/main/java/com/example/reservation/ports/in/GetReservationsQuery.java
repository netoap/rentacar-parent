package com.example.reservation.ports.in;

import com.example.reservation.domain.Reservation;

import java.util.List;

public interface GetReservationsQuery {
    List<Reservation> getAllReservations();
}
