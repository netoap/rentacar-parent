package com.example.reservation.application;

import com.example.reservation.adapters.out.jpa.entity.ReservationStatus;
import com.example.reservation.domain.Reservation;
import com.example.reservation.ports.in.CancelReservationUseCase;
import com.example.reservation.ports.in.CreateReservationUseCase;
import com.example.reservation.ports.in.GetReservationsQuery;
import com.example.reservation.ports.in.GetVehicleAvailabilityUseCase;
import com.example.reservation.ports.out.ReservationRepositoryPort;
import com.rentacar.commons.dto.VehicleAvailabilityResponse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService implements
        CreateReservationUseCase,
        GetReservationsQuery,
        GetVehicleAvailabilityUseCase,
        CancelReservationUseCase {
    private final ReservationRepositoryPort reservationRepository;

    public ReservationService(ReservationRepositoryPort reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation createReservation(String customerEmail, Long carId, LocalDate startDate, LocalDate endDate) {
        Reservation reservation = new Reservation(customerEmail, carId, startDate, endDate, ReservationStatus.CONFIRMED);
        return reservationRepository.save(reservation);
    }


    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getByCustomerEmail(String email) {
        return reservationRepository.findByCustomerEmail(email);
    }

    @Override
    public VehicleAvailabilityResponse getVehicleAvailability(Long vehicleId, LocalDate from, LocalDate to) {
        List<Reservation> reservations = reservationRepository.findByVehicleIdAndDateRange(vehicleId, from, to);

        Set<LocalDate> booked = new HashSet<>();
        for (Reservation r : reservations) {
            LocalDate start = r.getStartDate();
            LocalDate end = r.getEndDate();
            while (!start.isAfter(end)) {
                booked.add(start);
                start = start.plusDays(1);
            }
        }

        List<LocalDate> sorted = booked.stream().sorted().collect(Collectors.toList());
        return new VehicleAvailabilityResponse(vehicleId, sorted);
    }

    @Override
    public void cancel(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

}
