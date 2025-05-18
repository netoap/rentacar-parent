package com.example.vehicle.adapters.outbound.client;

import com.example.vehicle.adapters.outbound.client.dto.CustomerResponse;
import com.example.vehicle.ports.out.LoadCustomerPort;
import org.springframework.stereotype.Component;

@Component
public class CustomerClientAdapter implements LoadCustomerPort {
    private final CustomerClient customerClient;
    public CustomerClientAdapter(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @Override
    public CustomerResponse loadCustomer(Long customerId) {
        return customerClient.getCustomerById(customerId);
    }
}
