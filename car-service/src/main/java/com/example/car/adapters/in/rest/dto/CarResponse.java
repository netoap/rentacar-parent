package com.example.car.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO for Car")
public class CarResponse {

    @Schema(description = "Car ID", example = "1")
    private Long id;

    @Schema(description = "Car model", example = "Honda Civic")
    private String model;

    @Schema(description = "Model year", example = "2021")
    private int year;

    @Schema(description = "Availability", example = "true")
    private boolean available;

    public CarResponse(Long id, String model, int year, boolean available) {
        this.id = id;
        this.model = model;
        this.year = year;
        this.available = available;
    }

    public Long getId() { return id; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public boolean isAvailable() { return available; }
}
