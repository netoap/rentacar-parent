package com.example.customer.ports.in;

import com.example.customer.domain.Customer;

public interface CreateCustomerUseCase {
    Customer createCustomer(String name, String email);
}
