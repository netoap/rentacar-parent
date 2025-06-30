package com.example.reservation.adapters.out.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle-service", path = "/api/v1/vehicles")
public interface VehicleClient {

    @GetMapping("/{id}/model")
    String getVehicleModelById(@PathVariable("id") Long id);
}
