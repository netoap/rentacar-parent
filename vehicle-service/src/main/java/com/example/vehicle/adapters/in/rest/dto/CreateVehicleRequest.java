package com.example.vehicle.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO for creating a new vehicle")
public class CreateVehicleRequest {

    @NotBlank
    @Schema(description = "Vehicle model", example = "Honda Civic", required = true)
    private String model;

    @Min(1886) // First car invented
    @Schema(description = "Model year", example = "2021", required = true)
    private int year;

    @Schema(description = "Availability status", example = "true")
    private boolean available;

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }
}
