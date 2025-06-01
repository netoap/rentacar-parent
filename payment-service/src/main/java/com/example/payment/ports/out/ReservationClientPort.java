package com.example.payment.ports.out;

public interface ReservationClientPort {
    void updateReservationStatus(Long reservationId, String status);
}
