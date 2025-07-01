package com.example.payment.ports.in;

import com.example.payment.adapters.in.rest.PaymentRequest;

public interface PaymentUseCase {
    void processPayment(PaymentRequest request);
}
