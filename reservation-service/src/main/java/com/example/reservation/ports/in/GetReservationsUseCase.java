package com.example.reservation.ports.in;

import com.example.reservation.domain.Reservation;
import com.rentacar.commons.ReservationStatus;
import com.rentacar.commons.dto.ReservationResponse;

import java.util.List;

public interface GetReservationsUseCase {
    List<Reservation> getAllReservations();
    List<Reservation> getByCustomerEmail(String email);
    Reservation getById(Long id);
    List<ReservationResponse> findReservationsByEmailAndStatus(String email, ReservationStatus status);
}
