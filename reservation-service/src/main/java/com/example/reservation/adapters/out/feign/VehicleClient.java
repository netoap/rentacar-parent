package com.example.reservation.adapters.out.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "vehicle-service", url = "${vehicle.service.url}")
public interface VehicleClient {

    @PatchMapping("/api/v1/vehicles/{id}/availability")
    void updateAvailability(
            @PathVariable("id") Long vehicleId,
            @RequestParam("available") boolean available
    );

    @GetMapping("/api/v1/vehicles/{id}/model")
    String getVehicleModelById(@PathVariable("id") Long id);
}
