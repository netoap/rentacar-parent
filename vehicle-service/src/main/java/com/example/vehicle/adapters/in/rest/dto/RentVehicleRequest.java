package com.example.vehicle.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class RentVehicleRequest {

    @NotNull
    @Positive
    @Schema(description = "Customer ID", example = "101")
    private Long customerId;

    @NotNull
    @Positive
    @Schema(description = "Vehicle ID", example = "42")
    private Long vehicleId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Reservation start date", example = "2025-06-10")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Reservation end date", example = "2025-06-15")
    private LocalDate endDate;

    public Long getCustomerId() {
        return customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
