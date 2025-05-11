package com.example.car.ports.in;

import com.example.car.domain.Car;

import java.util.List;

public interface GetAvailableCarsQuery {
    List<Car> getAvailableCars();
}
