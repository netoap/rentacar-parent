package com.example.reservation.domain;

import com.example.reservation.adapters.out.jpa.entity.ReservationStatus;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String customerEmail;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;

    public Reservation(Long id, String customerEmail, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Reservation(String customerEmail, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this.customerEmail = customerEmail;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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

