package com.example.vehicle.adapters.in.rest.dto;

import com.example.vehicle.domain.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;

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

    public VehicleResponse(Long id, String model, int year, boolean available) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.available = available;
    }
    public Long getId() { return id; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public boolean isAvailable() { return available; }

    public static VehicleResponse fromDomain(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.isAvailable()
        );
    }
}
