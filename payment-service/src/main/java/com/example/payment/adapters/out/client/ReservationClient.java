package com.example.payment.adapters.out.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "reservation-client")
public interface ReservationClient {

    @PutMapping("/{id}/paid")
    void markAsPaid(@PathVariable("id") Long reservationId);
}