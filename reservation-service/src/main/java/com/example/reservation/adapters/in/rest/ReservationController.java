package com.example.reservation.adapters.in.rest;

import com.example.reservation.adapters.in.rest.dto.ReservationRequest;
import com.example.reservation.adapters.in.rest.dto.ReservationResponse;
import com.example.reservation.domain.Reservation;
import com.example.reservation.ports.in.CreateReservationUseCase;
import com.example.reservation.ports.in.GetReservationsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservations", description = "Endpoints for managing reservations")
@RestController
@RequestMapping("/reservations")
@Validated
public class ReservationController {
    private final CreateReservationUseCase reservationUseCase;
    private final GetReservationsQuery getReservationsQuery;

    public ReservationController(CreateReservationUseCase reservationUseCase, GetReservationsQuery getReservationsQuery) {
        this.reservationUseCase = reservationUseCase;
        this.getReservationsQuery = getReservationsQuery;
    }

    @PostMapping
    @Operation(summary = "Create a new reservation")
    public ResponseEntity<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationUseCase.createReservation(request.getCustomerId(), request.getCarId());
        return new ResponseEntity<>(
                new ReservationResponse(reservation.getId(), reservation.getCustomerId(),
                        reservation.getCarId(), reservation.getReservationDate()),
                HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all reservations")
    public ResponseEntity<List<ReservationResponse>> getAllReservations() {
        return ResponseEntity.ok(getReservationsQuery.getAllReservations().stream()
                .map(r -> new ReservationResponse(r.getId(), r.getCustomerId(), r.getCarId(), r.getReservationDate()))
                .toList());
    }
}
