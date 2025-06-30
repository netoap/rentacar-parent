package com.example.vehicle.ports.out;

import com.example.vehicle.adapters.out.client.dto.CustomerResponse;

public interface LoadCustomerPort {
    CustomerResponse loadCustomer(String customerEmail);
}
