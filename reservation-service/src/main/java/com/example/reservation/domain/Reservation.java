package com.example.reservation.domain;

import com.example.reservation.adapters.out.jpa.entity.ReservationStatus;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Long customerId;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

    public Reservation(Long id, Long customerId, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Reservation(Long customerId, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this(null, customerId, carId, startDate, endDate, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}

