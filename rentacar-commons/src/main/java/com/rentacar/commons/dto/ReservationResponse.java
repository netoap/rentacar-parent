package com.rentacar.commons.dto;

import com.rentacar.commons.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Reservation response object")
public class ReservationResponse {

    @Schema(description = "Reservation ID", example = "1001")
    private Long id;

    @Schema(description = "Customer Email", example = "alex@email.com")
    private String customerEmail;

    private String customerName;

    @Schema(description = "Vehicle ID", example = "42")
    private Long vehicleId;

    @Schema(description = "Reservation start date", example = "2025-06-10")
    private LocalDate startDate;

    @Schema(description = "Reservation end date", example = "2025-06-15")
    private LocalDate endDate;

    private String vehicleModel;

    private ReservationStatus status;

    private String model;

    public ReservationResponse() {}

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

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
