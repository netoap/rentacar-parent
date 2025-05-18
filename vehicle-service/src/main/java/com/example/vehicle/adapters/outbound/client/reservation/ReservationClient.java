package com.example.vehicle.adapters.outbound.client.reservation;

import com.rentacar.commons.dto.CreateReservationRequest;
import com.rentacar.commons.dto.ReservationResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reservation-service", url = "${reservation.service.url}")
public interface ReservationClient {

    @PostMapping("/api/v1/reservations")
    @CircuitBreaker(name = "reservationService", fallbackMethod = "fallbackReservation")
    ReservationResponse createReservation(@RequestBody CreateReservationRequest request);

    default void fallbackReservation(CreateReservationRequest request, Throwable t) {
        throw new RuntimeException("Reservation service unavailable", t);
    }
}
