package com.example.reservation.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request data for creating a reservation")
public class ReservationRequest {

    @NotNull
    @Positive
    @Schema(description = "Customer ID", example = "101", required = true)
    private Long customerId;

    @NotNull
    @Positive
    @Schema(description = "Car ID", example = "202", required = true)
    private Long carId;

    public Long getCustomerId() {
        return customerId;
    }

    public Long getCarId() {
        return carId;
    }
}
