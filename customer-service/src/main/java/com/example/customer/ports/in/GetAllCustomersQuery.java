package com.example.customer.ports.in;

import com.example.customer.domain.Customer;

import java.util.List;

public interface GetAllCustomersQuery {
    List<Customer> getAllCustomers();
}
