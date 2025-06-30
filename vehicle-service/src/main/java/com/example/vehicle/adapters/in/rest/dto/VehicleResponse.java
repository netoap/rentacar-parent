package com.example.vehicle.adapters.in.rest.dto;

import com.example.vehicle.domain.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response DTO for Vehicle")
public class VehicleResponse {

    @Schema(description = "Vehicle ID", example = "1")
    private Long id;

    @Schema(description = "Model name", example = "Toyota Corolla")
    private String model;

    @Schema(description = "Model year", example = "2021")
    private int year;

    @Schema(description = "Availability status", example = "true")
    private boolean available;

    @Schema(description = "License plate", example = "1234-ABC")
    private String licensePlate;

    @Schema(description = "Rental price per day", example = "45.00")
    private BigDecimal pricePerDay;

    public static VehicleResponse fromDomain(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.isAvailable(),
                vehicle.getLicensePlate(),
                vehicle.getPricePerDay()
        );
    }
}
