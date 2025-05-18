package com.example.customer.ports.out;

import com.example.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Customer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    void deleteById(Long id);
}
