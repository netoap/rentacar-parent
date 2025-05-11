package com.example.reservation.domain;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private Long customerId;
    private Long carId;
    private LocalDateTime reservationDate;

    public Reservation(Long id, Long customerId, Long carId, LocalDateTime reservationDate) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.reservationDate = reservationDate;
    }

    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Long getCarId() { return carId; }
    public LocalDateTime getReservationDate() { return reservationDate; }
}

