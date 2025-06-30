package com.example.reservation.adapters.in.rest;

import com.example.reservation.adapters.out.jpa.entity.ReservationStatus;
import com.example.reservation.domain.Reservation;
import com.example.reservation.ports.in.CancelReservationUseCase;
import com.example.reservation.ports.in.CreateReservationUseCase;
import com.example.reservation.ports.in.GetReservationsQuery;
import com.example.reservation.ports.in.GetVehicleAvailabilityUseCase;
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
    private final CreateReservationUseCase reservationUseCase;
    private final GetReservationsQuery getReservationsQuery;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final VehicleClient vehicleClient;
    private final GetVehicleAvailabilityUseCase getVehicleAvailabilityUseCase;

    public ReservationController(CreateReservationUseCase reservationUseCase, GetReservationsQuery getReservationsQuery, CancelReservationUseCase cancelReservationUseCase, VehicleClient vehicleClient, GetVehicleAvailabilityUseCase getVehicleAvailabilityUseCase) {
        this.reservationUseCase = reservationUseCase;
        this.getReservationsQuery = getReservationsQuery;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.vehicleClient = vehicleClient;
        this.getVehicleAvailabilityUseCase = getVehicleAvailabilityUseCase;
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody CreateReservationRequest request) {

        Reservation reservation = reservationUseCase.createReservation(
                request.getCustomerEmail(),
                request.getVehicleId(),
                request.getStartDate(),
                request.getEndDate()
        );
        vehicleClient.updateAvailability(request.getVehicleId(), false);
        String model = vehicleClient.getVehicleModelById(request.getVehicleId());
        return new ResponseEntity<>(
                new ReservationResponse(
                        reservation.getId(),
                        reservation.getCustomerEmail(),
                        reservation.getCarId(),
                        model,
                        reservation.getStartDate(),
                        reservation.getEndDate()
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "Get all reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        List<ReservationResponse> responses = getReservationsQuery.getAllReservations().stream()
                .map(r -> {
                    String model = vehicleClient.getVehicleModelById(r.getCarId());
                    return new ReservationResponse(
                            r.getId(),
                            r.getCustomerEmail(),
                            r.getCarId(),
                            model,
                            r.getStartDate(),
                            r.getEndDate()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        cancelReservationUseCase.cancel(id); // sets status to CANCELLED
        Reservation reservation = getReservationsQuery.getById(id);
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
        List<Reservation> reservations = getReservationsQuery.getByCustomerEmail(email);

        List<ReservationResponse> response = reservations.stream()
                .map(r -> {
                    String model = vehicleClient.getVehicleModelById(r.getCarId());
                    return new ReservationResponse(
                            r.getId(),
                            r.getCustomerEmail(),
                            r.getCarId(),
                            model,
                            r.getStartDate(),
                            r.getEndDate()
                    );
                })
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
