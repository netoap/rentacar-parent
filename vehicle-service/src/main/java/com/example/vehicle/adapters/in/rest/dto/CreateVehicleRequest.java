package com.example.vehicle.adapters.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVehicleRequest {
    private String model;
    private int year;
    private boolean available;
    private String licensePlate;
    private BigDecimal pricePerDay;
}
