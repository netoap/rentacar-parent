package com.example.payment.adapters.in.rest;

import com.example.payment.domain.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Filtering options for payments")
public class PaymentSearchCriteria {

    @Schema(description = "Filter by payment status", example = "PAID")
    private PaymentStatus status;

    @Schema(description = "Filter by reservation ID", example = "101")
    private Long reservationId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtTo;

    private Long customerId;

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public LocalDate getCreatedAtFrom() { return createdAtFrom; }
    public void setCreatedAtFrom(LocalDate createdAtFrom) { this.createdAtFrom = createdAtFrom; }

    public LocalDate getCreatedAtTo() { return createdAtTo; }
    public void setCreatedAtTo(LocalDate createdAtTo) { this.createdAtTo = createdAtTo; }
}
