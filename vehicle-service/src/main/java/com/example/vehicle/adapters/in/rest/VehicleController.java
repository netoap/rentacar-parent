package com.example.vehicle.adapters.in.rest;

import com.example.vehicle.adapters.in.rest.dto.CreateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.RentVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.UpdateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.ports.in.*;
import com.rentacar.commons.dto.VehicleAvailabilityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/vehicles")
@Tag(name = "Vehicle API v1", description = "Vehicle management operations (version 1)")
@Validated
public class VehicleController {
    private final AddVehicleUseCase addVehicleUseCase;
    private final GetVehicleByIdQuery getVehicleByIdQuery;
    private final GetAllVehiclesQuery getAllVehiclesQuery;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;
    private final GetAvailableVehiclesQuery getAvailableVehiclesQuery;
    private final RentVehicleUseCase rentVehicleUseCase;
    private final ReturnVehicleUseCase returnVehicleUseCase;
    private final GetVehicleAvailabilityUseCase getVehicleAvailabilityUseCase;


    public VehicleController(AddVehicleUseCase addVehicleUseCase, GetVehicleByIdQuery getVehicleByIdQuery, GetAllVehiclesQuery getAllVehiclesQuery, UpdateVehicleUseCase updateVehicleUseCase, DeleteVehicleUseCase deleteVehicleUseCase, GetAvailableVehiclesQuery getAvailableVehiclesQuery, RentVehicleUseCase rentVehicleUseCase, ReturnVehicleUseCase returnVehicleUseCase, GetVehicleAvailabilityUseCase getVehicleAvailabilityUseCase) {
        this.addVehicleUseCase = addVehicleUseCase;
        this.getVehicleByIdQuery = getVehicleByIdQuery;
        this.getAllVehiclesQuery = getAllVehiclesQuery;
        this.updateVehicleUseCase = updateVehicleUseCase;
        this.deleteVehicleUseCase = deleteVehicleUseCase;
        this.getAvailableVehiclesQuery = getAvailableVehiclesQuery;
        this.rentVehicleUseCase = rentVehicleUseCase;
        this.returnVehicleUseCase = returnVehicleUseCase;
        this.getVehicleAvailabilityUseCase = getVehicleAvailabilityUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new vehicle")
    public ResponseEntity<VehicleResponse> create(@RequestBody CreateVehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addVehicleUseCase.addVehicle(request));
    }

    @GetMapping("/available")
    @Operation(summary = "Get all available vehicles")
    public ResponseEntity<List<VehicleResponse>> getAvailableVehicle() {
        return ResponseEntity.ok(getAvailableVehiclesQuery.getAvailableVehicles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(getVehicleByIdQuery.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all vehicles")
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        return ResponseEntity.ok(getAllVehiclesQuery.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update vehicle by ID")
    public ResponseEntity<VehicleResponse> updateVehicle(@PathVariable Long id, @RequestBody UpdateVehicleRequest request) {
        return ResponseEntity.ok(updateVehicleUseCase.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete vehicle by ID")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        deleteVehicleUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Rent a vehicle")
    @PostMapping("/rent")
    public ResponseEntity<Void> rentVehicle(@Valid @RequestBody RentVehicleRequest request) {
        rentVehicleUseCase.rentVehicle(
                request.getCustomerId(),
                request.getVehicleId(),
                request.getStartDate(),
                request.getEndDate()
        );
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Return a rented vehicle")
    @PostMapping("/return")
    public ResponseEntity<Void> returnVehicle(@RequestParam Long vehicleId) {
        returnVehicleUseCase.returnVehicle(vehicleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vehicles/{vehicleId}/availability")
    @Operation(summary = "Get booked dates for a vehicle")
    public ResponseEntity<VehicleAvailabilityResponse> getAvailability(
            @PathVariable Long vehicleId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        VehicleAvailabilityResponse response = getVehicleAvailabilityUseCase.getVehicleAvailability(vehicleId, fromDate, toDate);
        return ResponseEntity.ok(response);
    }


}
