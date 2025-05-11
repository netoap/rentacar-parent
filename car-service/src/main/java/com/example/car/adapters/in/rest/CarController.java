package com.example.car.adapters.in.rest;

import com.example.car.adapters.in.rest.dto.CarRequest;
import com.example.car.adapters.in.rest.dto.CarResponse;
import com.example.car.domain.Car;
import com.example.car.ports.in.AddCarUseCase;
import com.example.car.ports.in.GetAvailableCarsQuery;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cars")
@Validated
public class CarController {
    private final AddCarUseCase addCarUseCase;
    private final GetAvailableCarsQuery getAvailableCarsQuery;

    public CarController(AddCarUseCase addCarUseCase, GetAvailableCarsQuery getAvailableCarsQuery) {
        this.addCarUseCase = addCarUseCase;
        this.getAvailableCarsQuery = getAvailableCarsQuery;
    }

    @PostMapping
    @Operation(summary = "Add a new car")
    public ResponseEntity<CarResponse> addCar(@Valid @RequestBody CarRequest request) {
        Car saved = addCarUseCase.addCar(request.getModel(), request.getYear());
        return ResponseEntity.ok(new CarResponse(saved.getId(), saved.getModel(), saved.getYear(), saved.isAvailable()));
    }

    @GetMapping("/available")
    @Operation(summary = "Get all available cars")
    public ResponseEntity<List<CarResponse>> getAvailableCars() {
        return ResponseEntity.ok(   getAvailableCarsQuery.getAvailableCars().stream()
                .map(car -> new CarResponse(car.getId(), car.getModel(), car.getYear(), car.isAvailable()))
                .toList());
    }
}
