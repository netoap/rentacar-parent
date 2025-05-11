package com.example.car.adapters.out.jpa;

import com.example.car.adapters.out.jpa.entity.JpaCarEntity;
import com.example.car.adapters.out.jpa.repository.JpaCarRepository;
import com.example.car.domain.Car;
import com.example.car.ports.out.CarRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaCarRepositoryAdapter implements CarRepositoryPort {

    private final JpaCarRepository repository;

    public JpaCarRepositoryAdapter(JpaCarRepository repository) {
        this.repository = repository;
    }

    @Override
    public Car save(Car car) {
        JpaCarEntity entity = new JpaCarEntity();
        entity.setModel(car.getModel());
        entity.setYear(car.getYear());
        entity.setAvailable(car.isAvailable());

        JpaCarEntity saved = repository.save(entity);
        return new Car(saved.getId(), saved.getModel(), saved.getYear(), saved.isAvailable());
    }

    @Override
    public List<Car> findAvailableCars() {
        return repository.findByAvailableTrue().stream()
                .map(e -> new Car(e.getId(), e.getModel(), e.getYear(), e.isAvailable()))
                .collect(Collectors.toList());
    }
}
