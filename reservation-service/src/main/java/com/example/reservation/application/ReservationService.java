package com.example.reservation.application;

import com.example.reservation.domain.Reservation;
import com.example.reservation.ports.in.CreateReservationUseCase;
import com.example.reservation.ports.out.ReservationRepositoryPort;

import java.time.LocalDateTime;

public class ReservationService implements CreateReservationUseCase {
    private final ReservationRepositoryPort reservationRepository;

    public ReservationService(ReservationRepositoryPort reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(Long customerId, Long carId) {
        Reservation reservation = new Reservation(customerId, carId, LocalDateTime.now());
        return reservationRepository.save(reservation);
    }
}
