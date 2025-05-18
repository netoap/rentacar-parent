package com.example.customer.ports.in;

import com.example.customer.domain.Customer;

public interface UpdateCustomerUseCase {
    Customer updateCustomer(Long id, String name, String email);
}
