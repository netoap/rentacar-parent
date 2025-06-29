package com.example.vehicle.ports.out;

import com.example.vehicle.adapters.outbound.client.dto.CustomerResponse;

public interface LoadCustomerPort {
    CustomerResponse loadCustomer(String customerEmail);
}
