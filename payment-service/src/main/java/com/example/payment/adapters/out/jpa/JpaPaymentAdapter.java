package com.example.payment.adapters.out.jpa;

import com.example.payment.ports.out.SavePaymentPort;
import org.springframework.stereotype.Component;

@Component
public class JpaPaymentAdapter implements SavePaymentPort {

    private final PaymentRepository paymentRepository;

    public JpaPaymentAdapter(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(JpaPaymentMapper.toEntity(payment));
    }
}
