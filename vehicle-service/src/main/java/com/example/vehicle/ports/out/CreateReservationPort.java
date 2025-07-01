package com.example.vehicle.ports.out;


import com.rentacar.commons.dto.ReservationResponse;

import java.time.LocalDate;

public interface CreateReservationPort {
    ReservationResponse createReservation(String email, String customerId, Long vehicleId, LocalDate startDate, LocalDate endDate);

}
