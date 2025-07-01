package com.example.reservation.adapters.out.jpa;

import com.example.reservation.adapters.out.jpa.entity.ReservationEntity;
import com.example.reservation.domain.Reservation;
import com.rentacar.commons.dto.ReservationResponse;

public class JpaReservationMapper {

    public static ReservationEntity toEntity(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.setId(reservation.getId());
        entity.setCustomerEmail(reservation.getCustomerEmail());
        entity.setCustomerName(reservation.getCustomerName());
        entity.setCarId(reservation.getCarId());
        entity.setStartDate(reservation.getStartDate());
        entity.setEndDate(reservation.getEndDate());
        entity.setStatus(reservation.getStatus());
        return entity;
    }

    public static Reservation toDomain(ReservationEntity entity) {
        Reservation reservation = new Reservation();
        reservation.setId(entity.getId());
        reservation.setCustomerEmail(entity.getCustomerEmail());
        reservation.setCustomerName(entity.getCustomerName());
        reservation.setCarId(entity.getCarId());
        reservation.setStartDate(entity.getStartDate());
        reservation.setEndDate(entity.getEndDate());
        reservation.setStatus(entity.getStatus());
        reservation.setVehicleModel(entity.getVehicleModel());
        return reservation;
    }


    public static ReservationResponse toResponse(Reservation reservation) {
        ReservationResponse response = new ReservationResponse();
        response.setId(reservation.getId());
        response.setCustomerEmail(reservation.getCustomerEmail());
        response.setCustomerName(reservation.getCustomerName());
        response.setVehicleId(reservation.getCarId());
        response.setStartDate(reservation.getStartDate());
        response.setEndDate(reservation.getEndDate());
        response.setStatus(reservation.getStatus());
        response.setVehicleModel(reservation.getVehicleModel());
        return response;
    }


}
