package com.example.vehicle.adapters.out.client.reservation;

import com.rentacar.commons.dto.CreateReservationRequest;
import com.rentacar.commons.dto.ReservationResponse;
import com.rentacar.commons.dto.ReservationStatusUpdateRequest;
import com.rentacar.commons.dto.VehicleAvailabilityResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "reservation-service", url = "${reservation.service.url}", contextId = "reservationClientAdapter")
public interface ReservationClient {

    @PostMapping("/api/v1/reservations")
    @CircuitBreaker(name = "reservationService", fallbackMethod = "fallbackReservation")
    ReservationResponse createReservation(@RequestBody CreateReservationRequest request);

    @PatchMapping("/api/v1/reservations/{id}/status")
    void updateReservationStatus(@PathVariable("id") Long id, @RequestBody ReservationStatusUpdateRequest request);

    @GetMapping("/api/v1/reservations/vehicles/{vehicleId}/availability")
    VehicleAvailabilityResponse getVehicleAvailability(
            @PathVariable("vehicleId") Long vehicleId,
            @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
            @RequestParam(value = "toDate", required = false) LocalDate toDate
    );


    default void fallbackReservation(CreateReservationRequest request, Throwable t) {
        throw new RuntimeException("Reservation service unavailable", t);
    }
}
