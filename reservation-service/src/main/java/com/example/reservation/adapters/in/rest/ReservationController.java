package com.example.reservation.adapters.in.rest;

import com.example.reservation.domain.Reservation;
import com.example.reservation.ports.in.CreateReservationUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final CreateReservationUseCase reservationUseCase;

    public ReservationController(CreateReservationUseCase reservationUseCase) {
        this.reservationUseCase = reservationUseCase;
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestParam Long customerId,
                                                         @RequestParam Long carId) {
        Reservation reservation = reservationUseCase.createReservation(customerId, carId);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }
}
