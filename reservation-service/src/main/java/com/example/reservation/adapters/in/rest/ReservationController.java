package com.example.reservation.adapters.in.rest;

import com.example.reservation.adapters.out.jpa.JpaReservationMapper;
import com.example.reservation.domain.Reservation;
import com.example.reservation.ports.in.*;
import com.rentacar.commons.ReservationStatus;
import com.rentacar.commons.dto.CreateReservationRequest;
import com.rentacar.commons.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.example.reservation.adapters.out.feign.VehicleClient;
@Tag(name = "Reservations", description = "Endpoints for managing reservations")
@RestController
@RequestMapping("/api/v1/reservations")
@Validated
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final GetReservationsUseCase getReservationsUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final VehicleClient vehicleClient;
    private final GetVehicleAvailabilityUseCase getVehicleAvailabilityUseCase;
    private final UpdateReservationUseCase updateReservationUseCase;

    public ReservationController(CreateReservationUseCase reservationUseCase, GetReservationsUseCase getReservationsUseCase, CancelReservationUseCase cancelReservationUseCase, VehicleClient vehicleClient, GetVehicleAvailabilityUseCase getVehicleAvailabilityUseCase, UpdateReservationUseCase updateReservationUseCase) {
        this.createReservationUseCase = reservationUseCase;
        this.getReservationsUseCase = getReservationsUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.vehicleClient = vehicleClient;
        this.getVehicleAvailabilityUseCase = getVehicleAvailabilityUseCase;
        this.updateReservationUseCase = updateReservationUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody CreateReservationRequest request) {

        Reservation reservation = createReservationUseCase.createReservation(
                request.getCustomerEmail(),
                request.getVehicleId(),
                request.getStartDate(),
                request.getEndDate()
        );

        ReservationResponse reservationResponse = new ReservationResponse();
        return new ResponseEntity<>(JpaReservationMapper.toResponse(reservation), HttpStatus.CREATED);
    }

    @GetMapping("/pending")
    public List<ReservationResponse> pendingReservations(
            @RequestParam String email) {
        return getReservationsUseCase.findReservationsByEmailAndStatus(email, ReservationStatus.PENDING);
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable("id") Long id) {
        updateReservationUseCase.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get all reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> responses = getReservationsUseCase.getAllReservations().stream()
                .map(JpaReservationMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        cancelReservationUseCase.cancel(id); // sets status to CANCELLED
        Reservation reservation = getReservationsUseCase.getById(id);
        vehicleClient.updateAvailability(reservation.getCarId(), true);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/mine")
    @Operation(
            summary = "Get reservations for the currently authenticated user",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<List<ReservationResponse>> getMyReservations() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Reservation> reservations = getReservationsUseCase.getByCustomerEmail(email);

        List<ReservationResponse> response = reservations.stream()
                .map(JpaReservationMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isVehicleAvailable(
            @RequestParam Long vehicleId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        boolean notAvailable = getVehicleAvailabilityUseCase.isVehicleUnavailable(vehicleId, end, start);
        return ResponseEntity.ok(!notAvailable);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleConflict(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
