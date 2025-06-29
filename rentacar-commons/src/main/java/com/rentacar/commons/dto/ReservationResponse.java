package com.rentacar.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Reservation response object")
public class ReservationResponse {

    @Schema(description = "Reservation ID", example = "1001")
    private Long id;

    @Schema(description = "Customer Email", example = "alex@email.com")
    private String customerEmail;

    @Schema(description = "Vehicle ID", example = "42")
    private Long vehicleId;

    @Schema(description = "Reservation start date", example = "2025-06-10")
    private LocalDate startDate;

    @Schema(description = "Reservation end date", example = "2025-06-15")
    private LocalDate endDate;

    public ReservationResponse() {}

    public ReservationResponse(Long id, String customerEmail, Long vehicleId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
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

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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
