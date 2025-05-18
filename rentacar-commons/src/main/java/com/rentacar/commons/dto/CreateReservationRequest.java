package com.rentacar.commons.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateReservationRequest {
    @NotNull
    @Positive
    @Schema(description = "Customer ID", example = "101", required = true)
    private Long customerId;

    @NotNull
    @Positive
    @Schema(description = "Vehicle ID", example = "202", required = true)
    private Long vehicleId;

    public CreateReservationRequest() {}

    public CreateReservationRequest(Long customerId, Long vehicleId) {
        this.customerId = customerId;
        this.vehicleId = vehicleId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }
}
