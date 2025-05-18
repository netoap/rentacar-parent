package com.example.customer.application;

import com.example.customer.domain.Customer;
import com.example.customer.domain.exception.CustomerNotFoundException;
import com.example.customer.ports.in.*;
import com.example.customer.ports.out.CustomerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CreateCustomerUseCase, GetAllCustomersQuery, GetCustomerByIdQuery, UpdateCustomerUseCase, DeleteCustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    public CustomerService(CustomerRepositoryPort customerRepositoryPort) {
        this.customerRepositoryPort = customerRepositoryPort;
    }

    @Override
    public Customer createCustomer(String name, String email) {
        Customer customer = new Customer(name, email);
        return customerRepositoryPort.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepositoryPort.findAll();
    }

    @Override
    public Customer getById(Long id) {
        return customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer updateCustomer(Long id, String name, String email) {
        Customer existingCustomer = customerRepositoryPort.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        Customer updated = new Customer(existingCustomer.getId(), name, email);
        return customerRepositoryPort.save(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (customerRepositoryPort.findById(id).isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        customerRepositoryPort.deleteById(id);
    }
}
