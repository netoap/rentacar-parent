package com.example.customer.ports.in;

import com.example.customer.domain.Customer;

public interface GetCustomerByEmailQuery {

    Customer getByEmail(String email);
}
