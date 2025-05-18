package com.example.customer.ports.in;

import com.example.customer.domain.Customer;

public interface GetCustomerByIdQuery {
    Customer getById(Long id);
}
