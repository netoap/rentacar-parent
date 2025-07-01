package com.example.reservation.adapters.out.jpa;

import com.example.reservation.adapters.out.jpa.entity.ReservationEntity;
import com.example.reservation.domain.Reservation;
import com.rentacar.commons.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class JpaReservationMapper {

    public static ReservationEntity toEntity(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(reservation.getId());
        entity.setCustomerEmail(reservation.getCustomerEmail());
        entity.setCarId(reservation.getCarId());
        entity.setStartDate(reservation.getStartDate());
        entity.setEndDate(reservation.getEndDate());
        entity.setStatus(reservation.getStatus());
        return entity;
    }

    public static Reservation toDomain(ReservationEntity entity) {
        Reservation reservation = new Reservation();
        reservation.setId(reservation.getId());
        reservation.setCustomerEmail(reservation.getCustomerEmail());
        reservation.setCarId(reservation.getCarId());
        reservation.setStartDate(reservation.getStartDate());
        reservation.setEndDate(reservation.getEndDate());
        reservation.setStatus(reservation.getStatus());
        return reservation;
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCustomerEmail(reservation.getCustomerEmail());
        response.setVehicleId(reservation.getCarId());
        response.setStartDate(reservation.getStartDate());
        response.setEndDate(reservation.getEndDate());
        response.setStatus(reservation.getStatus());
        return response;
    }


}
