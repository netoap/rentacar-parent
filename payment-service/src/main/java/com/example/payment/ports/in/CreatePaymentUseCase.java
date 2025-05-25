package com.example.payment.ports.in;

import com.example.payment.adapters.in.rest.CreatePaymentRequest;
import com.example.payment.domain.PaymentEntity;

public interface CreatePaymentUseCase {
    PaymentEntity createPayment(CreatePaymentRequest request);
}
