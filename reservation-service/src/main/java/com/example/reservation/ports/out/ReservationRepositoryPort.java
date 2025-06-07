package com.example.reservation.ports.out;

import com.example.reservation.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryPort {
    Reservation save(Reservation reservation);
    List<Reservation> findAll();
    List<Reservation> findByVehicleIdAndDateRange(Long vehicleId, LocalDate fromDate, LocalDate toDate);
}
