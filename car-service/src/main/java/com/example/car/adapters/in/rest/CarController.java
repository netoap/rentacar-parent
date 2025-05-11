package com.example.car.adapters.in.rest;

import com.example.car.domain.Car;
import com.example.car.ports.in.AddCarUseCase;
import com.example.car.ports.in.GetAvailableCarsQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final AddCarUseCase addCarUseCase;
    private final GetAvailableCarsQuery getAvailableCarsQuery;

    public CarController(AddCarUseCase addCarUseCase, GetAvailableCarsQuery getAvailableCarsQuery) {
        this.addCarUseCase = addCarUseCase;
        this.getAvailableCarsQuery = getAvailableCarsQuery;
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestParam String model, @RequestParam int year) {
        return ResponseEntity.ok(addCarUseCase.addCar(model, year));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars() {
        return ResponseEntity.ok(getAvailableCarsQuery.getAvailableCars());
    }
}
