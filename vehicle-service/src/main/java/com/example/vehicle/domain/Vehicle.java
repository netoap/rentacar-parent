package com.example.vehicle.domain;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    private Long id;
    private String model;
    private int year;
    private boolean available;
    private String licensePlate;
    private BigDecimal pricePerDay;
}
