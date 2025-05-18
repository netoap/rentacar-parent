package com.example.customer.adapters.in.rest;

import com.example.customer.adapters.in.rest.dto.CustomerRequest;
import com.example.customer.adapters.in.rest.dto.CustomerResponse;
import com.example.customer.application.CustomerService;
import com.example.customer.config.ModelMapperConfig;
import com.example.customer.domain.Customer;
import com.example.customer.ports.in.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetAllCustomersQuery getAllCustomersQuery;
    private final GetCustomerByIdQuery getCustomerByIdQuery;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final ModelMapperConfig modelMapper;

    public CustomerController(CreateCustomerUseCase createCustomerUseCase, GetAllCustomersQuery getAllCustomersQuery, GetCustomerByIdQuery getCustomerByIdQuery, UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase, ModelMapperConfig modelMapper) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getAllCustomersQuery = getAllCustomersQuery;
        this.getCustomerByIdQuery = getCustomerByIdQuery;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        Customer customer = createCustomerUseCase.createCustomer(request.getName(), request.getEmail());
        CustomerResponse response = modelMapper.toCustomerResponse(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = getAllCustomersQuery.getAllCustomers();
        List<CustomerResponse> responses = customers.stream()
                .map(modelMapper::toCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = getCustomerByIdQuery.getById(id);
        CustomerResponse response = modelMapper.toCustomerResponse(customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        Customer updatedCustomer = updateCustomerUseCase.updateCustomer(id, request.getName(), request.getEmail());
        CustomerResponse response = modelMapper.toCustomerResponse(updatedCustomer);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        deleteCustomerUseCase.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}



