package com.example.payment.adapters.out.client.reservation;


import com.rentacar.commons.dto.ReservationStatusUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "reservation-service",
        url = "${reservation.service.url}",
fallback = ReservationClientFallback.class)
public interface ReservationClient {

    @PatchMapping("/api/v1/reservations/{id}/status")
    void updateReservationStatus(@PathVariable("id") Long id,
                                 @RequestBody ReservationStatusUpdateRequest request);
}