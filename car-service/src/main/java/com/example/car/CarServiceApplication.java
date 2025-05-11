package com.example.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.car")
public class CarServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }
}
