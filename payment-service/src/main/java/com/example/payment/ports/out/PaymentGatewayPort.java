package com.example.payment.ports.out;

import com.example.payment.domain.PaymentEntity;

public interface PaymentGatewayPort {
    boolean processPayment(PaymentEntity payment);
}
