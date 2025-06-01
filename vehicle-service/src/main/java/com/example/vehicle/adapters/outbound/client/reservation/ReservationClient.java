package com.example.vehicle.adapters.outbound.client.reservation;

import com.rentacar.commons.dto.CreateReservationRequest;
import com.rentacar.commons.dto.ReservationResponse;
import com.rentacar.commons.dto.ReservationStatusUpdateRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reservation-service", url = "${reservation.service.url}")
public interface ReservationClient {

    @PostMapping("/api/v1/reservations")
    @CircuitBreaker(name = "reservationService", fallbackMethod = "fallbackReservation")
    ReservationResponse createReservation(@RequestBody CreateReservationRequest request);

    @PatchMapping("/api/v1/reservations/{id}/status")
    void updateReservationStatus(@PathVariable Long id, @RequestBody ReservationStatusUpdateRequest request);

    default void fallbackReservation(CreateReservationRequest request, Throwable t) {
        throw new RuntimeException("Reservation service unavailable", t);
    }
}
