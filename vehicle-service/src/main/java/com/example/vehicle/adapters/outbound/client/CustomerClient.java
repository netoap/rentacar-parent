package com.example.vehicle.adapters.outbound.client;

import com.example.vehicle.adapters.outbound.client.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerClient {

    @GetMapping("/api/customers/{email}")
    CustomerResponse getCustomerByEmail(@PathVariable("email") String email);

}
