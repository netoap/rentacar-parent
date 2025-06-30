package com.example.reservation;

import com.example.reservation.application.ReservationService;
import com.example.reservation.ports.in.CreateReservationUseCase;
import com.example.reservation.ports.out.ReservationRepositoryPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.example.reservation")
@EnableFeignClients(basePackages = "com.example.reservation.adapters.out.feign")
public class ReservationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @Bean
    public CreateReservationUseCase createReservationUseCase(ReservationRepositoryPort reservationRepository) {
        return new ReservationService(reservationRepository);
    }
}
