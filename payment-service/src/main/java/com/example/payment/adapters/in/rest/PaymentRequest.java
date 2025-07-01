package com.example.payment.adapters.in.rest;

import java.math.BigDecimal;

public class PaymentRequest {
    private Long reservationId;
    private BigDecimal amount;
    private String method;

    public PaymentRequest() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
