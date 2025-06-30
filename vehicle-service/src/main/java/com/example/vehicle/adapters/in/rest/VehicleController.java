package com.example.vehicle.adapters.in.rest;

import com.example.vehicle.adapters.in.rest.dto.CreateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.RentVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.UpdateVehicleRequest;
import com.example.vehicle.adapters.in.rest.dto.VehicleResponse;
import com.example.vehicle.adapters.out.jpa.mapper.JpaVehicleMapper;
import com.example.vehicle.domain.Vehicle;
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
import java.util.Map;

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

    @GetMapping("/available")
    public ResponseEntity<List<VehicleResponse>> getAvailableVehicles() {
        List<Vehicle> vehicles = getAllVehiclesQuery.getAllAvailableVehicles();
        List<VehicleResponse> response = vehicles.stream()
                .map(JpaVehicleMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }


    @PostMapping
    @Operation(summary = "Create a new vehicle")
    public ResponseEntity<VehicleResponse> create(@RequestBody CreateVehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addVehicleUseCase.addVehicle(request));
    }

    @GetMapping("/available-range")
    @Operation(summary = "Listar vehículos disponibles entre fechas")
    public ResponseEntity<List<VehicleResponse>> getAvailableBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return ResponseEntity.ok(getAvailableVehiclesQuery.getAvailableVehicles(start, end));
    }


//    @GetMapping("/available")
//    @Operation(summary = "Get vehicles available in a date range")
//    public ResponseEntity<List<VehicleResponse>> getAvailableVehicleByDates(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
//    ) {
//        return ResponseEntity.ok(getAvailableVehiclesQuery.getAvailableVehicles(start, end));
//    }


    @GetMapping("/{id}")
    @Operation(summary = "Get vehicle by ID")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable Long id) {
        return ResponseEntity.ok(getVehicleByIdQuery.getById(id));
    }

    @GetMapping("/{id}/model")
    @Operation(summary = "Get only the model of a vehicle by ID")
    public ResponseEntity<Map<String, String>> getVehicleModelById(@PathVariable("id") Long id) {
        String model = getVehicleByIdQuery.getById(id).getModel();
        return ResponseEntity.ok(Map.of("model", model));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar vehículos por modelo")
    public ResponseEntity<List<VehicleResponse>> searchByModel(@RequestParam String model) {
        List<VehicleResponse> results = getAllVehiclesQuery.getAll().stream()
                .filter(v -> v.getModel().toLowerCase().contains(model.toLowerCase()))
                .toList();
        return ResponseEntity.ok(results);
    }
    @PatchMapping("/{id}/availability")
    @Operation(summary = "Cambiar disponibilidad del vehículo")
    public ResponseEntity<Void> toggleAvailability(@PathVariable Long id, @RequestParam boolean available) {
        updateVehicleUseCase.updateAvailability(id, available);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @Operation(summary = "Listar vehículos paginados")
    public ResponseEntity<List<VehicleResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<VehicleResponse> all = getAllVehiclesQuery.getAll();
        int from = Math.min(page * size, all.size());
        int to = Math.min(from + size, all.size());

        return ResponseEntity.ok(all.subList(from, to));
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
                request.getCustomerEmail(),
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
