package com.example.reservation.ports.in;

import com.example.reservation.adapters.out.jpa.entity.ReservationStatus;
import com.example.reservation.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface GetReservationsQuery {
    List<Reservation> getAllReservations();

    List<Reservation> getByCustomerEmail(String email);

    Reservation getById(Long id);
}
