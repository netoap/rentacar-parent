package com.example.car.ports.out;

import com.example.car.domain.Car;

import java.util.List;

public interface CarRepositoryPort {
    Car save(Car car);
    List<Car> findAvailableCars();
}
