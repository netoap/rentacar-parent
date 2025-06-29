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
    @Schema(description = "Customer Email", example = "name@email.com", required = true)
    private String customerEmail;

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

    public CreateReservationRequest() {
    }

    public CreateReservationRequest(String customerEmail, Long vehicleId, LocalDate startDate, LocalDate endDate) {
        this.customerEmail = customerEmail;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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
}
