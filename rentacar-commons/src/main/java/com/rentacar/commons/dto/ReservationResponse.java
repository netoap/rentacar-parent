package com.rentacar.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Reservation response object")
public class ReservationResponse {

    @Schema(description = "Reservation ID", example = "1001")
    private Long id;

    @Schema(description = "Customer ID", example = "101")
    private Long customerId;

    @Schema(description = "Vehicle ID", example = "42")
    private Long vehicleId;

    @Schema(description = "Reservation start date", example = "2025-06-10")
    private LocalDate startDate;

    @Schema(description = "Reservation end date", example = "2025-06-15")
    private LocalDate endDate;

    public ReservationResponse() {}

    public ReservationResponse(Long id, Long customerId, Long vehicleId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
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
