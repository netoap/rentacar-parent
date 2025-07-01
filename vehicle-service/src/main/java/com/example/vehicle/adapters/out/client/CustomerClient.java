package com.example.vehicle.adapters.out.client;

import com.rentacar.commons.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerClient {

    @GetMapping("/api/v1/customers/email/{email}")
    CustomerResponse getCustomerByEmail(@PathVariable("email") String email);


}
