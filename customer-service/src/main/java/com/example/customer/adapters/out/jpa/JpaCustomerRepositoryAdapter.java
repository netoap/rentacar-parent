
package com.example.customer.adapters.out.jpa;

import com.example.customer.domain.Customer;
import com.example.customer.ports.in.GetCustomerByEmailQuery;
import com.example.customer.ports.out.CustomerRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort, GetCustomerByEmailQuery {

    private final SpringDataCustomerRepository repository;

    public JpaCustomerRepositoryAdapter(SpringDataCustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = JpaCustomerMapper.toEntity(customer);
        return JpaCustomerMapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll().stream()
                .map(JpaCustomerMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id).map(JpaCustomerMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Customer getByEmail(String email) {
        return repository.findByEmail(email)
                .map(JpaCustomerMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
    }
}
