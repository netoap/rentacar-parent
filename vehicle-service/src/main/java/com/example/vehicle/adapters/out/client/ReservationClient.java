package com.example.vehicle.adapters.out.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

//@FeignClient(name = "reservation-service", url = "${reservation.service.url}")
@FeignClient(
        name = "reservation-service",
        url = "${reservation.service.url}",
        configuration = com.example.vehicle.config.FeignClientConfig.class
)
public interface ReservationClient {

    @GetMapping("/api/v1/reservations/check")
    boolean isVehicleAvailable(
            @RequestParam("vehicleId") Long vehicleId,
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
    );
}
