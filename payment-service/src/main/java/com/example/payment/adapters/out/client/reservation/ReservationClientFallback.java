package com.example.payment.adapters.out.client.reservation;

import com.rentacar.commons.dto.ReservationStatusUpdateRequest;

public class ReservationClientFallback implements ReservationClient{
    @Override
    public void updateReservationStatus(Long id, ReservationStatusUpdateRequest request) {
        System.err.printf("Failed to update reservation %d status to %s (fallback triggered)%n", id, request.getStatus());
    }
}
