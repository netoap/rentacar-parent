package com.example.reservation.domain;


import com.rentacar.commons.ReservationStatus;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private String customerEmail;
    private String customerName;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
    private String vehicleModel;

    public Reservation() {
    }

    public Reservation(String vehicleModel,String customerEmail, String customerName, Long carId, LocalDate startDate, LocalDate endDate, ReservationStatus status) {
        this.customerName = customerName;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.customerEmail = customerEmail;
        this.vehicleModel = vehicleModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
}

