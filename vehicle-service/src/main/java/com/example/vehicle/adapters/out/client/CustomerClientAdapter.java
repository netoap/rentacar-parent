package com.example.vehicle.adapters.out.client;

import com.example.vehicle.ports.out.LoadCustomerPort;
import com.rentacar.commons.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerClientAdapter implements LoadCustomerPort {
    private final CustomerClient customerClient;
    public CustomerClientAdapter(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @Override
    public CustomerResponse loadCustomer(String email) {
        return customerClient.getCustomerByEmail(email);
    }
}
