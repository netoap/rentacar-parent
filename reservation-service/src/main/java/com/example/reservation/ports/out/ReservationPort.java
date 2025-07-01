package com.example.reservation.ports.out;

import com.example.reservation.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationPort {
    Reservation save(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByVehicleIdAndDateRange(Long vehicleId, LocalDate fromDate, LocalDate toDate);
    List<Reservation> findByCustomerEmail(String email);
    List<Reservation> findByCustomerEmailAndStatus(String email, String status);
    boolean isOverlappingReservation(Long carId, LocalDate endDate, LocalDate startDate);

}
