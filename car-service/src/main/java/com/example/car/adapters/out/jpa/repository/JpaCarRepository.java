package com.example.car.adapters.out.jpa.repository;

import com.example.car.adapters.out.jpa.entity.JpaCarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCarRepository extends JpaRepository<JpaCarEntity, Long> {
    List<JpaCarEntity> findByAvailableTrue();
}
