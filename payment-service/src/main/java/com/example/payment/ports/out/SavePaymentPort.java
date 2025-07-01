package com.example.payment.ports.out;

import com.example.payment.adapters.out.jpa.Payment;

public interface SavePaymentPort {
    void save(Payment payment);
}
