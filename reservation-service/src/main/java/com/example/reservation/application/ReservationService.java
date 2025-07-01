package com.example.reservation.application;


import com.example.reservation.adapters.out.jpa.JpaReservationMapper;
import com.example.reservation.domain.Reservation;
import com.example.reservation.domain.exception.ReservationNotFoundException;
import com.example.reservation.ports.in.*;
import com.example.reservation.ports.out.ReservationPort;
import com.rentacar.commons.ReservationStatus;
import com.rentacar.commons.dto.ReservationResponse;
import com.rentacar.commons.dto.VehicleAvailabilityResponse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReservationService implements
        CreateReservationUseCase,
        GetReservationsUseCase,
        GetVehicleAvailabilityUseCase,
        CancelReservationUseCase, UpdateReservationUseCase {
    private final ReservationPort reservationPort;

    public ReservationService(ReservationPort reservationRepository) {
        this.reservationPort = reservationRepository;
    }

    @Override
    public Reservation createReservation(String customerEmail, Long carId, LocalDate startDate, LocalDate endDate) {
//        boolean notAvailable = reservationRepository.isOverlappingReservation(carId, endDate, startDate);
//        if (notAvailable) {
//            throw new IllegalStateException("El vehículo ya está reservado en esas fechas.");
//        }
        Reservation reservation = new Reservation(customerEmail, carId, startDate, endDate, ReservationStatus.PENDING);
        return reservationPort.save(reservation);
    }

    @Override
    public void markAsPaid(Long id) {
        Reservation reservation = reservationPort.findById(id);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationPort.save(reservation);
    }

    @Override
    public List<ReservationResponse> findReservationsByEmailAndStatus(String email, ReservationStatus status) {
        List<Reservation> reservation = reservationPort.findByCustomerEmailAndStatus(email, ReservationStatus.PENDING.toString());
        return reservation.stream()
                .map(JpaReservationMapper::toResponse).toList();
    }


    @Override
    public List<Reservation> getAllReservations() {
        return reservationPort.findAll();
    }

    @Override
    public List<Reservation> getByCustomerEmail(String email) {
        return reservationPort.findByCustomerEmail(email);
    }

    @Override
    public Reservation getById(Long id) {
        Reservation reservation = reservationPort.findById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found with ID: " + id);
        }
        return reservation;
    }




    @Override
    public VehicleAvailabilityResponse getVehicleAvailability(Long vehicleId, LocalDate from, LocalDate to) {
        List<Reservation> reservations = reservationPort.findByVehicleIdAndDateRange(vehicleId, from, to);

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
    public boolean isVehicleUnavailable(Long vehicleId, LocalDate start, LocalDate end) {
        return reservationPort.isOverlappingReservation(vehicleId, end, start);

    }

    @Override
    public void cancel(Long reservationId) {
        Reservation reservation = reservationPort.findById(reservationId);
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationPort.save(reservation);
    }


}
