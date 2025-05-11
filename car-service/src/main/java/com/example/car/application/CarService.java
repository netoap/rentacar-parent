package com.example.car.application;

import com.example.car.domain.Car;
import com.example.car.ports.in.AddCarUseCase;
import com.example.car.ports.in.GetAvailableCarsQuery;
import com.example.car.ports.out.CarRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements AddCarUseCase, GetAvailableCarsQuery {

    private final CarRepositoryPort carRepository;

    public CarService(CarRepositoryPort carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car addCar(String model, int year) {
        Car car = new Car(model, year, true); // default: available = true
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAvailableCars() {
        return carRepository.findAvailableCars();
    }
}
