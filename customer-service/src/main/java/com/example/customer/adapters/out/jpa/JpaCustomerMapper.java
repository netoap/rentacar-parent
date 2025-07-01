package com.example.customer.adapters.out.jpa;

import com.example.customer.domain.Customer;

public class JpaCustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getName(), entity.getEmail());
    }

    public static CustomerEntity toEntity(Customer domain) {
        return new CustomerEntity(domain.getId(), domain.getName(), domain.getEmail());
    }
}
