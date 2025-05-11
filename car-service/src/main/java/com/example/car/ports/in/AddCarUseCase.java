package com.example.car.ports.in;

import com.example.car.domain.Car;

public interface AddCarUseCase {
    Car addCar(String model, int year);
}
