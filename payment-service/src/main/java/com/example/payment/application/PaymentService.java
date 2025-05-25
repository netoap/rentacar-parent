package com.example.payment.application;

import com.example.payment.adapters.in.rest.CreatePaymentRequest;
import com.example.payment.domain.PaymentEntity;
import com.example.payment.domain.PaymentStatus;
import com.example.payment.ports.in.CreatePaymentUseCase;
import com.example.payment.ports.out.PaymentGatewayPort;
import com.example.payment.ports.out.SavePaymentPort;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements CreatePaymentUseCase {

    private final SavePaymentPort savePaymentPort;
    private final PaymentGatewayPort paymentGatewayPort;

    public PaymentService(SavePaymentPort savePaymentPort, PaymentGatewayPort paymentGatewayPort) {
        this.savePaymentPort = savePaymentPort;
        this.paymentGatewayPort = paymentGatewayPort;
    }

    @Override
    public PaymentEntity createPayment(CreatePaymentRequest request) {
        PaymentEntity payment = new PaymentEntity(
                request.getReservationId(),
                request.getAmount(),
                request.getMethod(),
                PaymentStatus.PENDING
        );

        PaymentEntity saved = savePaymentPort.save(payment);
        boolean approved = paymentGatewayPort.processPayment(saved);
        saved.setStatus(approved ? PaymentStatus.PAID : PaymentStatus.FAILED);
        return savePaymentPort.save(saved);
    }
}
