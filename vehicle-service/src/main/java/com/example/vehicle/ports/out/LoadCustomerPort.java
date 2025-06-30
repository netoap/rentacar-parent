package com.example.vehicle.ports.out;


import com.rentacar.commons.dto.CustomerResponse;

public interface LoadCustomerPort {
    CustomerResponse loadCustomer(String customerEmail);
}
