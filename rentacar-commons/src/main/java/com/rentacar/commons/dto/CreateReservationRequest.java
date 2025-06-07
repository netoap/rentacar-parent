package com.rentacar.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Request to create a new reservation")
public class CreateReservationRequest {

    @NotNull
    @Positive
    @Schema(description = "Customer ID", example = "101", required = true)
    private Long customerId;

    @NotNull
    @Positive
    @Schema(description = "Vehicle ID", example = "42", required = true)
    private Long vehicleId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Reservation start date", example = "2025-06-10", required = true)
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Reservation end date", example = "2025-06-15", required = true)
    private LocalDate endDate;

    public CreateReservationRequest() {}

    public CreateReservationRequest(Long customerId, Long vehicleId, LocalDate startDate, LocalDate endDate) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
