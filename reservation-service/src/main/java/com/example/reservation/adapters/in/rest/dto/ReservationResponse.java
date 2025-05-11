package com.example.reservation.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response data for a reservation")
public class ReservationResponse {

    @Schema(description = "Reservation ID", example = "1")
    private Long id;

    @Schema(description = "Customer ID", example = "101")
    private Long customerId;

    @Schema(description = "Car ID", example = "202")
    private Long carId;

    @Schema(description = "Reservation timestamp", example = "2025-05-11T17:30:00")
    private LocalDateTime reservationDate;

    // Constructor
    public ReservationResponse(Long id, Long customerId, Long carId, LocalDateTime reservationDate) {
        this.id = id;
        this.customerId = customerId;
        this.carId = carId;
        this.reservationDate = reservationDate;
    }

    // Getters
    public Long getId() { return id; }
    public Long getCustomerId() { return customerId; }
    public Long getCarId() { return carId; }
    public LocalDateTime getReservationDate() { return reservationDate; }
}
