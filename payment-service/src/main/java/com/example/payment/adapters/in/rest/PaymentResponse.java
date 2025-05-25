package com.example.payment.adapters.in.rest;

import com.example.payment.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Long reservationId,
        BigDecimal amount,
        String method,
        PaymentStatus status,
        LocalDateTime createdAt
) {}
