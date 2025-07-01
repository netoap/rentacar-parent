package com.example.reservation.adapters.out.jpa;

import com.example.reservation.adapters.out.jpa.entity.ReservationEntity;
import com.example.reservation.domain.Reservation;
import com.example.reservation.domain.exception.ReservationNotFoundException;
import com.example.reservation.ports.out.ReservationPort;
import com.rentacar.commons.ReservationStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JpaReservationAdapter implements ReservationPort {

    private final SpringDataReservationRepository repository;

    public JpaReservationAdapter(SpringDataReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity entity = JpaReservationMapper.toEntity(reservation);
        ReservationEntity saved = repository.save(entity);
        return JpaReservationMapper.toDomain(saved);
    }

    @Override
    public List<Reservation> findByCustomerEmail(String email) {
        return repository.findByCustomerEmail(email).stream()
                .map(JpaReservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByCustomerEmailAndStatus(String email, String status) {
        List<ReservationEntity> reservations = repository.findByCustomerEmail(email);
        return reservations.stream().filter(r -> r.getStatus().name().equals(status))
                .map(JpaReservationMapper::toDomain)
                .toList();
    }

    @Override
    public Reservation findById(Long id) {
        return repository.findById(id)
                .map(JpaReservationMapper::toDomain)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    @Override
    public List<Reservation> findAll() {
        return repository.findAll().stream()
                .map(JpaReservationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByVehicleIdAndDateRange(Long vehicleId, LocalDate fromDate, LocalDate toDate) {
        return List.of();
    }



    @Override
    public boolean isOverlappingReservation(Long carId, LocalDate endDate, LocalDate startDate) {
        return repository.isOverlappingReservation(carId, endDate, startDate);
    }
}
