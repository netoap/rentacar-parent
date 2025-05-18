package com.example.customer.config;

import com.example.customer.adapters.in.rest.dto.CustomerResponse;
import com.example.customer.domain.Customer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
    }
}
